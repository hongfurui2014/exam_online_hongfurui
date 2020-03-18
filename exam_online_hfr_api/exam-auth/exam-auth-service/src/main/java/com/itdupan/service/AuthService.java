package com.itdupan.service;

import com.itdupan.bean.ResultBean;
import com.itdupan.config.JwtProperties;
import com.itdupan.feign.UserClient;
import com.itdupan.pojo.User;
import com.itdupan.pojo.UserInfo;
import com.itdupan.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserClient userClient;

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
}
