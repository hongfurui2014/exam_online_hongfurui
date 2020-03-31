package com.itdupan.api;

import com.itdupan.bean.ResultBean;
import com.itdupan.pojo.Rights;
import com.itdupan.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface UserApi {

    /**
     * 根据用户名和密码查询
     * @param username
     * @param password
     * @return
     */
    @GetMapping("user/login")
    public ResultBean<User> login(@RequestParam("username") String username, @RequestParam("password") String password);

    /**
     * 根据用户id查询角色,再根据角色id查询其下的所有一级二级菜单
     * @param userId
     * @return
     */
    @GetMapping("user/findRolesRightsByUserId")
    public ResultBean<List<Rights>> findRolesRightsByUserId(@RequestParam("userId") Long userId);

    /**
     * 通过id查询
     *
     * @param userId
     * @return
     */
    @GetMapping("user/findUserById")
    public ResultBean<User> findUserById(@RequestParam("userId") Long userId);

    /**
     * 通过登录账户查询列表
     *
     * @param userAccount
     * @return
     */
    @GetMapping("user/findUsersByuserAccount")
    public ResultBean<List<User>> findUsersByUserName(@RequestParam("userAccount") String userAccount);
}
