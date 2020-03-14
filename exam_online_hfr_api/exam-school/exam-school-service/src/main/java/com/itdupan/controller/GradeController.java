package com.itdupan.controller;

import com.itdupan.bean.PageResult;
import com.itdupan.bean.ResultBean;
import com.itdupan.pojo.Grade;
import com.itdupan.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("grade")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    /**
     * 添加
     *
     * @param grade
     * @return
     */
    @PostMapping("addGrade")
    public ResultBean<Void> addGrade(@RequestBody Grade grade) {
        List<Grade> list = gradeService.findGradesByGradeName(grade.getGradeName());
        if (list.size() >= 1) {
            return new ResultBean(600, "班级已存在，不允许重复添加！", null);
        }
        gradeService.addGrade(grade);
        return new ResultBean(201, "添加成功！", null);
    }

    /**
     * 通过id删除
     *
     * @param gradeId
     * @return
     */
    @DeleteMapping("delGradeById")
    public ResultBean<Void> delGradeById(@RequestParam("gradeId") Long gradeId) {
        try {
            gradeService.delGradeById(gradeId);
            System.out.println(gradeId);
            return new ResultBean(204, "删除成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultBean(600, "删除失败，该班级可能被其它因素引用到！", null);
        }
    }

    /**
     * 更新
     *
     * @param grade
     * @return
     */
    @PutMapping("updateGrade")
    public ResultBean<Void> updateGrade(@RequestBody Grade grade) {
        List<Grade> list = gradeService.findGradesByGradeName(grade.getGradeName());
        if (list.size() >= 1) {
            return new ResultBean(600, "修改失败，班级已存在！", null);
        }
        gradeService.updateGrade(grade);
        return new ResultBean(201, "修改成功！", null);
    }

    /**
     * 通过id查询
     *
     * @param gradeId
     * @return
     */
    @GetMapping("findGradeById")
    public ResultBean<Grade> getGradeById(@RequestParam("gradeId") Long gradeId) {
        Grade grade = gradeService.findGradeById(gradeId);
        if (grade == null) {
            return new ResultBean(600, "班级不存在！", null);
        }
        return new ResultBean(200, "查询成功！", grade);
    }

    /**
     * 查询所有
     *
     * @return
     */
    @GetMapping("findGrades")
    public ResultBean<List<Grade>> findAll() {
        List<Grade> list = gradeService.findAll();
        return new ResultBean(200, "查询成功！", list);
    }

    /**
     * 分页查询
     *
     * @param page
     * @param rows
     * @param gradeName
     * @return
     */
    @GetMapping("findGradesByPage")
    public ResultBean<PageResult<Grade>> findGradesByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "gradeName", required = false) String gradeName) {
        PageResult<Grade> gradesByPage = gradeService.findGradesByPage(page, rows, gradeName);
        return new ResultBean(200, "查询成功！", gradesByPage);
    }


    /**
     * 通过名称查询列表
     *
     * @param gradeName
     * @return
     */
    @GetMapping("findGradesByGradeName")
    public ResultBean<List<Grade>> findGradesByGradeName(@RequestParam("gradeName") String gradeName) {
        return new ResultBean(200, "查询成功！", gradeService.findGradesByGradeName(gradeName));
    }


}
