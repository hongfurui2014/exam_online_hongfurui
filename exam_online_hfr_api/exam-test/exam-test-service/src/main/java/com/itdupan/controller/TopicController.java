package com.itdupan.controller;

import Com.itdupan.pojo.Topic;
import com.itdupan.bean.PageResult;
import com.itdupan.bean.ResultBean;
import com.itdupan.enums.ExamExceptionEnum;
import com.itdupan.exception.ExamException;
import com.itdupan.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping("topic")
public class TopicController {

    @Autowired
    private TopicService topicService;

    /**
     * 添加
     *
     * @param
     * @return
     */
    @PostMapping("addTopic")
    public ResultBean<Void> addTopic(@RequestBody Topic topic) {
        topicService.addTopic(topic);
        return new ResultBean(201, "添加成功！", null);
    }

    /**
     * 通过id删除
     *
     * @param topicId
     * @return
     */
    @DeleteMapping("delTopicById")
    public ResultBean<Void> delTopicById(@RequestParam("topicId") Long topicId) {
        try {
            topicService.delTopicById(topicId);
            return new ResultBean(204, "id为["+topicId+"]的试题删除成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultBean(600, "删除失败，该试题可能被其它因素引用到！", null);
        }
    }

    /**
     * 更新
     *
     * @param topic
     * @return
     */
    @PutMapping("updateTopic")
    public ResultBean<Void> updateTopic(@RequestBody Topic topic) {
        System.out.println(topic);
        topicService.updateTopic(topic);
        return new ResultBean(201, "id为["+topic.getTopicId()+"]的试题修改成功！", null);
    }

    /**
     * 通过id查询
     *
     * @param topicId
     * @return
     */
    @GetMapping("findTopicById")
    public ResultBean<Topic> findTopicById(@RequestParam("topicId") Long topicId) {
        Topic topic = topicService.findTopicById(topicId);
        if (topic == null) {
            return new ResultBean(600, "试题不存在！", null);
        }
        return new ResultBean(200, "查询成功！", topic);
    }

    /**
     * 查询所有
     *
     * @return
     */
//    @GetMapping("findTopics")
//    public ResultBean<List<Topic>> findTopics() {
//        List<Topic> list = topicService.findAll();
//        return new ResultBean(200, "查询成功！", list);
//    }

    /**
     * 分页查询
     *
     * @param page
     * @param rows
     * @param topicType
     * @param topicLevel
     * @param fkTopicSubjectId
     * @return
     */
    @GetMapping("findTopicsByPage")
    public ResultBean<PageResult<Topic>> findTopicsByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "topicType", required = false) Integer topicType,
            @RequestParam(value = "topicLevel", required = false) Integer topicLevel,
            @RequestParam(value = "fkTopicSubjectId", required = false) Integer fkTopicSubjectId) {
        PageResult<Topic> topicsByPage = topicService.findTopicsByPage(page, rows, topicType, topicLevel, fkTopicSubjectId);
        return new ResultBean(200, "查询成功！", topicsByPage);
    }

    /**
     * 上传导入试题
     *
     * @param file
     * @param fkTopicSubjectId
     * @return
     */
    @PostMapping("upload")
    public ResultBean<Void> uploadAndImportExcelTopic(@RequestParam("file") MultipartFile file, @RequestParam("fkTopicSubjectId") Long fkTopicSubjectId) {
        if (fkTopicSubjectId == null) {
            return new ResultBean(600, "请为试题选择所属科目！", null);
        }
        try {
            topicService.uploadAndImportExcelTopic(file, fkTopicSubjectId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExamException(ExamExceptionEnum.TOPIX_IMPORT_FIAL);
        }
        return new ResultBean(201, "excel文件试题导入成功！", null);
    }

    @GetMapping("downloadExcel")
    public ResultBean<Void> downloadExcel(HttpServletResponse res) {
        File file = new File("D:\\导入试题模板.xlsx");

        res.setContentType("application/vnd.ms-excel;charset=utf-8");
//        res.setHeader("content-type", "application/octet-stream");
//        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = res.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(file));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return new ResultBean(200, "试题模板excel文件下载成功！", null);
    }
}
