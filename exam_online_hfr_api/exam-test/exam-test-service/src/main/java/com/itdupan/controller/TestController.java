package com.itdupan.controller;

import Com.itdupan.pojo.Test;
import com.itdupan.bean.PageResult;
import com.itdupan.bean.ResultBean;
import com.itdupan.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private TestService testService;

    /**
     * 添加
     *
     * @param
     * @return
     */
    @PostMapping("addTest")
    public ResultBean<Void> addTest(@RequestBody Test test) {
        testService.addTest(test);
        return new ResultBean(201, "组卷成功！", null);
    }

    /**
     * 通过id删除
     *
     * @param testId
     * @return
     */
    @DeleteMapping("delTestById")
    public ResultBean<Void> delTestById(@RequestParam("testId") Long testId) {
        try {
            testService.delTestById(testId);
            return new ResultBean(204, "删除成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultBean(600, "删除失败，该试题可能被其它因素引用到！", null);
        }
    }

    /**
     * 更新
     *
     * @param test
     * @return
     */
    @PutMapping("updateTest")
    public ResultBean<Void> updateTest(@RequestBody Test test) {
        testService.updateTest(test);
        return new ResultBean(201, "修改成功！", null);
    }

    /**
     * 通过id查询
     *
     * @param testId
     * @return
     */
    @GetMapping("findTestById")
    public ResultBean<Test> findTestById(@RequestParam("testId") Long testId) {
        Test test = testService.findTestById(testId);
        if (test == null) {
            return new ResultBean(600, "试题不存在！", null);
        }
        return new ResultBean(200, "查询成功！", test);
    }

    /**
     * 查询所有
     *
     * @return
     */
//    @GetMapping("findTests")
//    public ResultBean<List<Test>> findTests() {
//        List<Test> list = testService.findAll();
//        return new ResultBean(200, "查询成功！", list);
//    }

    /**
     * 分页查询
     * @param page
     * @param rows
     * @param fkTestGradeId
     * @param fkTestSubjectId
     * @return
     */
    @GetMapping("findTestsByPage")
    public ResultBean<PageResult<Test>> findTestsByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "fkTestGradeId", required = false) Integer fkTestGradeId,
            @RequestParam(value = "fkTestSubjectId", required = false) Integer fkTestSubjectId){
        PageResult<Test> testsByPage = testService.findTestsByPage(page, rows, fkTestGradeId, fkTestSubjectId);
        return new ResultBean(200, "查询成功！", testsByPage);
    }

    /**
     * 考生查看考试中心时，查出该考试能参加的考试
     * @param page
     * @param rows
     * @param fkTestGradeId
     * @param userQId
     * @return
     */
    @GetMapping("findTestsByPageByGradeIdAndUserQId")
    public ResultBean<PageResult<Test>> findTestsByPage1(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam("fkTestGradeId") Long fkTestGradeId,
            @RequestParam("userQId") Long userQId){
        PageResult<Test> testsByPage = testService.findTestsByPageByGradeIdAndUserQId(page, rows, fkTestGradeId, userQId);
        return new ResultBean(200, "查询成功！", testsByPage);
    }
}
