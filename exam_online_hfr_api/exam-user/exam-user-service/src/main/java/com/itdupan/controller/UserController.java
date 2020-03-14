package com.itdupan.controller;

import com.itdupan.bean.PageResult;
import com.itdupan.bean.ResultBean;
import com.itdupan.pojo.User;
import com.itdupan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 添加
     *
     * @param user
     * @return
     */
    @PostMapping("addUser")
    public @ResponseBody
    ResultBean addUser(@RequestBody User user) {
        List<User> list = userService.findUsersByUserName(user.getUserAccount());
        if (list.size() >= 1) {
            return new ResultBean(600, "用户已存在，不允许重复添加！", null);
        }
        userService.addUser(user);
        return new ResultBean(201, "添加成功！", null);
    }

    /**
     * 通过主键删除
     *
     * @param userId
     * @return
     */
    @DeleteMapping("delUserById")
    public @ResponseBody
    ResultBean delUserById(@RequestParam("userId") Long userId) {
        try {
            userService.delUserById(userId);
            System.out.println(userId);
            return new ResultBean(204, "删除成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultBean(600, "删除失败，该用户可能被权限表引用到！", null);
        }
    }

    /**
     * 更新
     *
     * @param user
     * @return
     */
    @PutMapping("updateUser")
    public @ResponseBody
    ResultBean updateUser(@RequestBody User user) {
        List<User> list = userService.findUsersByUserName(user.getUserAccount());
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
    public @ResponseBody
    ResultBean getUserById(@RequestParam("userId") Long userId) {
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
    public @ResponseBody
    ResultBean findAll() {
        List<User> list = userService.findAll();
        return new ResultBean(200, "查询成功！", list);
    }

    /**
     * 分页查询
     *
     * @param page
     * @param rows
     * @param userName
     * @return
     */
    @GetMapping("findUsersByPage")
    public @ResponseBody
    ResultBean findUsersByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "userName", required = false) String userName) {
        PageResult<User> usersByPage = userService.findUsersByPage(page, rows, userName);
        return new ResultBean(200, "查询成功！", usersByPage);
    }

    /**
     * 通过名称查询列表
     *
     * @param userName
     * @return
     */
    @GetMapping("findUsersByUserName")
    public @ResponseBody
    ResultBean findUsersByUserName(@RequestParam("userName") String userName) {
        return new ResultBean(200, "查询成功！", userService.findUsersByUserName(userName));
    }
}
