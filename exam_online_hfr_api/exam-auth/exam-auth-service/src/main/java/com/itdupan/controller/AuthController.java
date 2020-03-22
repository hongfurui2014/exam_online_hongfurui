package com.itdupan.controller;

import com.itdupan.bean.ResultBean;
import com.itdupan.config.JwtProperties;
import com.itdupan.pojo.UserInfo;
import com.itdupan.service.AuthService;
import com.itdupan.utils.CookieUtils;
import com.itdupan.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("auth")
@EnableConfigurationProperties(JwtProperties.class)
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 后台用户登录
     * @param request
     * @param response
     * @param username
     * @param password
     * @return
     */
    @PostMapping("login")
    public ResultBean<Void> login(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("username") String username,
            @RequestParam("password") String password) {
        String token = authService.login(username, password);

        if (token == null) {
            return new ResultBean<Void>(600, "用户名或密码错误！", null);
        }

        CookieUtils.setCookie(request, response, jwtProperties.getCookieName(), token, jwtProperties.getExpire() * 60);
        return new ResultBean<Void>(200, "登陆成功！", null);
    }

    /**
     * 考试用户登录
     * @param request
     * @param response
     * @param username
     * @param password
     * @return
     */
    @PostMapping("loginQ")
    public ResultBean<Void> loginQ(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("username") String username,
            @RequestParam("password") String password) {
        String token = authService.loginQ(username, password);

        if (token == null) {
            return new ResultBean<Void>(600, "用户名或密码错误！", null);
        }

        CookieUtils.setCookie(request, response, "HFR_Q_TOKEN", token, jwtProperties.getExpire() * 60);
        return new ResultBean<Void>(200, "登陆成功！", null);
    }

    /**
     * 通过cookie解析，验证用户是否已认证
     * @param token
     * @return
     */
    @GetMapping("verify")
    public ResultBean<UserInfo> verify(
            HttpServletRequest request,
            HttpServletResponse response,
            @CookieValue("HFR_TOKEN") String token){

        try {
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());

            if(userInfo == null){   //token解析失败，身份未认证
                return new ResultBean<UserInfo>(401, "身份未认证！",null);
            }

            //解析成功刷新jwt时效，即重新生成jwt
            token = JwtUtils.generateToken(userInfo, jwtProperties.getPrivateKey(), jwtProperties.getExpire());

            //再刷新cookie时效和
            CookieUtils.setCookie(request, response, jwtProperties.getCookieName(), token, jwtProperties.getExpire() * 60);

            //System.out.println(user);
            return new ResultBean<UserInfo>(200, "认证成功！",userInfo);
        }catch (Exception e){
        }
        return new ResultBean<UserInfo>(401, "身份未认证！",null);
    }

    /**
     * 通过cookie解析，验证考试用户是否已认证
     * @param token
     * @return
     */
    @GetMapping("verifyQ")
    public ResultBean<UserInfo> verifyQ(
            HttpServletRequest request,
            HttpServletResponse response,
            @CookieValue("HFR_Q_TOKEN") String token){
        try {
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());

            if(userInfo == null){   //token解析失败，身份未认证
                return new ResultBean<UserInfo>(401, "身份未认证！",null);
            }

            //解析成功刷新jwt时效，即重新生成jwt
            token = JwtUtils.generateToken(userInfo, jwtProperties.getPrivateKey(), jwtProperties.getExpire());

            //再刷新cookie时效和
            CookieUtils.setCookie(request, response, "HFR_Q_TOKEN", token, jwtProperties.getExpire() * 60);

            //System.out.println(user);
            return new ResultBean<UserInfo>(200, "认证成功！",userInfo);
        }catch (Exception e){
        }
        return new ResultBean<UserInfo>(401, "身份未认证！",null);
    }
}
