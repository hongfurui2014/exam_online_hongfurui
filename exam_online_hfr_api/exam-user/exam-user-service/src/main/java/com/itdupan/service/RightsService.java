package com.itdupan.service;

import com.itdupan.mapper.RightsMapper;
import com.itdupan.pojo.Rights;
import com.netflix.discovery.converters.Auto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RightsService {

    @Autowired
    private RightsMapper rightsMapper;

    /**
     * 查询所有菜单
     * @return
     */
    public List<Rights> findAll(){
        //先查出一级菜单
        List<Rights> list = rightsMapper.findRightsListByPid(0L); //一级菜单列表

        for (Rights r : list){
            r.setChildren(rightsMapper.findRightsListByPid(r.getRightsId()));   //设置二级菜单
        }

        for(Rights r : list){
            for(Rights r2: r.getChildren()){
                r2.setChildren(rightsMapper.findRightsListByPid(r2.getRightsId())); //设置二级菜单下的三级菜单
            }
        }

        return list;
        //return rightsMapper.findAll();
    }

    /**
     * 根据id查询一个权限
     * @param rightsId
     * @return
     */
    public Rights findRightsById(Long rightsId){
        return rightsMapper.findRightsById(rightsId);
    }
}
