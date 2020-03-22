package com.itdupan.service;

import Com.itdupan.pojo.Test;
import Com.itdupan.pojo.TestTopic;
import Com.itdupan.pojo.Topic;
import com.itdupan.feign.UserQClient;
import com.itdupan.mapper.TestTopicMapper;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.validation.Valid;
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

    @Autowired
    private UserQClient userQClient;

    @Autowired
    private TestService testService;

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
    public List<List<Topic>> findTopicsByTestId(Long testId, Long userId) {

        //查询test的gradeId和user的gradeId是否相同，不同则不能考试
        Long grade1 = testService.findTestById(testId).getFkTestGradeId();

        Long grade2 = userQClient.findUserQById(userId).getData().getFkUserQGradeId();

        if(grade1 != grade2){
            return null;
        }

        Example example = new Example(TestTopic.class);

        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("fkTestId", testId);

        List<TestTopic> list = testTopicMapper.selectByExample(example);

        List<Topic> topicList = new ArrayList<>();

        for(TestTopic tt : list){
            Topic t = topicService.findTopicById(tt.getFkTopicId());
            topicList.add(t);
        }

        topicList.sort(new TopicSortCompator());

        //正确答案清空
        for(Topic t:topicList){
            t.setTopicYesanswer("");
        }

        List<List<Topic>> topics = new ArrayList<>();

        List<Topic> simples = new ArrayList<>();
        List<Topic> mores = new ArrayList<>();
        List<Topic> diffs = new ArrayList<>();

        for(Topic t: topicList){
            if(t.getTopicType() == 0){
                simples.add(t);
            }
            if(t.getTopicType() == 1){
                mores.add(t);
            }
            if(t.getTopicType() == 2){
                diffs.add(t);
            }
        }

        //简单 中等 高难题分别归类存放
        topics.add(simples);
        topics.add(mores);
        topics.add(diffs);

        return topics;
    }
}

class TopicSortCompator implements Comparator<Topic>{

    @Override
    public int compare(Topic o1, Topic o2) {
        return o1.getTopicLevel() - o2.getTopicLevel();
    }
}