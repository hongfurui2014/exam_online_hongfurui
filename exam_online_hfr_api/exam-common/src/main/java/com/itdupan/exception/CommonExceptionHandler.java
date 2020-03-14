package com.itdupan.exception;

import com.itdupan.bean.ResultBean;
import com.itdupan.enums.ExamExceptionEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(ExamException.class) //捕获自定义异常
    public ResponseEntity<ExceptionResult> handleException(ExamException e){
        ExamExceptionEnum examExceptionEnum = e.getExamExceptionEnum();
        return ResponseEntity.status(examExceptionEnum.getHttpCode()).body(new ExceptionResult(e.getExamExceptionEnum()));
    }

    @ExceptionHandler(RuntimeException.class) //捕获所有RuntimeException异常
    public ResponseEntity<ExceptionResult> handleRuntimeException(RuntimeException e){
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResult(ExamExceptionEnum.SYATEM_NOT_KNOW_EXCEPTION));
    }

//    @ExceptionHandler(RuntimeException.class) //捕获所有RuntimeException异常
//    public @ResponseBody ResultBean handleRuntimeException(RuntimeException e){
//        e.printStackTrace();
//        return new ResultBean(500, "操作失败，系统未知异常，请尽快反馈给系统管理员！", null);
//    }
}
