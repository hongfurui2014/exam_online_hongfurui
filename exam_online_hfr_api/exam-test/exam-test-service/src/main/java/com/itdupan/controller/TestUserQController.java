package com.itdupan.controller;

import Com.itdupan.pojo.Test;
import Com.itdupan.pojo.TestUserQ;
import com.itdupan.bean.ResultBean;
import com.itdupan.service.TestService;
import com.itdupan.service.TestUserQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("testUserQ")
public class TestUserQController {

    @Autowired
    private TestUserQService testUserQService;

    @Autowired
    private TestService testService;

    /**
     * 考生点开考卷开始考试时，增添考试信息
     * @param testId
     * @param userQId
     * @return
     */
    @PostMapping("addTestforUserQ")
    public ResultBean<Void> addTestforUserQ(@RequestParam("testId") Long testId, @RequestParam("userQId") Long userQId){

        Test test = testService.findTestById(testId);

        TestUserQ testUserQ = new TestUserQ();
        testUserQ.setFkTestId(testId);
        testUserQ.setFkUserQId(userQId);
        testUserQ.setTestUserQLasttime(test.getTestTimesum() * 60); //设置该考生参加该考试的剩余时间，单位为秒
        testUserQ.setTestUserQStarttime(new Date());
        testUserQ.setTestUserQCommittime(new Date());
        testUserQ.setTestUserQScore(0);

        testUserQService.addTestforUserQ(testUserQ);

        return new ResultBean<>(201, "新增成功！", null);
    }

    /**
     * 根据查找testId和userQid查找学生考试结果
     * @param testId
     * @param userQId
     * @return
     */
    @GetMapping("findTestUserQByTestIdAndUserQId")
    public ResultBean<TestUserQ> findTestUserQByTestIdAndUserQId(@RequestParam("testId") Long testId, @RequestParam("userQId") Long userQId){

        TestUserQ testUserQ = testUserQService.findTestUserQByTestIdAndUserQId(testId, userQId);

        if(testUserQ == null){
            return new ResultBean<>(600, "查询失败！", null);
        }
        return new ResultBean<>(200, "查询成功！", testUserQ);
    }

    /**
     * 根据查找userQid查找学生考试结果
     * @param userQId
     * @return
     */
    @GetMapping("findTestUserQByUserQId")
    public ResultBean<List<TestUserQ>> findTestUserQByUserQId(@RequestParam("userQId") Long userQId){

        List<TestUserQ> list = testUserQService.findTestUserQByUserQId(userQId);

        if(list == null){
            return new ResultBean<>(600, "查询失败！", null);
        }
        return new ResultBean<>(200, "查询成功！", list);
    }

    /**
     * 查询所有学生考试结果
     * @return
     */
    @GetMapping("findTestUserQs")
    public ResultBean<List<TestUserQ>> findTestUserQs(){

        List<TestUserQ> list = testUserQService.findTestUserQs();

        if(list == null){
            return new ResultBean<>(600, "查询失败！", null);
        }
        return new ResultBean<>(200, "查询成功！", list);
    }

    /**
     * 更新
     * @param testUserQ
     * @return
     */
    @PutMapping("updateTestUserQ")
    public ResultBean<Void> updateTestUserQ(@RequestBody TestUserQ testUserQ){
        testUserQService.updateTestUserQ(testUserQ);
        return new ResultBean<>(200, "更新成功！", null);
    }
}
