package com.itdupan.controller;

import Com.itdupan.pojo.Topic;
import Com.itdupan.pojo.UserQTopic;
import Com.itdupan.pojo.vo.UserQTopicQueryVo;
import com.itdupan.bean.ResultBean;
import com.itdupan.service.UserQTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("userQTopic")
public class UserQTopicController {

    @Autowired
    private UserQTopicService userQTopicService;

    /**
     * 提交试卷
     * @param vo
     * @return
     */
    @PostMapping("commitTest")
    public ResultBean<Void> commitTest(@RequestBody UserQTopicQueryVo vo){
        List<List<Topic>> topics = vo.getTopicList();
        Long testId = vo.getTestId();
        Long userQId = vo.getUserQId();

        userQTopicService.commitTest(topics, testId, userQId);
        return new ResultBean<>(204, "更新成功", null);
    }

    /**
     * 根据testId和userQId查询该用户答过的所有试题
     * @param fkTestId
     * @param fkUserQId
     * @return
     */
    @GetMapping("findUserQTopicByTestIdAndUserQId")
    public ResultBean<List<UserQTopic>> findUserQTopicByTestIdAndUserQId(@RequestParam("fkTestId") Long fkTestId, @RequestParam("fkUserQId") Long fkUserQId){
        List<UserQTopic> list = userQTopicService.findUserQTopicByTestIdAndUserQId(fkTestId, fkUserQId);
        return new ResultBean<>(200, "查询成功", list);
    }
}
