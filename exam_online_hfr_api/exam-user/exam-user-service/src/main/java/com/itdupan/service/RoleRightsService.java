package com.itdupan.service;

import com.itdupan.mapper.RoleRightsMapper;
import com.itdupan.pojo.RoleRights;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RoleRightsService {

    @Autowired
    private RoleRightsMapper roleRightsMapper;


    /**
     * 根据角色id查询出该角色下的所有菜单
     * @param fkRoleId
     * @return
     */
    public List<Long> getRightsIdsByRoleId(Long fkRoleId) {
        List<RoleRights> list = roleRightsMapper.getRightsIdsByRoleId(fkRoleId);
        List<Long> list2 = new ArrayList<>();
        for (RoleRights rr: list){
            list2.add(rr.getFkRightsId());
        }
        return list2;
    }

    /**
     * 更新用户对应的权限菜单
     * @param fkRoleId
     * @param rightsList
     */
    public void updateRights(Long fkRoleId, List<Long> rightsList) {

        //先删除该用户下的所有权限菜单
        roleRightsMapper.delRolesAndRights(fkRoleId);

        //添加角色对应的权限
        for (Long rightsId: rightsList){
            roleRightsMapper.insertRoleAndRights(fkRoleId, rightsId);
        }
    }
}
