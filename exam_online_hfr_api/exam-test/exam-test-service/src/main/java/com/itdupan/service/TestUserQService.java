package com.itdupan.service;

import Com.itdupan.pojo.TestUserQ;
import Com.itdupan.pojo.Topic;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itdupan.bean.PageResult;
import com.itdupan.feign.UserQClient;
import com.itdupan.mapper.TestUserQMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

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
     * 分页查询所有学生考试结果
     * @param page
     * @param rows
     * @param fkTestGradeId
     * @param fkTopicSubjectId
     * @return
     */
    public PageResult<TestUserQ> findTestUserQsByPage(Integer page, Integer rows, Long fkTestGradeId, Long fkTopicSubjectId) {
        PageHelper.startPage(page, rows);

        Example example = new Example(TestUserQ.class);

        Example.Criteria criteria = example.createCriteria();

        criteria.andLessThanOrEqualTo("testUserQLasttime", 0);

        List<TestUserQ> list = testUserQMapper.selectByExample(example);

        for(TestUserQ t : list){
            t.setFkTest(testService.findTestById(t.getFkTestId()));
            t.setFkUserQ(userQClient.findUserQById(t.getFkUserQId()).getData());
        }

        List<TestUserQ> list1 = new ArrayList<>();

        for(TestUserQ t : list){

            if(fkTestGradeId != null && fkTopicSubjectId == null){
                if(t.getFkTest().getFkTestGradeId() == fkTestGradeId){
                    list1.add(t);
                }
            }else if(fkTestGradeId == null && fkTopicSubjectId != null){
                if(t.getFkTest().getFkTestSubjectId() == fkTopicSubjectId){
                    list1.add(t);
                }
            }else if(fkTestGradeId != null && fkTopicSubjectId != null){
                if(t.getFkTest().getFkTestGradeId() == fkTestGradeId && t.getFkTest().getFkTestSubjectId() == fkTopicSubjectId){
                    list1.add(t);
                }
            }else{
                list1.add(t);
            }

        }

        PageInfo<TestUserQ> pageInfo = new PageInfo<>(list1);

        return new PageResult<>(pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getList());
    }

}
