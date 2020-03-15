package com.itdupan.api;

import com.itdupan.bean.ResultBean;
import com.itdupan.pojo.Grade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface GradeApi {

    /**
     * 通过id查询
     * @param gradeId
     * @return
     */
    @GetMapping("grade/findGradeById")
    public ResultBean<Grade> findGradeById(@RequestParam("gradeId") Long gradeId);
}
