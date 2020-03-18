package com.itdupan.pojo;

public class UserInfo {

    private Long userId;

    private String userRealname;

    public UserInfo() {
    }

    public UserInfo(Long userId, String userRealname) {
        this.userId = userId;
        this.userRealname = userRealname;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserRealname() {
        return userRealname;
    }

    public void setUserRealname(String userRealname) {
        this.userRealname = userRealname;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId=" + userId +
                ", userRealname='" + userRealname + '\'' +
                '}';
    }
}