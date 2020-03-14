package com.itdupan.exception;

import com.itdupan.enums.ExamExceptionEnum;

/**
 * 自定义异常
 */
public class ExamException extends RuntimeException{

    private ExamExceptionEnum examExceptionEnum;

    public ExamException(ExamExceptionEnum examExceptionEnum){
        this.examExceptionEnum = examExceptionEnum;
    }

    public ExamExceptionEnum getExamExceptionEnum() {
        return examExceptionEnum;
    }
}
