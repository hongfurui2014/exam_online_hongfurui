package com.itdupan.controller;

import Com.itdupan.pojo.Topic;
import Com.itdupan.pojo.vo.UserQTopicQueryVo;
import com.itdupan.bean.ResultBean;
import com.itdupan.service.UserQTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
