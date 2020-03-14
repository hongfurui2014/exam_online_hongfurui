package com.itdupan.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itdupan.bean.PageResult;
import com.itdupan.mapper.GradeMapper;
import com.itdupan.pojo.Grade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@Transactional
public class GradeService {

    @Autowired
    private GradeMapper gradeMapper;

    /**
     * 查询所有班级
     *
     * @return
     */
    public List<Grade> findAll() {
        return gradeMapper.selectAll();
    }

    /**
     * 通过id查询班级
     *
     * @param id
     * @return
     */
    public Grade findGradeById(Long id) {
        return gradeMapper.selectByPrimaryKey(id);
    }

    /**
     * 添加班级
     *
     * @param grade
     */
    public void addGrade(Grade grade) {
        gradeMapper.insertSelective(grade);
    }

    /**
     * 通过名称查询班级列表
     *
     * @param gradeName
     * @return
     */
    public List<Grade> findGradesByGradeName(String gradeName) {
        Example example = new Example(Grade.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("gradeName", gradeName);

        return gradeMapper.selectByExample(example);
    }

    /**
     * 分页查询班级
     *
     * @param page
     * @param rows
     * @param gradeName
     * @return
     */
    public PageResult<Grade> findGradesByPage(Integer page, Integer rows, String gradeName) {

        PageHelper.startPage(page, rows);

        Example example = new Example(Grade.class);
        Example.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotBlank(gradeName)) {
            criteria.andLike("gradeName", "%" + gradeName + "%");
        }

        List<Grade> list = gradeMapper.selectByExample(example);

        PageInfo<Grade> pageInfo = new PageInfo<>(list);
        System.out.println();

        return new PageResult<>(pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getList());
    }

    /**
     * 通过主键删除班级
     *
     * @param gradeId
     */
    public void delGradeById(Long gradeId) {
        gradeMapper.deleteByPrimaryKey(gradeId);
    }

    /**
     * 更新
     * @param grade
     */
    public void updateGrade(Grade grade){
        gradeMapper.updateByPrimaryKeySelective(grade);
    }
}
