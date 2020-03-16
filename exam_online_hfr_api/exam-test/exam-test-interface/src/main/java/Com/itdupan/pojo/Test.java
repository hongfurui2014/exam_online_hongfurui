package Com.itdupan.pojo;

import com.itdupan.pojo.Grade;
import com.itdupan.pojo.Subject;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "hfr_test")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long testId;

    private String testName;

    private Date testBeforetime;

    private Date testAftertime;

    private Integer testTimesum;

    private Integer testTopicsum;

    private Integer testScores;

    private Integer testPass;

    private Date testAddtime;

    private Long fkTestGradeId;

    private Long fkTestSubjectId;

    private Grade fkGrade;

    private Subject fkSubject;

    private String testState;   //试卷状态，"已考" "待考"

    public Test() {
    }

    public Test(String testName, Date testBeforetime, Date testAftertime, Integer testTimesum, Integer testTopicsum, Integer testScores, Integer testPass, Date testAddtime, Long fkTestGradeId, Long fkTestSubjectId, Grade fkGrade, Subject fkSubject, String testState) {
        this.testName = testName;
        this.testBeforetime = testBeforetime;
        this.testAftertime = testAftertime;
        this.testTimesum = testTimesum;
        this.testTopicsum = testTopicsum;
        this.testScores = testScores;
        this.testPass = testPass;
        this.testAddtime = testAddtime;
        this.fkTestGradeId = fkTestGradeId;
        this.fkTestSubjectId = fkTestSubjectId;
        this.fkGrade = fkGrade;
        this.fkSubject = fkSubject;
        this.testState = testState;
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

    public Integer getTestTopicsum() {
        return testTopicsum;
    }

    public void setTestTopicsum(Integer testTopicsum) {
        this.testTopicsum = testTopicsum;
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

    public String getTestState() {
        return testState;
    }

    public void setTestState(String testState) {
        this.testState = testState;
    }

    @Override
    public String toString() {
        return "Test{" +
                "testId=" + testId +
                ", testName='" + testName + '\'' +
                ", testBeforetime=" + testBeforetime +
                ", testAftertime=" + testAftertime +
                ", testTimesum=" + testTimesum +
                ", testTopicsum=" + testTopicsum +
                ", testScores=" + testScores +
                ", testPass=" + testPass +
                ", testAddtime=" + testAddtime +
                ", fkTestGradeId=" + fkTestGradeId +
                ", fkTestSubjectId=" + fkTestSubjectId +
                ", fkGrade=" + fkGrade +
                ", fkSubject=" + fkSubject +
                ", testState='" + testState + '\'' +
                '}';
    }
}
