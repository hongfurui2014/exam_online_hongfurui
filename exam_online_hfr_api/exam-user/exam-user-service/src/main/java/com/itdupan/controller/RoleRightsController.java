package com.itdupan.controller;

import com.itdupan.bean.ResultBean;
import com.itdupan.service.RoleRightsService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("roleRights")
public class RoleRightsController {

    @Autowired
    private RoleRightsService roleRightsService;

    /**
     * 根据角色id查询出该角色下的所有菜单
     * @param fkRoleId
     * @return
     */
    @GetMapping("getRightsIdsByRoleId")
    public ResultBean<List<Long>> getRightsIdsByRoleId(@RequestParam("fkRoleId") Long fkRoleId){

        List<Long> list = roleRightsService.getRightsIdsByRoleId(fkRoleId);
        return new ResultBean<>(200, "查询成功！", list);
    }

    /**
     * 更新用户对应的权限菜单
     * @param rightsList
     * @return
     */
    @PutMapping("updateRights")
    public ResultBean<Void> updateRights(@RequestParam("fkRoleId") Long fkRoleId, @RequestParam("rightsList") List<Long> rightsList){
        roleRightsService.updateRights(fkRoleId, rightsList);
        return new ResultBean<>(201, "权限修改成功！", null);
    }
}
