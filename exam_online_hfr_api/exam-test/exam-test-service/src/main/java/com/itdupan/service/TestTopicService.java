package com.itdupan.service;

import Com.itdupan.pojo.TestTopic;
import Com.itdupan.pojo.Topic;
import com.itdupan.mapper.TestTopicMapper;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class TestTopicService {

    @Autowired
    private TestTopicMapper testTopicMapper;

    @Autowired
    private TopicService topicService;

    /**
     * 添加
     *
     * @param testTopic
     */
    public void addTopic(TestTopic testTopic) {
        testTopicMapper.insertSelective(testTopic);
    }

    /**
     * 根据试卷id查询出该试卷下的所有试题，且按试题类型和难易程度排序
     * @param testId
     * @return
     */
    public List<Topic> findTopicsByTestId(Long testId) {
        Example example = new Example(TestTopic.class);

        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("fkTestId", testId);

        List<TestTopic> list = testTopicMapper.selectByExample(example);

        List<Topic> topicList = new ArrayList<>();

        for(TestTopic tt : list){
            Topic t = topicService.findTopicById(tt.getFkTopicId());
            topicList.add(t);
        }

        //topicList.sort(new TopicSortByType());

        //topicList.sort(new TopicSortByLevel());

        for(Topic t:topicList){
            System.out.println(t.getTopicId() + "- - " + t.getTopicType() + " - " + t.getTopicLevel());
        }

        return topicList;
    }
}

class TopicSortByType implements Comparator<Topic>{

    @Override
    public int compare(Topic o1, Topic o2) {
        return o1.getTopicType() - o2.getTopicType();
    }
}

class TopicSortByLevel implements Comparator<Topic>{

    @Override
    public int compare(Topic o1, Topic o2) {
        return o1.getTopicLevel() - o2.getTopicLevel();
    }
}