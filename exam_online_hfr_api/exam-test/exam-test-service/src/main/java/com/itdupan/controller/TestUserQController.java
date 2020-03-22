package com.itdupan.controller;

import Com.itdupan.pojo.Test;
import Com.itdupan.pojo.TestUserQ;
import com.itdupan.bean.ResultBean;
import com.itdupan.service.TestService;
import com.itdupan.service.TestUserQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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
     * @param userId
     * @return
     */
    @PostMapping("addTestforUserQ")
    public ResultBean<Void> addTestforUserQ(@RequestParam("testId") Long testId, @RequestParam("userId") Long userId){

        Test test = testService.findTestById(testId);

        TestUserQ testUserQ = new TestUserQ();
        testUserQ.setFkTestId(testId);
        testUserQ.setFkUserQId(userId);
        testUserQ.setTestUserQLasttime(test.getTestTimesum() * 60); //设置该考生参加该考试的剩余时间，单位为秒
        testUserQ.setTestUserQStarttime(new Date());

        testUserQService.addTestforUserQ(testUserQ);

        return new ResultBean<>(201, "新增成功！", null);
    }

    /**
     * 查找
     * @param testId
     * @param userId
     * @return
     */
    @GetMapping("findTestUserQByTestIdAndUserQId")
    public ResultBean<TestUserQ> findTestUserQByTestIdAndUserQId(@RequestParam("testId") Long testId, @RequestParam("userId") Long userId){

        TestUserQ testUserQ = testUserQService.findTestUserQByTestIdAndUserQId(testId, userId);

        if(testUserQ != null){
            return new ResultBean<>(200, "查询成功！", testUserQ);
        }

        return new ResultBean<>(600, "查询失败！", null);
    }
}
