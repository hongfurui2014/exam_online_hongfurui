package Com.itdupan.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.itdupan.pojo.UserQ;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "hfr_test_user_q")
public class TestUserQ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long testUserQId;

    private Integer testUserQLasttime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date testUserQStarttime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date testUserQCommittime;

    private Integer testUserQScore;

    private Long fkTestId;

    private Test fkTest;

    private Long fkUserQId;

    private UserQ fkUserQ;

    public TestUserQ() {
    }

    public Long getTsetUserQId() {
        return testUserQId;
    }

    public void setTsetUserQId(Long tsetUserQId) {
        this.testUserQId = tsetUserQId;
    }

    public Integer getTestUserQLasttime() {
        return testUserQLasttime;
    }

    public void setTestUserQLasttime(Integer testUserQLasttime) {
        this.testUserQLasttime = testUserQLasttime;
    }

    public Date getTestUserQStarttime() {
        return testUserQStarttime;
    }

    public void setTestUserQStarttime(Date testUserQStarttime) {
        this.testUserQStarttime = testUserQStarttime;
    }

    public Date getTestUserQCommittime() {
        return testUserQCommittime;
    }

    public void setTestUserQCommittime(Date testUserQCommittime) {
        this.testUserQCommittime = testUserQCommittime;
    }

    public Integer getTestUserQScore() {
        return testUserQScore;
    }

    public void setTestUserQScore(Integer testUserQScore) {
        this.testUserQScore = testUserQScore;
    }

    public Long getFkTestId() {
        return fkTestId;
    }

    public void setFkTestId(Long fkTestId) {
        this.fkTestId = fkTestId;
    }

    public Test getFkTest() {
        return fkTest;
    }

    public void setFkTest(Test fkTest) {
        this.fkTest = fkTest;
    }

    public Long getFkUserQId() {
        return fkUserQId;
    }

    public void setFkUserQId(Long fkUserQId) {
        this.fkUserQId = fkUserQId;
    }

    public UserQ getFkUserQ() {
        return fkUserQ;
    }

    public void setFkUserQ(UserQ fkUserQ) {
        this.fkUserQ = fkUserQ;
    }

    @Override
    public String toString() {
        return "TestUserQ{" +
                "tsetUserQId=" + testUserQId +
                ", testUserQLasttime=" + testUserQLasttime +
                ", testUserQStarttime=" + testUserQStarttime +
                ", testUserQCommittime=" + testUserQCommittime +
                ", testUserQScore=" + testUserQScore +
                ", fkTestId=" + fkTestId +
                ", fkTest=" + fkTest +
                ", fkUserQId=" + fkUserQId +
                ", fkUserQ=" + fkUserQ +
                '}';
    }
}
