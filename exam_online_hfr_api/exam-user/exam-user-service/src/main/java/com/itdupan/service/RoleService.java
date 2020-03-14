package com.itdupan.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itdupan.bean.PageResult;
import com.itdupan.mapper.RoleMapper;
import com.itdupan.pojo.Role;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@Transactional
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 添加
     *
     * @param role
     */
    public void addRole(Role role) {
        roleMapper.insertSelective(role);
    }

    /**
     * 通过主键删除
     *
     * @param roleId
     */
    public void delRoleById(Long roleId) {
        roleMapper.deleteByPrimaryKey(roleId);
    }

    /**
     * 更新
     *
     * @param role
     */
    public void updateRole(Role role) {
        roleMapper.updateByPrimaryKeySelective(role);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    public Role findRoleById(Long id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有
     *
     * @return
     */
    public List<Role> findAll() {
        return roleMapper.selectAll();
    }

    /**
     * 分页查询
     *
     * @param page
     * @param rows
     * @param roleName
     * @return
     */
    public PageResult<Role> findRolesByPage(Integer page, Integer rows, String roleName) {

        PageHelper.startPage(page, rows);

        Example example = new Example(Role.class);
        Example.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotBlank(roleName)) {
            criteria.andLike("roleName", "%" + roleName + "%");
        }

        List<Role> list = roleMapper.selectByExample(example);

        PageInfo<Role> pageInfo = new PageInfo<>(list);
        System.out.println();

        return new PageResult<>(pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getList());
    }


    /**
     * 通过名称查询列表
     *
     * @param roleName
     * @return
     */
    public List<Role> findRolesByRoleName(String roleName) {
        Example example = new Example(Role.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("roleName", roleName);

        return roleMapper.selectByExample(example);
    }
}
