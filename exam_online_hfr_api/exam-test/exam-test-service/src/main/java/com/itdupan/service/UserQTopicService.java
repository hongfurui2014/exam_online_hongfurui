package com.itdupan.service;

import Com.itdupan.pojo.TestUserQ;
import Com.itdupan.pojo.Topic;
import Com.itdupan.pojo.UserQTopic;
import com.itdupan.mapper.UserQTopicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserQTopicService {

    @Autowired
    private TopicService topicService;

    @Autowired
    private UserQTopicMapper userQTopicMapper;

    @Autowired
    private TestUserQService testUserQService;

    /**
     * 用户提交试卷
     * @param topics
     * @param testId
     * @param userQId
     */
    public void commitTest(List<List<Topic>> topics, Long testId, Long userQId) {
        int sum = 0;    //学生总得分

        for (List<Topic> list: topics){
            for (Topic t : list){
                if(topicService.findTopicById(t.getTopicId()).getTopicYesanswer().equals(t.getTopicYesanswer())){
                    sum += t.getTopicScore();
                }
                UserQTopic ut = new UserQTopic();
                ut.setUserQTopicUseranswer(t.getTopicYesanswer());
                ut.setFkTopicId(t.getTopicId());
                ut.setFkTestId(testId);
                ut.setFkUserQId(userQId);

                userQTopicMapper.insertSelective(ut);
            }
        }

        TestUserQ tu = testUserQService.findTestUserQByTestIdAndUserQId(testId, userQId);
        tu.setTestUserQLasttime(0);
        tu.setTestUserQCommittime(new Date());
        tu.setTestUserQScore(sum);

        System.out.println(tu);
        testUserQService.updateTestUserQ(tu);
    }
}
