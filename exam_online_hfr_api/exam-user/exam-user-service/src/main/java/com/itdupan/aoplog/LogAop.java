package com.itdupan.aoplog;

import com.itdupan.bean.ResultBean;
import com.itdupan.pojo.UserInfo;
import com.itdupan.utils.CookieUtils;
import com.itdupan.utils.JwtUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
@Aspect
public class LogAop {

    private String userName;    //访问用户

    private Date visitTime;//操作时间

    private String ip;  //访问ip

    private String classAndMethod; //执行的类与方法

    private String describe;    //描述

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    /**
     * 前置通知：获取开始时间，执行的是哪一个类，执行的是哪一个方法
     *
     * @param jp
     */
    @Before("execution(* com.itdupan.controller.*.*(..))")
    public void doBefore(JoinPoint jp) {
        visitTime = new Date();

        String className = jp.getTarget().getClass().getName();
        String methodName = jp.getSignature().getName();

        classAndMethod = "[类名：]" + className + "[方法名：]" + methodName;

        describe = "描述";
    }

    /**
     * 后置通知
     *
     * @param jp
     */
    @After("execution(* com.itdupan.controller.*.*(..))")
    public void doAfter(JoinPoint jp) {

        //获取访问的ip
        ip = request.getRemoteAddr();

        //获取用户
        String hfr_token = CookieUtils.getCookieValue(request, "HFR_TOKEN");
//        try {
//            UserInfo userInfo = JwtUtils.getInfoFromToken(hfr_token, jwtProperties.getPublicKey());
//
//            System.out.println(userInfo);
//        } catch (Exception e) {
//        }

        //封装实体类

        System.out.println(userName + " - " + visitTime + " - " + ip + " - " + classAndMethod + " + " + describe);
    }
}
