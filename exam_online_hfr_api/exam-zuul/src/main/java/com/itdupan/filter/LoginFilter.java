package com.itdupan.filter;

import com.itdupan.config.FilterProperties;
import com.itdupan.config.JwtProperties;
import com.itdupan.utils.CookieUtils;
import com.itdupan.utils.JwtUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
@EnableConfigurationProperties({JwtProperties.class, FilterProperties.class})
public class LoginFilter extends ZuulFilter {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private FilterProperties filterProperties;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 10;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {

        //获取白名单
        List<String> allowPaths = filterProperties.getAllowPaths();

        //初始化zull运行上下文
        RequestContext context = RequestContext.getCurrentContext();

        HttpServletRequest request = context.getRequest();

        String url = request.getRequestURL().toString();

        System.out.println(url);

        for (String allowPath: allowPaths){
            if(StringUtils.contains(url, allowPath)){
                return false;   //在白名单中的路径放行
            }
        }

        return true;
    }

    @Override
    public Object run() throws ZuulException {

        //初始化zull运行上下文
        RequestContext context = RequestContext.getCurrentContext();

        HttpServletRequest request = context.getRequest();

        String token = CookieUtils.getCookieValue(request, jwtProperties.getCookieName());

        if(StringUtils.isEmpty(token)){ //拦截请求
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }

        try {
            JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
        }catch (Exception e){
            //e.printStackTrace();
            //拦截请求
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }

        return null;
    }
}
