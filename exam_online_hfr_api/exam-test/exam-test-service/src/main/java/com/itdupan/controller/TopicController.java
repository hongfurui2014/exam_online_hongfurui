package com.itdupan.controller;

import Com.itdupan.pojo.Topic;
import com.itdupan.bean.PageResult;
import com.itdupan.bean.ResultBean;
import com.itdupan.enums.ExamExceptionEnum;
import com.itdupan.exception.ExamException;
import com.itdupan.service.TopicService;
import com.mysql.jdbc.log.LogUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.net.http.HttpResponse;
import java.util.List;

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
            return new ResultBean(204, "删除成功！", null);
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
        return new ResultBean(201, "修改成功！", null);
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
            @RequestParam(value = "fkTopicSubjectId", required = false) Integer fkTopicSubjectId){
        PageResult<Topic> topicsByPage = topicService.findTopicsByPage(page, rows, topicType, topicLevel, fkTopicSubjectId);
        return new ResultBean(200, "查询成功！", topicsByPage);
    }

    /**
     * 上传导入试题
     * @param file
     * @param fkTopicSubjectId
     * @return
     */
    @PostMapping("upload")
    public ResultBean<Void> uploadAndImportExcelTopic(@RequestParam("file")MultipartFile file, @RequestParam("fkTopicSubjectId") Long fkTopicSubjectId){
        if(fkTopicSubjectId == null){
            return new ResultBean(600, "请为试题选择所属科目！", null);
        }
        try {
            topicService.uploadAndImportExcelTopic(file, fkTopicSubjectId);
        }catch (Exception e){
            e.printStackTrace();
            throw new ExamException(ExamExceptionEnum.TOPIX_IMPORT_FIAL);
        }
        return new ResultBean(201, "试题导入成功！", null);
    }

//    @PostMapping("downloadExcel")
//    public void downloadExcel(HttpServletResponse response) throws FileNotFoundException {
//        // 下载本地文件
//        String fileName = "导入试题模板.xlsx"; // 文件的默认保存名
//        // 读到流中
//        InputStream inStream = new FileInputStream("D:\\导入试题模板.xlsx");// 文件的存放路径
//        // 设置输出的格式
//        response.reset();
//        response.setCharacterEncoding("utf-8");
//        response.setContentType("application/vnd.ms-excel");
//        response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
//        // 循环取出流中的数据
//        byte[] b = new byte[100];
//        int len;
//        try {
//            while ((len = inStream.read(b)) > 0)
//                response.getOutputStream().write(b, 0, len);
//            inStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println("000");
//    }

    @GetMapping("downloadExcel")
    public void downloadExcel(HttpServletResponse response) throws FileNotFoundException {
        File file = new File("D:\\导入试题模板.xlsx");
        InputStream inputStream = null;
        ServletOutputStream servletOutputStream = null;
        // 重置response
        response.reset();
        //设置http头信息的内容
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename=\"" + "导入试题模板.xlsx" + "\"");
        int fileLength = (int) file.length();
        response.setContentLength(fileLength);

        try {
            if (fileLength != 0) {
                inputStream = new FileInputStream(file);
                byte[] buf = new byte[4096];
                // 创建输出流
                servletOutputStream = response.getOutputStream();
                int readLength;
                // 读取文件内容并输出到response的输出流中
                while ((readLength = inputStream.read(buf)) != -1) {
                    servletOutputStream.write(buf, 0, readLength);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("download file error");
        } finally {
            try {
                // 关闭ServletOutputStream
                if (servletOutputStream != null) {
                    servletOutputStream.flush();
                    servletOutputStream.close();
                }
                // 关闭InputStream
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
