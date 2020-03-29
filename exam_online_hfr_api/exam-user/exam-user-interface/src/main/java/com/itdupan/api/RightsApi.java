package com.itdupan.api;

import com.itdupan.bean.ResultBean;
import com.itdupan.pojo.Rights;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface RightsApi {

    /**
     * 查询所有菜单
     * @return
     */
    @GetMapping("rights/findAll")
    public ResultBean<List<Rights>> findAll();

    /**
     * 根据id查询一个权限
     * @param rightsId
     * @return
     */
    @GetMapping("rights/findRightsById")
    public ResultBean<Rights> findRightsById(@RequestParam("rightsId") Long rightsId);
}
