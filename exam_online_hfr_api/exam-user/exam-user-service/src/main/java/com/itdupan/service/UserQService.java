package com.itdupan.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itdupan.bean.PageResult;
import com.itdupan.bean.ResultBean;
import com.itdupan.feign.GradeClient;
import com.itdupan.mapper.UserQMapper;
import com.itdupan.pojo.Grade;
import com.itdupan.pojo.UserQ;
import com.itdupan.utils.CodecUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserQService {

    @Autowired
    private UserQMapper userQMapper;

    @Autowired
    private GradeClient gradeClient;

    /**
     * 添加
     *
     * @param userQ
     */
    public void addUserQ(UserQ userQ) {
        userQ.setUserQAddtime(new Date());
        //生成盐值
        String salt = CodecUtils.generateSalt();
        userQ.setUserQSalt(salt);
        userQ.setUserQPassword(CodecUtils.md5Hex(userQ.getUserQPassword(), salt));
        userQMapper.insertSelective(userQ);
    }

    /**
     * 通过主键删除
     *
     * @param userQId
     */
    public void delUserQById(Long userQId) {
        userQMapper.deleteByPrimaryKey(userQId);
    }

    /**
     * 更新
     *
     * @param userQ
     */
    public void updateUserQ(UserQ userQ) {
        //数据库只会更新非null字段
        userQ.setUserQPassword(null);
        userQ.setUserQAddtime(null);
        userQMapper.updateByPrimaryKeySelective(userQ);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    public UserQ findUserQById(Long id) {
        UserQ userQ = userQMapper.selectByPrimaryKey(id);
        ResultBean<Grade> res = gradeClient.findGradeById(userQ.getFkUserQGradeId());
        userQ.setFkGradeQ(res.getData());
        return userQ;
    }

    /**
     * 查询所有
     *
     * @return
     */
    public List<UserQ> findAll() {
        List<UserQ> list = userQMapper.selectAll();
        if(list.size() > 0){
            for (UserQ userQ : list){
                ResultBean<Grade> res = gradeClient.findGradeById(userQ.getFkUserQGradeId());
                userQ.setFkGradeQ(res.getData());
            }
        }
        return list;
    }

    /**
     * 分页查询
     * @param page
     * @param rows
     * @param userQAccount
     * @param userQRealname
     * @param fkUserQGradeId
     * @return
     */
    public PageResult<UserQ> findUserQsByPage(Integer page, Integer rows, String userQAccount, String userQRealname, Integer fkUserQGradeId) {

        PageHelper.startPage(page, rows);

        Example example = new Example(UserQ.class);
        Example.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotBlank(userQAccount)) {
            criteria.andLike("userQAccount", "%" + userQAccount + "%");
        }

        if (StringUtils.isNotBlank(userQRealname)) {
            criteria.andLike("userQRealname", "%" + userQRealname + "%");
        }

        if(fkUserQGradeId != null){
            criteria.andEqualTo("fkUserQGradeId", fkUserQGradeId);
        }

        List<UserQ> list = userQMapper.selectByExample(example);

        if(!CollectionUtils.isEmpty(list)){
            //为每个用户添加班级
            for(UserQ u : list){
                u.setFkGradeQ(gradeClient.findGradeById(u.getFkUserQGradeId()).getData());
            }
        }

        PageInfo<UserQ> pageInfo = new PageInfo<>(list);

        return new PageResult<>(pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getList());
    }

    /**
     * 通过登录账户查询列表
     *
     * @param userQAccount
     * @return
     */
    public List<UserQ> findUserQsByUserQAccount(String userQAccount) {
        Example example = new Example(UserQ.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("userQAccount", userQAccount);
        List<UserQ> list = userQMapper.selectByExample(example);
        if(list.size() > 0){
            for (UserQ userQ : list){
                ResultBean<Grade> res = gradeClient.findGradeById(userQ.getFkUserQGradeId());
                userQ.setFkGradeQ(res.getData());
            }
        }
        return list;
    }

    /**
     * 通过真实姓名查询列表
     *
     * @param userQRealname
     * @return
     */
    public List<UserQ> findUserQsByRealname(String userQRealname) {
        Example example = new Example(UserQ.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("userQRealname", userQRealname);

        List<UserQ> list = userQMapper.selectByExample(example);
        if(list.size() > 0){
            for (UserQ userQ : list){
                ResultBean<Grade> res = gradeClient.findGradeById(userQ.getFkUserQGradeId());
                userQ.setFkGradeQ(res.getData());
            }
        }
        return list;
    }

    /**
     * 根据用户名和密码查询
     * @param userQname
     * @param password
     * @return
     */
    public UserQ login(String userQname, String password) {
        List<UserQ> l = findUserQsByUserQAccount(userQname);
        if (CollectionUtils.isEmpty(l)){
            return null;
        }

        String salt = l.get(0).getUserQSalt();
        String userQPass = CodecUtils.md5Hex(password, salt);

        Example example = new Example(UserQ.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("userQPassword", userQPass);

        List<UserQ> list = userQMapper.selectByExample(example);
        if(list.size() > 0){
            for (UserQ userQ : list){
                ResultBean<Grade> res = gradeClient.findGradeById(userQ.getFkUserQGradeId());
                userQ.setFkGradeQ(res.getData());
            }
            return list.get(0);
        }
        return null;
    }
}
