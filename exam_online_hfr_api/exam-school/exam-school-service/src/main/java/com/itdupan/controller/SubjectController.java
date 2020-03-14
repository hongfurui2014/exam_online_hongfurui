package com.itdupan.controller;

import com.itdupan.bean.PageResult;
import com.itdupan.bean.ResultBean;
import com.itdupan.pojo.Subject;
import com.itdupan.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    /**
     * 添加
     *
     * @param subject
     * @return
     */
    @PostMapping("addSubject")
    public @ResponseBody
    ResultBean addSubject(@RequestBody Subject subject) {
        List<Subject> list = subjectService.findSubjectsBySubjectName(subject.getSubjectName());
        if (list.size() >= 1) {
            return new ResultBean(600, "科目已存在，不允许重复添加！", null);
        }
        subjectService.addSubject(subject);
        return new ResultBean(201, "添加成功！", null);
    }

    /**
     * 通过主键删除
     *
     * @param subjectId
     * @return
     */
    @DeleteMapping("delSubjectById")
    public @ResponseBody
    ResultBean delSubjectById(@RequestParam("subjectId") Long subjectId) {
        try {
            subjectService.delSubjectById(subjectId);
            System.out.println(subjectId);
            return new ResultBean(204, "删除成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultBean(600, "删除失败，该科目可能被试题表或试卷表引用到！", null);
        }
    }

    /**
     * 更新
     *
     * @param subject
     * @return
     */
    @PutMapping("updateSubject")
    public @ResponseBody
    ResultBean updateSubject(@RequestBody Subject subject) {
        List<Subject> list = subjectService.findSubjectsBySubjectName(subject.getSubjectName());
        if (list.size() >= 1) {
            return new ResultBean(600, "修改失败，科目已存在！", null);
        }
        subjectService.updateSubject(subject);
        return new ResultBean(201, "修改成功！", null);
    }

    /**
     * 通过id查询
     *
     * @param subjectId
     * @return
     */
    @GetMapping("findSubjectById")
    public @ResponseBody
    ResultBean getSubjectById(@RequestParam("subjectId") Long subjectId) {
        Subject subject = subjectService.findSubjectById(subjectId);
        if (subject == null) {
            return new ResultBean(600, "科目不存在！", null);
        }
        return new ResultBean(200, "查询成功！", subject);
    }

    /**
     * 查询所有
     *
     * @return
     */
    @GetMapping("findSubjects")
    public @ResponseBody
    ResultBean findAll() {
        List<Subject> list = subjectService.findAll();
        return new ResultBean(200, "查询成功！", list);
    }

    /**
     * 分页查询
     *
     * @param page
     * @param rows
     * @param subjectName
     * @return
     */
    @GetMapping("findSubjectsByPage")
    public @ResponseBody
    ResultBean findSubjectsByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "subjectName", required = false) String subjectName) {
        PageResult<Subject> subjectsByPage = subjectService.findSubjectsByPage(page, rows, subjectName);
        return new ResultBean(200, "查询成功！", subjectsByPage);
    }

    /**
     * 通过名称查询列表
     *
     * @param subjectName
     * @return
     */
    @GetMapping("findSubjectsBySubjectName")
    public @ResponseBody
    ResultBean findSubjectsBySubjectName(@RequestParam("subjectName") String subjectName) {
        return new ResultBean(200, "查询成功！", subjectService.findSubjectsBySubjectName(subjectName));
    }
}
