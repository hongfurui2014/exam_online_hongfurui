package com.itdupan.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itdupan.bean.PageResult;
import com.itdupan.mapper.SubjectMapper;
import com.itdupan.pojo.Subject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@Transactional
public class SubjectService {

    @Autowired
    private SubjectMapper subjectMapper;

    /**
     * 添加
     *
     * @param subject
     */
    public void addSubject(Subject subject) {
        subjectMapper.insertSelective(subject);
    }

    /**
     * 通过主键删除
     *
     * @param subjectId
     */
    public void delSubjectById(Long subjectId) {
        subjectMapper.deleteByPrimaryKey(subjectId);
    }

    /**
     * 更新
     *
     * @param subject
     */
    public void updateSubject(Subject subject) {
        subjectMapper.updateByPrimaryKeySelective(subject);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    public Subject findSubjectById(Long id) {
        return subjectMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有
     *
     * @return
     */
    public List<Subject> findAll() {
        return subjectMapper.selectAll();
    }

    /**
     * 分页查询
     *
     * @param page
     * @param rows
     * @param subjectName
     * @return
     */
    public PageResult<Subject> findSubjectsByPage(Integer page, Integer rows, String subjectName) {

        PageHelper.startPage(page, rows);

        Example example = new Example(Subject.class);
        Example.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotBlank(subjectName)) {
            criteria.andLike("subjectName", "%" + subjectName + "%");
        }

        List<Subject> list = subjectMapper.selectByExample(example);

        PageInfo<Subject> pageInfo = new PageInfo<>(list);
        System.out.println();

        return new PageResult<>(pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getList());
    }


    /**
     * 通过名称查询列表
     *
     * @param subjectName
     * @return
     */
    public List<Subject> findSubjectsBySubjectName(String subjectName) {
        Example example = new Example(Subject.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("subjectName", subjectName);

        return subjectMapper.selectByExample(example);
    }


}
