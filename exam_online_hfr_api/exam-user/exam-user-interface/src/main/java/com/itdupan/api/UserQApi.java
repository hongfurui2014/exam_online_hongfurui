package com.itdupan.api;

import com.itdupan.bean.ResultBean;
import com.itdupan.pojo.UserQ;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserQApi {

    /**
     * 根据用户名和密码查询
     * @param userQname
     * @param password
     * @return
     */
    @GetMapping("userQ/loginQ")
    public ResultBean<UserQ> loginQ(@RequestParam("userQname") String userQname, @RequestParam("password") String password);

    /**
     * 通过id查询
     *
     * @param userQId
     * @return
     */
    @GetMapping("userQ/findUserQById")
    public ResultBean<UserQ> findUserQById(@RequestParam("userQId") Long userQId);
}
