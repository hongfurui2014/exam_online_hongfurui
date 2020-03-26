package com.itdupan.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;

@Table(name = "hfr_user_q")
public class UserQ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userQId;

    @Length(min = 3, max = 16, message = "登录账户长度在 3 到 16 个字符")
    private String userQAccount;

    @Length(min = 2, max = 7, message = "真实姓名长度在 2 到 7 个字符")
    private String userQRealname;

//    @JsonIgnore
    @Length(min = 5, max = 20, message = "账户密码长度在 5 到 20 个字符")
    private String userQPassword;

//    @JsonIgnore
    private String userQSalt;

    private Date userQAddtime;

    private Long fkUserQGradeId;

    @Transient
    private Grade fkGradeQ;

    public UserQ() {
    }

    public UserQ(@Length(min = 3, max = 16, message = "登录账户长度在 3 到 16 个字符") String userQAccount, @Length(min = 2, max = 7, message = "真实姓名长度在 2 到 7 个字符") String userQRealname, @Length(min = 5, max = 20, message = "账户密码长度在 5 到 20 个字符") String userQPassword, String userQSalt, Date userQAddtime, Long fkUserQGradeId, Grade fkGradeQ) {
        this.userQAccount = userQAccount;
        this.userQRealname = userQRealname;
        this.userQPassword = userQPassword;
        this.userQSalt = userQSalt;
        this.userQAddtime = userQAddtime;
        this.fkUserQGradeId = fkUserQGradeId;
        this.fkGradeQ = fkGradeQ;
    }

    public Long getUserQId() {
        return userQId;
    }

    public void setUserQId(Long userQId) {
        this.userQId = userQId;
    }

    public String getUserQAccount() {
        return userQAccount;
    }

    public void setUserQAccount(String userQAccount) {
        this.userQAccount = userQAccount;
    }

    public String getUserQRealname() {
        return userQRealname;
    }

    public void setUserQRealname(String userQRealname) {
        this.userQRealname = userQRealname;
    }

    public String getUserQPassword() {
        return userQPassword;
    }

    public void setUserQPassword(String userQPassword) {
        this.userQPassword = userQPassword;
    }

    public String getUserQSalt() {
        return userQSalt;
    }

    public void setUserQSalt(String userQSalt) {
        this.userQSalt = userQSalt;
    }

    public Date getUserQAddtime() {
        return userQAddtime;
    }

    public void setUserQAddtime(Date userQAddtime) {
        this.userQAddtime = userQAddtime;
    }

    public Long getFkUserQGradeId() {
        return fkUserQGradeId;
    }

    public void setFkUserQGradeId(Long fkUserQGradeId) {
        this.fkUserQGradeId = fkUserQGradeId;
    }

    public Grade getFkGradeQ() {
        return fkGradeQ;
    }

    public void setFkGradeQ(Grade fkGradeQ) {
        this.fkGradeQ = fkGradeQ;
    }

    @Override
    public String toString() {
        return "UserQ{" +
                "userQId=" + userQId +
                ", userQAccount='" + userQAccount + '\'' +
                ", userQRealname='" + userQRealname + '\'' +
                ", userQPassword='" + userQPassword + '\'' +
                ", userQSalt='" + userQSalt + '\'' +
                ", userQAddtime=" + userQAddtime +
                ", fkUserQGradeId=" + fkUserQGradeId +
                ", fkGradeQ=" + fkGradeQ +
                '}';
    }
}
