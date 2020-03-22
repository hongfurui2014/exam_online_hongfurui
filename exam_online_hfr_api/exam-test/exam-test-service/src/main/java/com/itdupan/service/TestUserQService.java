package com.itdupan.service;

import Com.itdupan.pojo.Test;
import Com.itdupan.pojo.TestUserQ;
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

    /**
     * 考生点开考卷开始考试时，增添考试信息
     *
     * @param testUserQ
     */
    public void addTestforUserQ(TestUserQ testUserQ) {
        testUserQMapper.insertSelective(testUserQ);
    }

    public TestUserQ findTestUserQByTestIdAndUserQId(Long testId, Long userId) {

        Example example = new Example(TestUserQ.class);

        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("fkTestId", testId);

        criteria.andEqualTo("fkUserQId", userId);

        List<TestUserQ> list = testUserQMapper.selectByExample(example);

        if(!CollectionUtils.isEmpty(list)){
            return list.get(0);
        }

        return null;
    }
}
