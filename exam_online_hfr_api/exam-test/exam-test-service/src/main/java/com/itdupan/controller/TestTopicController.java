package com.itdupan.controller;

import Com.itdupan.pojo.Topic;
import com.itdupan.bean.ResultBean;
import com.itdupan.service.TestTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("testTopic")
public class TestTopicController {

    @Autowired
    private TestTopicService testTopicService;

    /**
     * 根据试卷id查询出该试卷下的所有试题，且按试题类型和难易程度排序
     * @param testId
     * @return
     */
    @GetMapping("findTopicsByTestId")
    public ResultBean<List<Topic>> findTopicsByTestId(@RequestParam("testId") Long testId){
        List<Topic> list = testTopicService.findTopicsByTestId(testId);
        return new ResultBean<>(200, "查询成功！", list);
    }
}
