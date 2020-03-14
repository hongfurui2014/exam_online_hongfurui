package com.itdupan.bean;

public class ResultBean {

    private Integer httpCode; //响应状态码

    private String message;

    private Object data; //响应数据

    public ResultBean() {
    }

    public ResultBean(Integer httpCode, String message, Object data) {
        this.httpCode = httpCode;
        this.message = message;
        this.data = data;
    }

    public Integer getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(Integer httpCode) {
        this.httpCode = httpCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultBean{" +
                "httpCode=" + httpCode +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
