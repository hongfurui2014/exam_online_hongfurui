package com.itdupan.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Table(name = "hfr_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Length(min = 3, max = 16, message = "登录账户长度在 3 到 16 个字符")
    private String userAccount;

    @Length(min = 2, max = 7, message = "真实姓名长度在 2 到 7 个字符")
    private String userRealname;

    //@JsonIgnore
    @Length(min = 5, max = 20, message = "账户密码长度在 5 到 20 个字符")
    private String userPassword;

    //@JsonIgnore
    private String userSalt;

    private Date userAddtime;

    private Long fkUserGradeId;

    private Long fkUserRoleId;

    @Transient
    private Grade fkGrade;

    @Transient
    private Role fkRole;

    public User() {
    }

    public User(String userAccount, String userRealname, String userPassword, String userSalt, Date userAddtime, Long fkUserGradeId, Long fkUserRoleId, Grade fkGrade, Role fkRole) {
        this.userAccount = userAccount;
        this.userRealname = userRealname;
        this.userPassword = userPassword;
        this.userSalt = userSalt;
        this.userAddtime = userAddtime;
        this.fkUserGradeId = fkUserGradeId;
        this.fkUserRoleId = fkUserRoleId;
        this.fkGrade = fkGrade;
        this.fkRole = fkRole;
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

    public Grade getFkGrade() {
        return fkGrade;
    }

    public void setFkGrade(Grade fkGrade) {
        this.fkGrade = fkGrade;
    }

    public Role getFkRole() {
        return fkRole;
    }

    public void setFkRole(Role fkRole) {
        this.fkRole = fkRole;
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
                ", fkGrade=" + fkGrade +
                ", fkRole=" + fkRole +
                '}';
    }
}
