package com.itdupan.controller;

import com.itdupan.bean.PageResult;
import com.itdupan.bean.ResultBean;
import com.itdupan.pojo.Info;
import com.itdupan.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("info")
public class InfoController {

    @Autowired
    private InfoService infoService;

    /**
     * 分页查看后台操作日志
     * @param page
     * @param rows
     * @param infoUsername
     * @return
     */
    @GetMapping("findInfosByPage")
    public ResultBean<PageResult<Info>> findInfosByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "infoUsername", required = false) String infoUsername){

        PageResult<Info> pageResult = infoService.findInfosByPage(page, rows, infoUsername);

        if(pageResult == null){
            return new ResultBean<>(600, "查询失败！", null);
        }
        return new ResultBean(200, "查询成功！", pageResult);
    }
}
