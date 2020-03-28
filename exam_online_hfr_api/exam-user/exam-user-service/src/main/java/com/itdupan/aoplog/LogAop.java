package com.itdupan.aoplog;

import com.itdupan.config.JwtProperties;
import com.itdupan.mapper.InfoMapper;
import com.itdupan.pojo.UserInfo;
import com.itdupan.utils.CookieUtils;
import com.itdupan.utils.JwtUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
@EnableConfigurationProperties(JwtProperties.class)
public class LogAop {

    private String userName;    //访问用户

    private Date visitTime;//操作时间

    private String ip;  //访问ip

    private String classAndMethod; //执行的类与方法

    private String describe;    //描述

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private InfoMapper infoMapper;

    private Map<String, String> infoMap = new HashMap<>(){
        {
            put("addUser", "添加后台用户");
            put("updateUser", "修改后台用户");
            put("delUserById", "[尝试]删除后台用户");
            put("addUserQ", "添加考试用户");
            put("updateUserQ", "修改考试用户");
            put("delUserQById", "[尝试]删除考试用户");
            put("addRole", "添加角色");
            put("updateRole", "修改角色");
            put("delRoleById", "[尝试]删除角色");
            put("updateRights", "修改角色对应的权限");
            put("upload", "excel导入试题");
            put("downloadExcel", "下载excel试题导入模板");
            put("updateTopic", "修改试题");
            put("delTopicById", "[尝试]删除试题");
            put("addTest", "自动抽题出卷");
            put("addGrade", "添加班级");
            put("updateGrade", "修改班级");
            put("delGradeById", "[尝试]删除班级");
            put("addSubject", "添加科目");
            put("updateSubject", "修改科目");
            put("delSubjectById", "[尝试]删除科目");

//            put("login", "后台用户登录");
//            put("logout", "后台用户退出登录");
//            put("loginQ", "前台考试用户登录");
//            put("logoutQ", "前台考试用户退出登录");

//            put("findUsersByPage", "查看后台用户列表");
//            put("findUserQsByPage", "查看考试用户列表");
//            put("findRolesByPage", "查看角色列表");
//            put("findTopicsByPage", "查看试题列表");
//            put("findTestsByPage", "查案试卷列表");
//            put("findTestUserQsByPage", "查看考试统计列表");
//            put("findGradesByPage", "查看班级列表");
//            put("findSubjectsByPage", "查看科目列表");
//            put("findInfosByPage", "查看后台重要日志列表");
        }
    };

    /**
     * 前置通知：获取开始时间，执行的是哪一个类，执行的是哪一个方法
     *
     * @param jp
     */
    @Before("execution(* com.itdupan.controller.*.*(..))")
    public void doBefore(JoinPoint jp) {
        String methodName = jp.getSignature().getName();

        if (infoMap.containsKey(methodName)){
            visitTime = new Date();

            String className = jp.getTarget().getClass().getName();

            classAndMethod = "[类名]" + className + " [方法名]" + methodName;

            describe = infoMap.get(methodName);

            //获取访问的ip
            ip = request.getRemoteAddr();

            try {
                //获取后台用户
                String hfr_token = CookieUtils.getCookieValue(request, "HFR_TOKEN");
                UserInfo userInfo = JwtUtils.getInfoFromToken(hfr_token, jwtProperties.getPublicKey());
                userName = userInfo.getUserRealname();
            } catch (Exception e) {
                userName = "{用户获取失败}";
            }

            //封装实体类
            infoMapper.addInfo(userName, visitTime, ip, classAndMethod, describe);
        }
    }
}
