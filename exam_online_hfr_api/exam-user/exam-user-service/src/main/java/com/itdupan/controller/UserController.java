package com.itdupan.controller;

import com.itdupan.bean.PageResult;
import com.itdupan.bean.ResultBean;
import com.itdupan.pojo.User;
import com.itdupan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 添加
     *
     * @param
     * @return
     */
    @PostMapping("addUser")
    public ResultBean<Void> addUser(@RequestBody @Valid User user) {
        List<User> list = userService.findUsersByUserAccount(user.getUserAccount());
        if (list.size() >= 1) {
            return new ResultBean(600, "该登录账户已存在！", null);
        }
        List<User> list2 = userService.findUsersByRealname(user.getUserRealname());
        if (list2.size() >= 1) {
            return new ResultBean(600, "该真实姓名已被使用！", null);
        }
        userService.addUser(user);
        return new ResultBean(201, "添加成功！", null);
    }

    /**
     * 通过id删除
     *
     * @param userId
     * @return
     */
    @DeleteMapping("delUserById")
    public ResultBean<Void> delUserById(@RequestParam("userId") Long userId) {
        try {
            userService.delUserById(userId);
            return new ResultBean(204, "删除成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultBean(600, "删除失败，该用户可能被其它因素引用到！", null);
        }
    }

    /**
     * 更新
     *
     * @param user
     * @return
     */
    @PutMapping("updateUser")
    public ResultBean<Void> updateUser(@RequestBody User user) {
        List<User> list = userService.findUsersByUserAccount(user.getUserAccount());
        if (list.size() >= 1) {
            return new ResultBean(600, "修改失败，用户已存在！", null);
        }
        userService.updateUser(user);
        return new ResultBean(201, "修改成功！", null);
    }

    /**
     * 通过id查询
     *
     * @param userId
     * @return
     */
    @GetMapping("findUserById")
    public ResultBean<User> getUserById(@RequestParam("userId") Long userId) {
        User user = userService.findUserById(userId);
        if (user == null) {
            return new ResultBean(600, "用户不存在！", null);
        }
        return new ResultBean(200, "查询成功！", user);
    }

    /**
     * 查询所有
     *
     * @return
     */
    @GetMapping("findUsers")
    public ResultBean<List<User>> findAll() {
        List<User> list = userService.findAll();
        return new ResultBean(200, "查询成功！", list);
    }

    /**
     * 分页查询
     * @param page
     * @param rows
     * @param userAccount
     * @param userRealname
     * @param fkUserGradeId
     * @param fkUserRoleId
     * @return
     */
    @GetMapping("findUsersByPage")
    public ResultBean<PageResult<User>> findUsersByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "userAccount", required = false) String userAccount,
            @RequestParam(value = "userRealname", required = false) String userRealname,
            @RequestParam(value = "fkUserGradeId", required = false) Integer fkUserGradeId,
            @RequestParam(value = "fkUserRoleId", required = false) Integer fkUserRoleId) {
        PageResult<User> usersByPage = userService.findUsersByPage(page, rows, userAccount, userRealname, fkUserGradeId, fkUserRoleId);
        return new ResultBean(200, "查询成功！", usersByPage);
    }


    /**
     * 通过名称查询列表
     *
     * @param userName
     * @return
     */
    @GetMapping("findUsersByUserName")
    public ResultBean<List<User>> findUsersByUserName(@RequestParam("userName") String userName) {
        return new ResultBean(200, "查询成功！", userService.findUsersByUserAccount(userName));
    }


}
