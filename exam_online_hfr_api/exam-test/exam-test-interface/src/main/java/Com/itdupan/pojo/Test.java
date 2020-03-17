package Com.itdupan.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.itdupan.pojo.Grade;
import com.itdupan.pojo.Subject;

import javax.persistence.*;
import java.util.Date;

@Table(name = "hfr_test")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long testId;

    private String testName;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date testBeforetime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date testAftertime;

    private Integer testTimesum;

    private Integer testSelectOneSum;

    private Integer testSelectMoreSum;

    private Integer testJudgeSum;

    private Integer testTopicsum;

    private Integer testSimpleSum;

    private Integer testMiddleSum;

    private Integer testDiffSum;

    private Integer testScores;

    private Integer testPass;

    private Date testAddtime;

    private Long fkTestGradeId;

    private Long fkTestSubjectId;

    private Grade fkGrade;

    private Subject fkSubject;

    private Integer testState;   //试卷状态，0待考 1正在考试中 2已考

    public Test() {
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Date getTestBeforetime() {
        return testBeforetime;
    }

    public void setTestBeforetime(Date testBeforetime) {
        this.testBeforetime = testBeforetime;
    }

    public Date getTestAftertime() {
        return testAftertime;
    }

    public void setTestAftertime(Date testAftertime) {
        this.testAftertime = testAftertime;
    }

    public Integer getTestTimesum() {
        return testTimesum;
    }

    public void setTestTimesum(Integer testTimesum) {
        this.testTimesum = testTimesum;
    }

    public Integer getTestSelectOneSum() {
        return testSelectOneSum;
    }

    public void setTestSelectOneSum(Integer testSelectOneSum) {
        this.testSelectOneSum = testSelectOneSum;
    }

    public Integer getTestSelectMoreSum() {
        return testSelectMoreSum;
    }

    public void setTestSelectMoreSum(Integer testSelectMoreSum) {
        this.testSelectMoreSum = testSelectMoreSum;
    }

    public Integer getTestJudgeSum() {
        return testJudgeSum;
    }

    public void setTestJudgeSum(Integer testJudgeSum) {
        this.testJudgeSum = testJudgeSum;
    }

    public Integer getTestTopicsum() {
        return testTopicsum;
    }

    public void setTestTopicsum(Integer testTopicsum) {
        this.testTopicsum = testTopicsum;
    }

    public Integer getTestSimpleSum() {
        return testSimpleSum;
    }

    public void setTestSimpleSum(Integer testSimpleSum) {
        this.testSimpleSum = testSimpleSum;
    }

    public Integer getTestMiddleSum() {
        return testMiddleSum;
    }

    public void setTestMiddleSum(Integer testMiddleSum) {
        this.testMiddleSum = testMiddleSum;
    }

    public Integer getTestDiffSum() {
        return testDiffSum;
    }

    public void setTestDiffSum(Integer testDiffSum) {
        this.testDiffSum = testDiffSum;
    }

    public Integer getTestScores() {
        return testScores;
    }

    public void setTestScores(Integer testScores) {
        this.testScores = testScores;
    }

    public Integer getTestPass() {
        return testPass;
    }

    public void setTestPass(Integer testPass) {
        this.testPass = testPass;
    }

    public Date getTestAddtime() {
        return testAddtime;
    }

    public void setTestAddtime(Date testAddtime) {
        this.testAddtime = testAddtime;
    }

    public Long getFkTestGradeId() {
        return fkTestGradeId;
    }

    public void setFkTestGradeId(Long fkTestGradeId) {
        this.fkTestGradeId = fkTestGradeId;
    }

    public Long getFkTestSubjectId() {
        return fkTestSubjectId;
    }

    public void setFkTestSubjectId(Long fkTestSubjectId) {
        this.fkTestSubjectId = fkTestSubjectId;
    }

    public Grade getFkGrade() {
        return fkGrade;
    }

    public void setFkGrade(Grade fkGrade) {
        this.fkGrade = fkGrade;
    }

    public Subject getFkSubject() {
        return fkSubject;
    }

    public void setFkSubject(Subject fkSubject) {
        this.fkSubject = fkSubject;
    }

    public Integer getTestState() {
        return testState;
    }

    public void setTestState(Integer testState) {
        this.testState = testState;
    }
}
