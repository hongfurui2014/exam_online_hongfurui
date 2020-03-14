package com.itdupan.api;

import com.itdupan.bean.ResultBean;
import com.itdupan.bean.ResultBean2;
import com.itdupan.pojo.Grade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public interface GradeApi {

    /**
     * 通过id查询班级
     *
     * @param gradeId
     * @return
     */
//    @GetMapping("grade/findGradeById")
//    public @ResponseBody
//    ResultBean getGradeById(@RequestParam("gradeId") Long gradeId);

//    @GetMapping("grade/findGradeById")
//    public Grade getGradeById(@RequestParam("gradeId") Long gradeId);

    @GetMapping("grade/findGradeById")
    public @ResponseBody
    ResultBean2<Grade> getGradeById(@RequestParam("gradeId") Long gradeId);
}
