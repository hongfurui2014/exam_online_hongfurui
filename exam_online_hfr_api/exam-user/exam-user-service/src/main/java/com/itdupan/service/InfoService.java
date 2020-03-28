package com.itdupan.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itdupan.bean.PageResult;
import com.itdupan.mapper.InfoMapper;
import com.itdupan.pojo.Info;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.provider.ExampleProvider;

import java.util.List;

@Service
@Transactional
public class InfoService {

    @Autowired
    private InfoMapper infoMapper;


    public PageResult<Info> findInfosByPage(Integer page, Integer rows, String infoUsername) {
        PageHelper.startPage(page, rows);

        Example example = new Example(Info.class);

        example.setOrderByClause("`info_id` DESC");

        Example.Criteria criteria = example.createCriteria();

        if(StringUtils.isNotBlank(infoUsername)){
            criteria.andEqualTo("infoUsername", infoUsername);
        }

        List<Info> list = infoMapper.selectByExample(example);

        PageInfo<Info> pageInfo = new PageInfo<>(list);

        return new PageResult<>(pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getList());
    }
}
