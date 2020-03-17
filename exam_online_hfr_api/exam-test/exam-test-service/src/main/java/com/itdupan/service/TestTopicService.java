package com.itdupan.service;

import Com.itdupan.pojo.TestTopic;
import com.itdupan.mapper.TestTopicMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class TestTopicService {

    @Autowired
    private TestTopicMapper testTopicMapper;

    /**
     * 添加
     *
     * @param testTopic
     */
    public void addTopic(TestTopic testTopic) {
        testTopicMapper.insertSelective(testTopic);
    }
}
