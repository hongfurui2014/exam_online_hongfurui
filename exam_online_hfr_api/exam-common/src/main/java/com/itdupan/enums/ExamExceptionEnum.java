package com.itdupan.enums;

//自定义异常枚举
public enum ExamExceptionEnum {
    SYATEM_NOT_KNOW_EXCEPTION(500, "操作失败，系统未知异常，请尽快反馈给系统管理员！"),
    UPLOAD_EXCEL_FILE(500, "请上传excel文件！"),
    FILE_READ_FIAL(500, "文件读取失败！"),
    TOPIX_IMPORT_FIAL(500, "试题导入失败！"),
    ;
    private Integer httpCode;
    private String message;

    ExamExceptionEnum() {
    };

    ExamExceptionEnum(Integer httpCode, String message) {
        this.httpCode = httpCode;
        this.message = message;
    };

    public Integer getHttpCode() {
        return httpCode;
    }

    public String getMessage() {
        return message;
    }
}
