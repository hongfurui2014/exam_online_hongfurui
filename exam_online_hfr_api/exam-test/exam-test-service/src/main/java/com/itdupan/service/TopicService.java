package com.itdupan.service;

import Com.itdupan.pojo.Topic;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itdupan.bean.PageResult;
import com.itdupan.bean.ResultBean;
import com.itdupan.enums.ExamExceptionEnum;
import com.itdupan.exception.ExamException;
import com.itdupan.feign.SubjectClient;
import com.itdupan.mapper.TopicMapper;
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
//    public List<Topic> findAll() {
//        List<Topic> list = topicMapper.selectAll();
//        if(!CollectionUtils.isEmpty(list)){
//            for (Topic topic : list){
//                ResultBean<Subject> res = subjectClient.findSubjectById(topic.getFkTopicSubjectId());
//                topic.setFkSubject(res.getData());
//            }
//        }
//        return list;
//    }

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

    /**
     * 上传导入试题
     * @param file
     * @param fkTopicSubjectId
     */
    public void uploadAndImportExcelTopic(MultipartFile file, long fkTopicSubjectId) throws Exception {
        //校验文件类型
        String contentType = file.getContentType();

        //只能是xlsx文件
        if(!contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")){
            throw new ExamException(ExamExceptionEnum.UPLOAD_EXCEL_FILE);
        }

        InputStream inputStream = file.getInputStream();

        //获取工作簿
        XSSFWorkbook xwb = new XSSFWorkbook(inputStream);

        //获取工作表
        XSSFSheet sheet = xwb.getSheetAt(0);

        int lastRowNum = sheet.getLastRowNum();
        for(int i =2; i <= lastRowNum; i++){
            XSSFRow row = sheet.getRow(i);
            if(row != null){
                //获取每一行
                XSSFCell titleCell = row.getCell(0);
                String title = titleCell.getStringCellValue();

                XSSFCell typeCell = row.getCell(1);
                Double type = typeCell.getNumericCellValue();

                XSSFCell answerCell = row.getCell(6);
                String answer = answerCell.getStringCellValue();

                XSSFCell scoreCell = row.getCell(7);
                Double score = scoreCell.getNumericCellValue();

                XSSFCell levelCell = row.getCell(8);
                Double level = levelCell.getNumericCellValue();

                //构建试题
                Topic topic = new Topic();

                if(type == 0.0 || type == 1.0){ //单选或多选
                    XSSFCell selectACell = row.getCell(2);
                    String selectA = selectACell.getStringCellValue();
                    topic.setTopicSelectA(selectA);

                    XSSFCell selectBCell = row.getCell(3);
                    String selectB = selectBCell.getStringCellValue();
                    topic.setTopicSelectB(selectB);

                    XSSFCell selectCCell = row.getCell(4);
                    String selectC = selectCCell.getStringCellValue();
                    topic.setTopicSelectC(selectC);

                    XSSFCell selectDCell = row.getCell(5);
                    String selectD = selectDCell.getStringCellValue();
                    topic.setTopicSelectD(selectD);
                }else if(type == 2.0){  //判断

                }

                topic.setTopicTitle(title);
                topic.setTopicType(type.intValue());
                topic.setTopicYesanswer(answer);
                topic.setTopicScore(score.intValue());
                topic.setTopicLevel(level.intValue());
                topic.setTopicAddtime(new Date());

                topic.setFkTopicSubjectId(fkTopicSubjectId);

                topicMapper.insertSelective(topic);
            }
        }

        //释放资源
        xwb.close();
    }
}
