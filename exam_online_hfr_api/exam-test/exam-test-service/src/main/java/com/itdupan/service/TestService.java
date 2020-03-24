package com.itdupan.service;

import Com.itdupan.pojo.Test;
import Com.itdupan.pojo.TestTopic;
import Com.itdupan.pojo.TestUserQ;
import Com.itdupan.pojo.Topic;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itdupan.bean.PageResult;
import com.itdupan.bean.ResultBean;
import com.itdupan.enums.ExamExceptionEnum;
import com.itdupan.exception.ExamException;
import com.itdupan.feign.GradeClient;
import com.itdupan.feign.SubjectClient;
import com.itdupan.mapper.TestMapper;
import com.itdupan.mapper.TestTopicMapper;
import com.itdupan.pojo.Grade;
import com.itdupan.pojo.Subject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class TestService {

    @Autowired
    private TestMapper testMapper;

    @Autowired
    private SubjectClient subjectClient;

    @Autowired
    private GradeClient gradeClient;

    @Autowired
    private TopicService topicService;

    @Autowired
    private TestTopicMapper testTopicMapper;

    @Autowired
    private TestUserQService testUserQService;

    /**
     * 抽组试题生成试卷
     *
     * @param test
     */
    public void addTest(Test test) {

        int selectOne = test.getTestSelectOneSum();
        int selectMore = test.getTestSelectMoreSum();
        int judge = test.getTestJudgeSum();

        Double floor1 = Math.floor(selectOne * 0.5);
        Double floor2 = Math.floor(selectOne * 0.3);
        Double floor3 = Math.floor(selectOne * 0.2);

        Double floor4 = Math.floor(selectMore * 0.5);
        Double floor5 = Math.floor(selectMore * 0.3);
        Double floor6 = Math.floor(selectMore * 0.2);

        Double floor7 = Math.floor(judge * 0.5);
        Double floor8 = Math.floor(judge * 0.3);
        Double floor9 = Math.floor(judge * 0.2);

        int onesimple = floor1.intValue(); //单选简单题数目
        int onemiddle = floor2.intValue(); //单选中等题数目
        int onediff = floor3.intValue(); //单选高难题数目

        int moresimple = floor4.intValue(); //多选简单题数目
        int moremiddle = floor5.intValue();
        int morediff = floor6.intValue();

        int judgesimple = floor7.intValue(); //判断简单题数目
        int judgemiddle = floor8.intValue();
        int judgediff = floor9.intValue();

        if(selectOne > (onesimple+onemiddle+onediff)){
            onesimple = selectOne - onemiddle - onediff;
        }

        if(selectMore > (moresimple+moremiddle+morediff)){
            moresimple = selectMore - moremiddle - morediff;
        }
        if(judge > (judgesimple+judgemiddle+judgediff)){
            judgesimple = judge - judgemiddle - judgediff;
        }

        List<Topic> topics = topicService.findAll();

        List<Topic> onesimpleList = new ArrayList<>();
        List<Topic> onemiddleList = new ArrayList<>();
        List<Topic> onediffList = new ArrayList<>();

        List<Topic> moresimpleList = new ArrayList<>();
        List<Topic> moremiddleList = new ArrayList<>();
        List<Topic> morediffList = new ArrayList<>();

        List<Topic> judgesimpleList = new ArrayList<>();
        List<Topic> judgemiddleList = new ArrayList<>();
        List<Topic> judgediffList = new ArrayList<>();

        //先将题库中所有符合条件的全部放入各自的list中
        if(!CollectionUtils.isEmpty(topics)){
            for (Topic t : topics){
                if(t.getFkSubject().getSubjectId() == test.getFkTestSubjectId() && t.getTopicType() == 0 && t.getTopicLevel() == 0){ //单选简单
                    onesimpleList.add(t);
                }
                if(t.getFkSubject().getSubjectId() == test.getFkTestSubjectId() && t.getTopicType() == 0 && t.getTopicLevel() == 1){
                    onemiddleList.add(t);
                }
                if(t.getFkSubject().getSubjectId() == test.getFkTestSubjectId() && t.getTopicType() == 0 && t.getTopicLevel() == 2){
                    onediffList.add(t);
                }

                if(t.getFkSubject().getSubjectId() == test.getFkTestSubjectId() && t.getTopicType() == 1 && t.getTopicLevel() == 0){
                    moresimpleList.add(t);
                }
                if(t.getFkSubject().getSubjectId() == test.getFkTestSubjectId() && t.getTopicType() == 1 && t.getTopicLevel() == 1){
                    moremiddleList.add(t);
                }
                if(t.getFkSubject().getSubjectId() == test.getFkTestSubjectId() && t.getTopicType() == 1 && t.getTopicLevel() == 2){
                    morediffList.add(t);
                }

                if(t.getFkSubject().getSubjectId() == test.getFkTestSubjectId() && t.getTopicType() == 2 && t.getTopicLevel() == 0){
                    judgesimpleList.add(t);
                }
                if(t.getFkSubject().getSubjectId() == test.getFkTestSubjectId() && t.getTopicType() == 2 && t.getTopicLevel() == 1){
                    judgemiddleList.add(t);
                }
                if(t.getFkSubject().getSubjectId() == test.getFkTestSubjectId() && t.getTopicType() == 2 && t.getTopicLevel() == 2){
                    judgediffList.add(t);
                }
            }
        }

        if(onesimpleList.size() < onesimple){
            throw new ExamException(ExamExceptionEnum.ONESIMPLE_LESS);
        }
        if(onemiddleList.size() < onemiddle){
            throw new ExamException(ExamExceptionEnum.ONEMIDDLE_LESS);
        }
        if(onediffList.size() < onediff){
            throw new ExamException(ExamExceptionEnum.ONEDIFF_LESS);
        }

        if(moresimpleList.size() < moresimple){
            throw new ExamException(ExamExceptionEnum.MORESIMPLE_LESS);
        }
        if(moremiddleList.size() < moremiddle){
            throw new ExamException(ExamExceptionEnum.MOREMIDDLE_LESS);
        }
        if(morediffList.size() < morediff){
            throw new ExamException(ExamExceptionEnum.MOREDIFF_LESS);
        }

        if(judgesimpleList.size() < judgesimple){
            throw new ExamException(ExamExceptionEnum.JUDGESIMPLE_LESS);
        }
        if(judgemiddleList.size() < judgemiddle){
            throw new ExamException(ExamExceptionEnum.JUDGEMIDDLE_LESS);
        }
        if(judgediffList.size() < judgediff){
            throw new ExamException(ExamExceptionEnum.JUDGEDIFF_LESS);
        }

        onesimpleList = getSubByRadom(onesimpleList, onesimple);
        onemiddleList = getSubByRadom(onemiddleList, onemiddle);
        onediffList = getSubByRadom(onediffList, onediff);

        moresimpleList = getSubByRadom(moresimpleList, moresimple);
        moremiddleList = getSubByRadom(moremiddleList, moremiddle);
        morediffList = getSubByRadom(morediffList, morediff);

        judgesimpleList = getSubByRadom(judgesimpleList, judgesimple);
        judgemiddleList = getSubByRadom(judgemiddleList, judgemiddle);
        judgediffList = getSubByRadom(judgediffList, judgediff);

        int sum = 0; //试卷总分

        test.setTestTopicsum(selectOne + selectMore + judge);
        test.setTestAddtime(new Date());
        testMapper.insertSelective(test);

        for (Topic t : onesimpleList){
            TestTopic tt = new TestTopic();
            tt.setFkTestId(test.getTestId());
            tt.setFkTopicId(t.getTopicId());
            sum += t.getTopicScore();
            testTopicMapper.insertSelective(tt);
        }
        for (Topic t : onemiddleList){
            TestTopic tt = new TestTopic();
            tt.setFkTestId(test.getTestId());
            tt.setFkTopicId(t.getTopicId());
            sum += t.getTopicScore();
            testTopicMapper.insertSelective(tt);
        }
        for (Topic t : onediffList){
            TestTopic tt = new TestTopic();
            tt.setFkTestId(test.getTestId());
            tt.setFkTopicId(t.getTopicId());
            sum += t.getTopicScore();
            testTopicMapper.insertSelective(tt);
        }
        for (Topic t : moresimpleList){
            TestTopic tt = new TestTopic();
            tt.setFkTestId(test.getTestId());
            tt.setFkTopicId(t.getTopicId());
            sum += t.getTopicScore();
            testTopicMapper.insertSelective(tt);
        }
        for (Topic t : moremiddleList){
            TestTopic tt = new TestTopic();
            tt.setFkTestId(test.getTestId());
            tt.setFkTopicId(t.getTopicId());
            sum += t.getTopicScore();
            testTopicMapper.insertSelective(tt);
        }
        for (Topic t : morediffList){
            TestTopic tt = new TestTopic();
            tt.setFkTestId(test.getTestId());
            tt.setFkTopicId(t.getTopicId());
            sum += t.getTopicScore();
            testTopicMapper.insertSelective(tt);
        }
        for (Topic t : judgesimpleList){
            TestTopic tt = new TestTopic();
            tt.setFkTestId(test.getTestId());
            tt.setFkTopicId(t.getTopicId());
            sum += t.getTopicScore();
            testTopicMapper.insertSelective(tt);
        }
        for (Topic t : judgemiddleList){
            TestTopic tt = new TestTopic();
            tt.setFkTestId(test.getTestId());
            tt.setFkTopicId(t.getTopicId());
            sum += t.getTopicScore();
            testTopicMapper.insertSelective(tt);
        }
        for (Topic t : judgediffList){
            TestTopic tt = new TestTopic();
            tt.setFkTestId(test.getTestId());
            tt.setFkTopicId(t.getTopicId());
            sum += t.getTopicScore();
            testTopicMapper.insertSelective(tt);
        }

        test.setTestSimpleSum(onesimple + moresimple + judgesimple);
        test.setTestMiddleSum(onemiddle + moremiddle + judgemiddle);
        test.setTestDiffSum(onediff + morediff + judgediff);

        test.setTestScores(sum);
        Double ceil = Math.ceil(sum * 0.6);
        test.setTestPass(ceil.intValue());

        testMapper.updateByPrimaryKeySelective(test);
    }

    /**
     * List中随机去count个元素
     * @param list
     * @param count
     * @return
     */
    public List<Topic> getSubByRadom(List<Topic> list, int count){
        List backList = null;
        backList = new ArrayList<Topic>();
        Random random = new Random();
        int backSum = 0;
        if (list.size() >= count) {
            backSum = count;
        }else {
            backSum = list.size();
        }
        for (int i = 0; i < backSum; i++) {
//			随机数的范围为0-list.size()-1
            int target = random.nextInt(list.size());
            backList.add(list.get(target));
            list.remove(target);
        }
        return backList;
    }

    /**
     * 通过主键删除
     *
     * @param testId
     */
    public void delTestById(Long testId) {
        testMapper.deleteByPrimaryKey(testId);
    }

    /**
     * 更新
     *
     * @param test
     */
    public void updateTest(Test test) {
        //数据库只会更新非null字段
        test.setTestAddtime(null);
        testMapper.updateByPrimaryKeySelective(test);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    public Test findTestById(Long id) {
        Test test = testMapper.selectByPrimaryKey(id);
        ResultBean<Subject> res = subjectClient.findSubjectById(test.getFkTestSubjectId());
        ResultBean<Grade> gradeRes = gradeClient.findGradeById(test.getFkTestGradeId());
        test.setFkSubject(res.getData());
        test.setFkGrade(gradeRes.getData());
        return test;
    }

    /**
     * 查询所有
     *
     * @return
     */
//    public List<Test> findAll() {
//        List<Test> list = testMapper.selectAll();
//        if(!CollectionUtils.isEmpty(list)){
//            for (Test test : list){
//                ResultBean<Subject> res = subjectClient.findSubjectById(test.getFkTestSubjectId());
//                test.setFkSubject(res.getData());
//            }
//        }
//        return list;
//    }

    /**
     * 分页查询
     * @param page
     * @param rows
     * @param fkTestGradeId
     * @param fkTestSubjectId
     * @return
     */
    public PageResult<Test> findTestsByPage(Integer page, Integer rows, Integer fkTestGradeId, Integer fkTestSubjectId) {

        PageHelper.startPage(page, rows);

        Example example = new Example(Test.class);

        Example.Criteria criteria = example.createCriteria();

        if(fkTestGradeId != null){
            criteria.andEqualTo("fkTestGradeId", fkTestGradeId);
        }

        if(fkTestSubjectId != null){
            criteria.andEqualTo("fkTestSubjectId", fkTestSubjectId);
        }

        List<Test> list = testMapper.selectByExample(example);

        if(!CollectionUtils.isEmpty(list)){
            for(Test t : list){
                t.setFkSubject(subjectClient.findSubjectById(t.getFkTestSubjectId()).getData());
                t.setFkGrade(gradeClient.findGradeById(t.getFkTestGradeId()).getData());

                if(new Date().before(t.getTestBeforetime())){
                    t.setTestState(0); //已考
                }else if((new Date().after(t.getTestBeforetime())) && (new Date().before(t.getTestAftertime()))){
                    t.setTestState(1); //正在考试中
                }else{
                    t.setTestState(2); //考试结束
                }

            }
        }

        PageInfo<Test> pageInfo = new PageInfo<>(list);

        return new PageResult<>(pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getList());
    }


    /**
     * 考生查看考试中心时，查出该考试能参加的考试
     * @param page
     * @param rows
     * @param fkTestGradeId
     * @param userQId
     * @return
     */
    public PageResult<Test> findTestsByPageByGradeIdAndUserQId(Integer page, Integer rows, Long fkTestGradeId, Long userQId) {
        PageHelper.startPage(page, rows);

        Example example = new Example(Test.class);

        Example.Criteria criteria = example.createCriteria();

        if(fkTestGradeId != null){
            criteria.andEqualTo("fkTestGradeId", fkTestGradeId);
        }


        List<Test> list = testMapper.selectByExample(example);

        if(!CollectionUtils.isEmpty(list)){
            for(Test t : list){
                t.setFkSubject(subjectClient.findSubjectById(t.getFkTestSubjectId()).getData());
                t.setFkGrade(gradeClient.findGradeById(t.getFkTestGradeId()).getData());

                if(new Date().before(t.getTestBeforetime())){
                    t.setTestState(0); //已考
                }else if((new Date().after(t.getTestBeforetime())) && (new Date().before(t.getTestAftertime()))){ //正在考试中，看看该用户有没有参加该考试
                    TestUserQ testUserQ = testUserQService.findTestUserQByTestIdAndUserQId(t.getTestId(), userQId);
                    if(testUserQ != null && testUserQ.getTestUserQLasttime() <= 0){
                        t.setTestState(3);  //该用户已参加完该场考试
                    }else{
                        t.setTestState(1);
                    }
                }else{
                    t.setTestState(2); //考试结束
                }

            }
        }

        PageInfo<Test> pageInfo = new PageInfo<>(list);

        return new PageResult<>(pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getList());
    }
}
