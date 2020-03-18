package com.itdupan.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itdupan.bean.PageResult;
import com.itdupan.bean.ResultBean;
import com.itdupan.feign.GradeClient;
import com.itdupan.mapper.UserMapper;
import com.itdupan.pojo.Grade;
import com.itdupan.pojo.User;
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
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GradeClient gradeClient;

    @Autowired
    private RoleService roleService;

    /**
     * 添加
     *
     * @param user
     */
    public void addUser(User user) {
        user.setUserAddtime(new Date());
        //生成盐值
        String salt = CodecUtils.generateSalt();
        user.setUserSalt(salt);
        user.setUserPassword(CodecUtils.md5Hex(user.getUserPassword(), salt));
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
        //数据库只会更新非null字段
        user.setUserPassword(null);
        user.setUserAddtime(null);
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
        ResultBean<Grade> res = gradeClient.findGradeById(user.getFkUserGradeId());
        user.setFkGrade(res.getData());
        user.setFkRole(roleService.findRoleById(user.getFkUserRoleId()));
        return user;
    }

    /**
     * 查询所有
     *
     * @return
     */
    public List<User> findAll() {
        List<User> list = userMapper.selectAll();
        if(list.size() > 0){
            for (User user : list){
                ResultBean<Grade> res = gradeClient.findGradeById(user.getFkUserGradeId());
                user.setFkGrade(res.getData());
                user.setFkRole(roleService.findRoleById(user.getFkUserRoleId()));
            }
        }
        return list;
    }

    /**
     * 分页查询
     * @param page
     * @param rows
     * @param userAccount
     * @param userRealname
     * @param fkUserGradeId
     * @param fkUserRoleId
     * @return
     */
    public PageResult<User> findUsersByPage(Integer page, Integer rows, String userAccount, String userRealname, Integer fkUserGradeId, Integer fkUserRoleId) {

        PageHelper.startPage(page, rows);

        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotBlank(userAccount)) {
            criteria.andLike("userAccount", "%" + userAccount + "%");
        }

        if (StringUtils.isNotBlank(userRealname)) {
            criteria.andLike("userRealname", "%" + userRealname + "%");
        }

        if(fkUserGradeId != null){
            criteria.andEqualTo("fkUserGradeId", fkUserGradeId);
        }

        if(fkUserRoleId != null){
            criteria.andEqualTo("fkUserRoleId", fkUserRoleId);
        }

        List<User> list = userMapper.selectByExample(example);

        if(!CollectionUtils.isEmpty(list)){
            //为每个用户添加班级和角色
            for(User u : list){
                u.setFkGrade(gradeClient.findGradeById(u.getFkUserGradeId()).getData());
                u.setFkRole(roleService.findRoleById(u.getFkUserRoleId()));
            }
        }

        PageInfo<User> pageInfo = new PageInfo<>(list);

        return new PageResult<>(pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getList());
    }

    /**
     * 通过登录账户查询列表
     *
     * @param userAccount
     * @return
     */
    public List<User> findUsersByUserAccount(String userAccount) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("userAccount", userAccount);
        List<User> list = userMapper.selectByExample(example);
        if(list.size() > 0){
            for (User user : list){
                ResultBean<Grade> res = gradeClient.findGradeById(user.getFkUserGradeId());
                user.setFkGrade(res.getData());
                user.setFkRole(roleService.findRoleById(user.getFkUserRoleId()));
            }
        }
        return list;
    }

    /**
     * 通过真实姓名查询列表
     *
     * @param userRealname
     * @return
     */
    public List<User> findUsersByRealname(String userRealname) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("userRealname", userRealname);

        List<User> list = userMapper.selectByExample(example);
        if(list.size() > 0){
            for (User user : list){
                ResultBean<Grade> res = gradeClient.findGradeById(user.getFkUserGradeId());
                user.setFkGrade(res.getData());
                user.setFkRole(roleService.findRoleById(user.getFkUserRoleId()));
            }
        }
        return list;
    }

    /**
     * 根据用户名和密码查询
     * @param username
     * @param password
     * @return
     */
    public User login(String username, String password) {
        List<User> l = findUsersByUserAccount(username);
        if (CollectionUtils.isEmpty(l)){
            return null;
        }

        String salt = l.get(0).getUserSalt();
        String userPass = CodecUtils.md5Hex(password, salt);

        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("userPassword", userPass);

        List<User> list = userMapper.selectByExample(example);
        if(list.size() > 0){
            for (User user : list){
                ResultBean<Grade> res = gradeClient.findGradeById(user.getFkUserGradeId());
                user.setFkGrade(res.getData());
                user.setFkRole(roleService.findRoleById(user.getFkUserRoleId()));
            }
            return list.get(0);
        }
        return null;
    }
}
