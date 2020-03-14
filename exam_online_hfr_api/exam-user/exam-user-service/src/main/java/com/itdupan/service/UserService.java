package com.itdupan.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itdupan.bean.PageResult;
import com.itdupan.bean.ResultBean;
import com.itdupan.bean.ResultBean2;
import com.itdupan.feign.GradeClient;
import com.itdupan.mapper.UserMapper;
import com.itdupan.pojo.Grade;
import com.itdupan.pojo.User;
import com.itdupan.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GradeClient gradeClient;

    /**
     * 添加
     *
     * @param user
     */
    public void addUser(User user) {
        userMapper.insertSelective(user);
    }

    /**
     * 通过主键删除
     *
     * @param userId
     */
    public void delUserById(Long userId) {
        userMapper.deleteByPrimaryKey(userId);
    }

    /**
     * 更新
     *
     * @param user
     */
    public void updateUser(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    public User findUserById(Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        //Grade grade = gradeClient.getGradeById(user.getFkUserGradeId());

        ResultBean2<Grade> res = gradeClient.getGradeById(user.getFkUserGradeId());

        System.out.println(res);
        System.out.println(res.getData());
        user.setGrade(res.getData());
        return user;
    }

    /**
     * 查询所有
     *
     * @return
     */
    public List<User> findAll() {
        return userMapper.selectAll();
    }

    /**
     * 分页查询
     *
     * @param page
     * @param rows
     * @param userName
     * @return
     */
    public PageResult<User> findUsersByPage(Integer page, Integer rows, String userName) {

        PageHelper.startPage(page, rows);

        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotBlank(userName)) {
            criteria.andLike("userName", "%" + userName + "%");
        }

        List<User> list = userMapper.selectByExample(example);

        PageInfo<User> pageInfo = new PageInfo<>(list);
        System.out.println();

        return new PageResult<>(pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getList());
    }


    /**
     * 通过名称查询列表
     *
     * @param userName
     * @return
     */
    public List<User> findUsersByUserName(String userName) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("userName", userName);

        return userMapper.selectByExample(example);
    }
}
