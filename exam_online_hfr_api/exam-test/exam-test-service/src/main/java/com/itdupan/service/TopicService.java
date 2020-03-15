package com.itdupan.service;

import Com.itdupan.pojo.Topic;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itdupan.bean.PageResult;
import com.itdupan.bean.ResultBean;
import com.itdupan.feign.SubjectClient;
import com.itdupan.mapper.TopicMapper;
import com.itdupan.pojo.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TopicService {

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private SubjectClient subjectClient;

    /**
     * 添加
     *
     * @param topic
     */
    public void addTopic(Topic topic) {
        topic.setTopicAddtime(new Date());
        topicMapper.insertSelective(topic);
    }

    /**
     * 通过主键删除
     *
     * @param topicId
     */
    public void delTopicById(Long topicId) {
        topicMapper.deleteByPrimaryKey(topicId);
    }

    /**
     * 更新
     *
     * @param topic
     */
    public void updateTopic(Topic topic) {
        //数据库只会更新非null字段
        topic.setTopicAddtime(null);
        topicMapper.updateByPrimaryKeySelective(topic);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    public Topic findTopicById(Long id) {
        Topic topic = topicMapper.selectByPrimaryKey(id);
        ResultBean<Subject> res = subjectClient.findSubjectById(topic.getFkTopicSubjectId());
        topic.setFkSubject(res.getData());
        return topic;
    }

    /**
     * 查询所有
     *
     * @return
     */
    public List<Topic> findAll() {
        List<Topic> list = topicMapper.selectAll();
        if(!CollectionUtils.isEmpty(list)){
            for (Topic topic : list){
                ResultBean<Subject> res = subjectClient.findSubjectById(topic.getFkTopicSubjectId());
                topic.setFkSubject(res.getData());
            }
        }
        return list;
    }

    /**
     * 分页查询
     * @param page
     * @param rows
     * @param topicType
     * @param topicLevel
     * @param fkTopicSubjectId
     * @return
     */
    public PageResult<Topic> findTopicsByPage(Integer page, Integer rows, Integer topicType, Integer topicLevel, Integer fkTopicSubjectId) {

        PageHelper.startPage(page, rows);

        Example example = new Example(Topic.class);
        Example.Criteria criteria = example.createCriteria();

        if(topicType != null){
            criteria.andEqualTo("topicType", topicType);
        }

        if(topicLevel != null){
            criteria.andEqualTo("topicLevel", topicLevel);
        }

        if(fkTopicSubjectId != null){
            criteria.andEqualTo("fkTopicSubjectId", fkTopicSubjectId);
        }

        List<Topic> list = topicMapper.selectByExample(example);

        if(!CollectionUtils.isEmpty(list)){
            for(Topic t : list){
                t.setFkSubject(subjectClient.findSubjectById(t.getFkTopicSubjectId()).getData());
            }
        }

        PageInfo<Topic> pageInfo = new PageInfo<>(list);

        return new PageResult<>(pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getList());
    }

}
