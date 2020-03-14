package com.itdupan.pojo;

import javax.persistence.*;
import java.util.Date;

@Table(name = "hfr_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String userAccount;

    private String userRealname;

    @Transient
    private String userPassword;

    @Transient
    private String userSalt;

    private Date userAddtime;

    private Long fkUserGradeId;

    private Long fkUserRoleId;

    private Grade grade;

    private Role role;

    public User() {
    }

    public User(String userAccount, String userRealname, String userPassword, String userSalt, Date userAddtime, Long fkUserGradeId, Long fkUserRoleId, Grade grade, Role role) {
        this.userAccount = userAccount;
        this.userRealname = userRealname;
        this.userPassword = userPassword;
        this.userSalt = userSalt;
        this.userAddtime = userAddtime;
        this.fkUserGradeId = fkUserGradeId;
        this.fkUserRoleId = fkUserRoleId;
        this.grade = grade;
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserRealname() {
        return userRealname;
    }

    public void setUserRealname(String userRealname) {
        this.userRealname = userRealname;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserSalt() {
        return userSalt;
    }

    public void setUserSalt(String userSalt) {
        this.userSalt = userSalt;
    }

    public Date getUserAddtime() {
        return userAddtime;
    }

    public void setUserAddtime(Date userAddtime) {
        this.userAddtime = userAddtime;
    }

    public Long getFkUserGradeId() {
        return fkUserGradeId;
    }

    public void setFkUserGradeId(Long fkUserGradeId) {
        this.fkUserGradeId = fkUserGradeId;
    }

    public Long getFkUserRoleId() {
        return fkUserRoleId;
    }

    public void setFkUserRoleId(Long fkUserRoleId) {
        this.fkUserRoleId = fkUserRoleId;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userAccount='" + userAccount + '\'' +
                ", userRealname='" + userRealname + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userSalt='" + userSalt + '\'' +
                ", userAddtime=" + userAddtime +
                ", fkUserGradeId=" + fkUserGradeId +
                ", fkUserRoleId=" + fkUserRoleId +
                ", grade=" + grade +
                ", role=" + role +
                '}';
    }
}
