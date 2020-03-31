package com.itdupan.filter;

import com.itdupan.client.RightsClient;
import com.itdupan.client.RoleRightsClient;
import com.itdupan.client.UserClient;
import com.itdupan.config.FilterProperties;
import com.itdupan.config.JwtProperties;
import com.itdupan.enums.ExamExceptionEnum;
import com.itdupan.exception.ExamException;
import com.itdupan.pojo.Rights;
import com.itdupan.pojo.UserInfo;
import com.itdupan.utils.CookieUtils;
import com.itdupan.utils.JwtUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 鉴定用户是否有权限
 */
@Component
@EnableConfigurationProperties({JwtProperties.class, FilterProperties.class})
public class AuthorizationFilter extends ZuulFilter {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private FilterProperties filterProperties;

    @Autowired
    private RightsClient rightsClient;

    @Autowired
    private UserClient userClient;

    @Autowired
    private RoleRightsClient roleRightsClient;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 10;
    }

    /**
     * 只拦截三级菜单中的路径
     * @return
     */
    @Override
    public boolean shouldFilter() {

        //查询所有三级菜单的路径
        List<String> paths = new ArrayList<>();

        List<Rights> list = rightsClient.findAll().getData();

        for(Rights r : list){
            for(Rights r2 : r.getChildren()){
                for(Rights r3: r2.getChildren()){
                    if(r3.getRightsLevel() == 3){
                        paths.add(r3.getRightsPath());
                    }
                }
            }
        }

        //初始化zull运行上下文
        RequestContext context = RequestContext.getCurrentContext();

        HttpServletRequest request = context.getRequest();

        String uri = request.getRequestURI().toString();

        if(paths.contains(uri.substring(5))){
            return true;    //在三级菜单中的访问路径都要拦截
        }
        return false;

    }

    @Override
    public Object run() throws ZuulException {

        //初始化zull运行上下文
        RequestContext context = RequestContext.getCurrentContext();

        HttpServletRequest request = context.getRequest();

        String uri = request.getRequestURI().toString().substring(5); //

        try {
            String token = CookieUtils.getCookieValue(request, "HFR_TOKEN");
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());

            List<Long> ids = roleRightsClient.getRightsIdsByRoleId(userClient.findUserById(userInfo.getUserId()).getData().getFkUserRoleId()).getData();    //获取该用户能访问的所有三级菜单id集合

            for(Long id: ids){
                if(uri.equals(rightsClient.findRightsById(id).getData().getRightsPath())){
                    return null;    //用户有该权限，放行
                }
            }

            //拦截请求
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());

            //抛异常
            throw new ExamException(ExamExceptionEnum.NOT_RIGHT);

        }catch (Exception e){
            //拦截请求
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());

            //抛异常
            throw new ExamException(ExamExceptionEnum.NOT_RIGHT);
        }

//        return null;
    }
}
