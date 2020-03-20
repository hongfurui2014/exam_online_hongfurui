package com.itdupan.service;

import com.itdupan.bean.ResultBean;
import com.itdupan.config.JwtProperties;
import com.itdupan.feign.UserClient;
import com.itdupan.feign.UserQClient;
import com.itdupan.pojo.User;
import com.itdupan.pojo.UserInfo;
import com.itdupan.pojo.UserQ;
import com.itdupan.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserClient userClient;  //处理后台用户

    @Autowired
    private UserQClient userQClient;    //处理前台用户

    @Autowired
    private JwtProperties jwtProperties;

    public String login(String username, String password) {
        //根据用户名密码查询
        ResultBean<User> res = userClient.login(username, password);

        if(res.getData() == null){
            return null;
        }
        User user = res.getData();
        try {
            UserInfo userInfo = new UserInfo();

            userInfo.setUserId(user.getUserId());
            userInfo.setUserRealname(user.getUserRealname());

            //生成tioken
            return JwtUtils.generateToken(userInfo, jwtProperties.getPrivateKey(), jwtProperties.getExpire());
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public String loginQ(String username, String password) {
        //根据用户名密码查询
        ResultBean<UserQ> res = userQClient.loginQ(username, password);

        if(res.getData() == null){
            return null;
        }
        UserQ userQ = res.getData();
        try {
            UserInfo userInfo = new UserInfo();

            userInfo.setUserId(userQ.getUserQId());
            userInfo.setUserRealname(userQ.getUserQRealname());

            //生成tioken
            return JwtUtils.generateToken(userInfo, jwtProperties.getPrivateKey(), jwtProperties.getExpire());
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
