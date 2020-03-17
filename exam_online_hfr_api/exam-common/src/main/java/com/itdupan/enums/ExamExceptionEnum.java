package com.itdupan.enums;

//自定义异常枚举
public enum ExamExceptionEnum {
    SYATEM_NOT_KNOW_EXCEPTION(500, "操作失败，系统未知异常，请尽快反馈给系统管理员！"),
    UPLOAD_EXCEL_FILE(500, "请上传excel文件！"),
    //FILE_READ_FIAL(500, "文件读取失败！"),
    TOPIX_IMPORT_FIAL(500, "试题导入失败！"),
    ONESIMPLE_LESS(500, "组卷失败，题库中所属该科目下的单选简单类型试题数量过少！"),
    ONEMIDDLE_LESS(500, "组卷失败，题库中所属该科目下的单选中等类型试题数量过少！"),
    ONEDIFF_LESS(500, "组卷失败，题库中所属该科目下的单选高等类型试题数量过少！"),

    MORESIMPLE_LESS(500, "组卷失败，题库中所属该科目下的多选简单类型试题数量过少！"),
    MOREMIDDLE_LESS(500, "组卷失败，题库中所属该科目下的多选中等类型试题数量过少！"),
    MOREDIFF_LESS(500, "组卷失败，题库中所属该科目下的多选高难类型试题数量过少！"),

    JUDGESIMPLE_LESS(500, "组卷失败，题库中所属该科目下的判断简单类型试题数量过少！"),
    JUDGEMIDDLE_LESS(500, "组卷失败，题库中所属该科目下的判断中等类型试题数量过少！"),
    JUDGEDIFF_LESS(500, "组卷失败，题库中所属该科目下的判断高难类型试题数量过少！"),
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
