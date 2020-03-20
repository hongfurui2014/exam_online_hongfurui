package com.itdupan.controller;

import com.itdupan.bean.PageResult;
import com.itdupan.bean.ResultBean;
import com.itdupan.pojo.UserQ;
import com.itdupan.service.UserQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("userQ")
public class UserQController {

    @Autowired
    private UserQService userQService;

    /**
     * 添加
     *
     * @param
     * @return
     */
    @PostMapping("addUserQ")
    public ResultBean<Void> addUserQ(@RequestBody @Valid UserQ userQ) {
        List<UserQ> list = userQService.findUserQsByUserQAccount(userQ.getUserQAccount());
        if (list.size() >= 1) {
            return new ResultBean(600, "该登录账户已存在！", null);
        }
        List<UserQ> list2 = userQService.findUserQsByRealname(userQ.getUserQRealname());
        if (list2.size() >= 1) {
            return new ResultBean(600, "该真实姓名已被使用！", null);
        }
        userQService.addUserQ(userQ);
        return new ResultBean(201, "添加成功！", null);
    }

    /**
     * 通过id删除
     *
     * @param userQId
     * @return
     */
    @DeleteMapping("delUserQById")
    public ResultBean<Void> delUserQById(@RequestParam("userQId") Long userQId) {
        try {
            userQService.delUserQById(userQId);
            return new ResultBean(204, "删除成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultBean(600, "删除失败，该用户可能被其它因素引用到！", null);
        }
    }

    /**
     * 更新
     *
     * @param userQ
     * @return
     */
    @PutMapping("updateUserQ")
    public ResultBean<Void> updateUserQ(@RequestBody UserQ userQ) {
        userQService.updateUserQ(userQ);
        return new ResultBean(201, "修改成功！", null);
    }

    /**
     * 通过id查询
     *
     * @param userQId
     * @return
     */
    @GetMapping("findUserQById")
    public ResultBean<UserQ> findUserQById(@RequestParam("userQId") Long userQId) {
        UserQ userQ = userQService.findUserQById(userQId);
        if (userQ == null) {
            return new ResultBean(600, "用户不存在！", null);
        }
        return new ResultBean(200, "查询成功！", userQ);
    }

    /**
     * 查询所有
     *
     * @return
     */
    @GetMapping("findUserQs")
    public ResultBean<List<UserQ>> findUserQs() {
        List<UserQ> list = userQService.findAll();
        return new ResultBean(200, "查询成功！", list);
    }

    /**
     * 分页查询
     * @param page
     * @param rows
     * @param userQAccount
     * @param userQRealname
     * @param fkUserQGradeId
     * @return
     */
    @GetMapping("findUserQsByPage")
    public ResultBean<PageResult<UserQ>> findUserQsByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "userQAccount", required = false) String userQAccount,
            @RequestParam(value = "userQRealname", required = false) String userQRealname,
            @RequestParam(value = "fkUserQGradeId", required = false) Integer fkUserQGradeId) {
        PageResult<UserQ> userQsByPage = userQService.findUserQsByPage(page, rows, userQAccount, userQRealname, fkUserQGradeId);
        return new ResultBean(200, "查询成功！", userQsByPage);
    }


    /**
     * 通过登录账户查询列表
     *
     * @param userQAccount
     * @return
     */
    @GetMapping("findUserQsByuserQAccount")
    public ResultBean<List<UserQ>> findUserQsByUserQName(@RequestParam("userQAccount") String userQAccount) {
        return new ResultBean(200, "查询成功！", userQService.findUserQsByUserQAccount(userQAccount));
    }

    /**
     * 通过真实姓名查询列表
     *
     * @param userQRealname
     * @return
     */
    @GetMapping("findUserQsByUserQRealname")
    public ResultBean<List<UserQ>> findUserQsByUserQRealname(@RequestParam("userQRealname") String userQRealname) {
        return new ResultBean(200, "查询成功！", userQService.findUserQsByRealname(userQRealname));
    }

    /**
     * 根据用户名和密码查询
     * @param userQname
     * @param password
     * @return
     */
    @GetMapping("loginQ")
    public ResultBean<UserQ> loginQ(@RequestParam("userQname") String userQname, @RequestParam("password") String password){
        UserQ userQ = userQService.login(userQname, password);
        if(userQ == null){
            return new ResultBean<UserQ>(600, "用户名或密码错误！", null);
        }
        return new ResultBean<UserQ>(200, "用户获取成功！", userQ);
    }
}
