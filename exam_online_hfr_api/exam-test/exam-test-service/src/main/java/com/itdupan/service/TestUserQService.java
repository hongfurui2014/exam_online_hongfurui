package com.itdupan.service;

import Com.itdupan.pojo.TestUserQ;
import com.itdupan.feign.UserQClient;
import com.itdupan.mapper.TestUserQMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@Transactional
public class TestUserQService {

    @Autowired
    private TestUserQMapper testUserQMapper;

    @Autowired
    private UserQClient userQClient;

    @Autowired
    private TestService testService;

    /**
     * 考生点开考卷开始考试时，增添考试信息
     *
     * @param testUserQ
     */
    public void addTestforUserQ(TestUserQ testUserQ) {
        testUserQMapper.insertSelective(testUserQ);
    }

    /**
     * 根据查找testId和userQid查找学生考试结果
     * @param testId
     * @param userQId
     * @return
     */
    public TestUserQ findTestUserQByTestIdAndUserQId(Long testId, Long userQId) {

        Example example = new Example(TestUserQ.class);

        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("fkTestId", testId);

        criteria.andEqualTo("fkUserQId", userQId);

        List<TestUserQ> list = testUserQMapper.selectByExample(example);

        if(!CollectionUtils.isEmpty(list)){
            list.get(0).setFkTest(testService.findTestById(testId));
            list.get(0).setFkUserQ(userQClient.findUserQById(userQId).getData());
            return list.get(0);
        }

        return null;
    }

    /**
     *
     * @param testUserQ
     */
    public void updateTestUserQ(TestUserQ testUserQ) {
        testUserQMapper.updateByPrimaryKeySelective(testUserQ);
    }

    /**
     * 根据查找userQid查找学生考试结果
     * @param userQId
     * @return
     */
    public List<TestUserQ> findTestUserQByUserQId(Long userQId) {
        Example example = new Example(TestUserQ.class);

        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("fkUserQId", userQId);
        criteria.andLessThanOrEqualTo("testUserQLasttime", 0);

        List<TestUserQ> list = testUserQMapper.selectByExample(example);

        for(TestUserQ t : list){
            t.setFkTest(testService.findTestById(t.getFkTestId()));
        }

        return list;
    }

    /**
     * 查询所有学生考试结果
     * @return
     */
    public List<TestUserQ> findTestUserQs() {
        Example example = new Example(TestUserQ.class);

        List<TestUserQ> list = testUserQMapper.selectByExample(example);

        for(TestUserQ t : list){
            t.setFkTest(testService.findTestById(t.getFkTestId()));
        }

        return list;
    }
}
