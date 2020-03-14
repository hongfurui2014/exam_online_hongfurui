package com.itdupan.exception;

import com.itdupan.enums.ExamExceptionEnum;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExceptionResult {

    private Integer httpCode;

    private String message;

    private String exceptionTime;

    public ExceptionResult(ExamExceptionEnum em) {
        this.httpCode = em.getHttpCode();
        this.message = em.getMessage();
        this.exceptionTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public ExceptionResult() {
    }

    public Integer getHttpCode() {
        return httpCode;
    }

    public String getMessage() {
        return message;
    }

    public String getExceptionTime() {
        return exceptionTime;
    }
}
