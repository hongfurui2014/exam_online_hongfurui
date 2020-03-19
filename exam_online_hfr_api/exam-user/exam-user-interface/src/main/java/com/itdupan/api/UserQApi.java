package com.itdupan.api;

import com.itdupan.bean.ResultBean;
import com.itdupan.pojo.User;
import com.itdupan.pojo.UserQ;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserQApi {

    /**
     * 根据用户名和密码查询
     * @param username
     * @param password
     * @return
     */
    @GetMapping("userq/login")
    public ResultBean<UserQ> login(@RequestParam("username") String username, @RequestParam("password") String password);
}
