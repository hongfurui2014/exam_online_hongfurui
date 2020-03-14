package com.itdupan.controller;

import com.itdupan.bean.PageResult;
import com.itdupan.bean.ResultBean;
import com.itdupan.bean.ResultBean2;
import com.itdupan.pojo.Grade;
import com.itdupan.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
@Controller
@RequestMapping("grade")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    /**
     * 通过id查询班级
     *
     * @param gradeId
     * @return
     */
//    @GetMapping("findGradeById")
//    public @ResponseBody
//    ResultBean getGradeById(@RequestParam("gradeId") Long gradeId) {
//        Grade grade = gradeService.findGradeById(gradeId);
//        if (grade == null) {
//            return new ResultBean(600, "用户不存在！", null);
//        }
//        return new ResultBean(200, "查询成功！", grade);
//    }

    @GetMapping("findGradeById")
    public @ResponseBody
    ResultBean2<Grade> getGradeById(@RequestParam("gradeId") Long gradeId) {
        Grade grade = gradeService.findGradeById(gradeId);
        if (grade == null) {
            return new ResultBean2(600, "用户不存在！", null);
        }
        return new ResultBean2(200, "查询成功！", grade);
    }

//    @GetMapping("findGradeById")
//    public ResponseEntity<Grade> getGradeById(@RequestParam("gradeId") Long gradeId) {
//        Grade grade = gradeService.findGradeById(gradeId);
//        if (grade == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(grade);
//    }

    /**
     * 更新班级
     *
     * @param grade
     * @return
     */
    @PutMapping("updateGrade")
    public @ResponseBody
    ResultBean updateGrade(@RequestBody Grade grade) {
        List<Grade> list = gradeService.findGradesByGradeName(grade.getGradeName());
        if (list.size() >= 1) {
            return new ResultBean(600, "修改失败，班级已存在！", null);
        }
        gradeService.updateGrade(grade);
        return new ResultBean(201, "修改成功！", null);
    }

    /**
     * 查询所有班级
     *
     * @return
     */
    @GetMapping("findGrades")
    public @ResponseBody
    ResultBean findAll() {
        List<Grade> list = gradeService.findAll();
        return new ResultBean(200, "查询成功！", list);
    }

    /**
     * 添加班级
     *
     * @param grade
     * @return
     */
    @PostMapping("addGrade")
    public @ResponseBody
    ResultBean addGrade(@RequestBody Grade grade) {
        List<Grade> list = gradeService.findGradesByGradeName(grade.getGradeName());
        if (list.size() >= 1) {
            return new ResultBean(600, "班级已存在，不允许重复添加！", null);
        }
        gradeService.addGrade(grade);
        return new ResultBean(201, "添加成功！", null);
    }

    /**
     * 通过名称查询班级列表
     *
     * @param gradeName
     * @return
     */
    @GetMapping("findGradesByGradeName")
    public @ResponseBody
    ResultBean findGradesByGradeName(@RequestParam("gradeName") String gradeName) {
        return new ResultBean(200, "查询成功！", gradeService.findGradesByGradeName(gradeName));
    }

    /**
     * 分页查询班级
     *
     * @param page
     * @param rows
     * @param gradeName
     * @return
     */
    @GetMapping("findGradesByPage")
    public @ResponseBody
    ResultBean findGradesByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "gradeName", required = false) String gradeName) {
        PageResult<Grade> gradesByPage = gradeService.findGradesByPage(page, rows, gradeName);
        return new ResultBean(200, "查询成功！", gradesByPage);
    }

    /**
     * 通过主键删除班级
     *
     * @param gradeId
     * @return
     */
    @DeleteMapping("delGradeById")
    public @ResponseBody
    ResultBean delGradeById(@RequestParam("gradeId") Long gradeId) {
        try {
            gradeService.delGradeById(gradeId);
            System.out.println(gradeId);
            return new ResultBean(204, "删除成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultBean(600, "删除失败，该班级可能被用户表或试卷表引用到！", null);
        }
    }
}
