package com.itdupan.service;

import com.itdupan.mapper.RightsMapper;
import com.itdupan.mapper.RoleRightsMapper;
import com.itdupan.pojo.Rights;
import com.itdupan.pojo.RoleRights;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class RoleRightsService {

    @Autowired
    private RoleRightsMapper roleRightsMapper;

    @Autowired
    private RightsService rightsService;

    /**
     * 根据角色id查询出该角色下的所有三级菜单ids供树形控件显示
     * @param fkRoleId
     * @return
     */
    public List<Long> getRightsIdsByRoleId(Long fkRoleId) {
        List<RoleRights> list = roleRightsMapper.getRightsIdsByRoleId(fkRoleId);
        List<Long> list2 = new ArrayList<>();
        for (RoleRights rr: list){
            if(rightsService.findRightsById(rr.getFkRightsId()).getRightsLevel() == 3){ //只有三级权限才让属性控件显示
                list2.add(rr.getFkRightsId());
            }
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

    /**
     * 根据角色id查询出该角色下的所有一级和二级菜单用于首页菜单显示
     * @param fkRoleId
     * @return
     */
    public List<Rights> getRightsByRoleId(Long fkRoleId) {

        //先查询出所有菜单
        List<Rights> allRights = rightsService.findAll();

        List<RoleRights> roleRights = roleRightsMapper.getRightsIdsByRoleId(fkRoleId);

        //角色所拥有的所有一级或二级菜单ids
        List<Long> rightsIds = new ArrayList<>();
        for(RoleRights rr : roleRights){
            if(rightsService.findRightsById(rr.getFkRightsId()).getRightsLevel() != 3){ //只要一级或二级菜单
                rightsIds.add(rr.getFkRightsId());
            }
        }

        rightsIds.sort(new Comparator<Long>() {
            @Override
            public int compare(Long o1, Long o2) {
                Long l = o1 - o2;
                return l.intValue();
            }
        });

        //组装role对应下的菜单数据结构

        //构建一级菜单
        List<Rights> oneLevelRights = new ArrayList<>();
        for (Rights r : allRights){
            if (rightsIds.contains(r.getRightsId()) && r.getRightsLevel() == 1){
                oneLevelRights.add(r);
            }
        }

        //构建一级菜单下的二级菜单
        for(Rights r1 : oneLevelRights){
            List<Rights> twoLevelRights = new ArrayList<>();
            for(Long id : rightsIds){

                if(rightsService.findRightsById(id).getRightsLevel() == 2 && rightsService.findRightsById(id).getFkPRightsId() == r1.getRightsId()){
                    twoLevelRights.add(rightsService.findRightsById(id));
                }
            }
            //将二级菜单挂到一级菜单下
            r1.setChildren(twoLevelRights);
        }

        return oneLevelRights;
    }
}
