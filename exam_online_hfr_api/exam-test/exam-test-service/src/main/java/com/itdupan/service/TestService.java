package com.itdupan.service;

import Com.itdupan.pojo.Test;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itdupan.bean.PageResult;
import com.itdupan.bean.ResultBean;
import com.itdupan.enums.ExamExceptionEnum;
import com.itdupan.exception.ExamException;
import com.itdupan.feign.SubjectClient;
import com.itdupan.mapper.TestMapper;
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
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TestService {

    @Autowired
    private TestMapper testMapper;

    @Autowired
    private SubjectClient subjectClient;

    /**
     * 添加
     *
     * @param test
     */
    public void addTest(Test test) {
        test.setTestAddtime(new Date());
        testMapper.insertSelective(test);
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
        test.setFkSubject(res.getData());
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
     * @param testType
     * @param testLevel
     * @param fkTestSubjectId
     * @return
     */
    public PageResult<Test> findTestsByPage(Integer page, Integer rows, Integer testType, Integer testLevel, Integer fkTestSubjectId) {

        PageHelper.startPage(page, rows);

        Example example = new Example(Test.class);
        Example.Criteria criteria = example.createCriteria();

        if(testType != null){
            criteria.andEqualTo("testType", testType);
        }

        if(testLevel != null){
            criteria.andEqualTo("testLevel", testLevel);
        }

        if(fkTestSubjectId != null){
            criteria.andEqualTo("fkTestSubjectId", fkTestSubjectId);
        }

        List<Test> list = testMapper.selectByExample(example);

        if(!CollectionUtils.isEmpty(list)){
            for(Test t : list){
                t.setFkSubject(subjectClient.findSubjectById(t.getFkTestSubjectId()).getData());
            }
        }

        PageInfo<Test> pageInfo = new PageInfo<>(list);

        return new PageResult<>(pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getList());
    }
}
