package com.itdupan.controller;

import com.itdupan.bean.PageResult;
import com.itdupan.bean.ResultBean;
import com.itdupan.pojo.Role;
import com.itdupan.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 添加
     *
     * @param role
     * @return
     */
    @PostMapping("addRole")
    public ResultBean<Void> addRole(@RequestBody Role role) {
        List<Role> list = roleService.findRolesByRoleName(role.getRoleName());
        if (list.size() >= 1) {
            return new ResultBean(600, "角色已存在，不允许重复添加！", null);
        }
        roleService.addRole(role);
        return new ResultBean(201, "添加成功！", null);
    }

    /**
     * 通过id删除
     *
     * @param roleId
     * @return
     */
    @DeleteMapping("delRoleById")
    public ResultBean<Void> delRoleById(@RequestParam("roleId") Long roleId) {
        try {
            roleService.delRoleById(roleId);
            System.out.println(roleId);
            return new ResultBean(204, "删除成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultBean(600, "删除失败，该角色可能被其它因素引用到！", null);
        }
    }

    /**
     * 更新
     *
     * @param role
     * @return
     */
    @PutMapping("updateRole")
    public ResultBean<Void> updateRole(@RequestBody Role role) {
        List<Role> list = roleService.findRolesByRoleName(role.getRoleName());
        if (list.size() >= 1) {
            return new ResultBean(600, "修改失败，角色已存在！", null);
        }
        roleService.updateRole(role);
        return new ResultBean(201, "修改成功！", null);
    }

    /**
     * 通过id查询
     *
     * @param roleId
     * @return
     */
    @GetMapping("findRoleById")
    public ResultBean<Role> getRoleById(@RequestParam("roleId") Long roleId) {
        Role role = roleService.findRoleById(roleId);
        if (role == null) {
            return new ResultBean(600, "角色不存在！", null);
        }
        return new ResultBean(200, "查询成功！", role);
    }

    /**
     * 查询所有
     *
     * @return
     */
    @GetMapping("findRoles")
    public ResultBean<List<Role>> findAll() {
        List<Role> list = roleService.findAll();
        return new ResultBean(200, "查询成功！", list);
    }

    /**
     * 分页查询
     *
     * @param page
     * @param rows
     * @param roleName
     * @return
     */
    @GetMapping("findRolesByPage")
    public ResultBean<PageResult<Role>> findRolesByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "roleName", required = false) String roleName) {
        PageResult<Role> rolesByPage = roleService.findRolesByPage(page, rows, roleName);
        return new ResultBean(200, "查询成功！", rolesByPage);
    }


    /**
     * 通过名称查询列表
     *
     * @param roleName
     * @return
     */
    @GetMapping("findRolesByRoleName")
    public ResultBean<List<Role>> findRolesByRoleName(@RequestParam("roleName") String roleName) {
        return new ResultBean(200, "查询成功！", roleService.findRolesByRoleName(roleName));
    }


}
