package Com.itdupan.pojo;

import com.itdupan.pojo.Subject;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "hfr_topic")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long topicId;

    private String topicTitle;

    private Integer topicType;  //试题类型，0：单选，1：多选，2：判断

    private String topicSelectA;

    private String topicSelectB;

    private String topicSelectC;

    private String topicSelectD;

    private String topicYesanswer;

    private Integer topicScore;

    private Integer topicLevel;     //难易程度，0：简单，1：中等，2：高难

    private Date topicAddtime;

    private Long fkTopicSubjectId;

    private Subject fkSubject;

    public Topic() {
    }

    public Topic(String topicTitle, Integer topicType, String topicSelectA, String topicSelectB, String topicSelectC, String topicSelectD, String topicYesanswer, Integer topicScore, Integer topicLevel, Date topicAddtime, Long fkTopicSubjectId, Subject fkSubject) {
        this.topicTitle = topicTitle;
        this.topicType = topicType;
        this.topicSelectA = topicSelectA;
        this.topicSelectB = topicSelectB;
        this.topicSelectC = topicSelectC;
        this.topicSelectD = topicSelectD;
        this.topicYesanswer = topicYesanswer;
        this.topicScore = topicScore;
        this.topicLevel = topicLevel;
        this.topicAddtime = topicAddtime;
        this.fkTopicSubjectId = fkTopicSubjectId;
        this.fkSubject = fkSubject;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public String getTopicTitle() {
        return topicTitle;
    }

    public void setTopicTitle(String topicTitle) {
        this.topicTitle = topicTitle;
    }

    public Integer getTopicType() {
        return topicType;
    }

    public void setTopicType(Integer topicType) {
        this.topicType = topicType;
    }

    public String getTopicSelectA() {
        return topicSelectA;
    }

    public void setTopicSelectA(String topicSelectA) {
        this.topicSelectA = topicSelectA;
    }

    public String getTopicSelectB() {
        return topicSelectB;
    }

    public void setTopicSelectB(String topicSelectB) {
        this.topicSelectB = topicSelectB;
    }

    public String getTopicSelectC() {
        return topicSelectC;
    }

    public void setTopicSelectC(String topicSelectC) {
        this.topicSelectC = topicSelectC;
    }

    public String getTopicSelectD() {
        return topicSelectD;
    }

    public void setTopicSelectD(String topicSelectD) {
        this.topicSelectD = topicSelectD;
    }

    public String getTopicYesanswer() {
        return topicYesanswer;
    }

    public void setTopicYesanswer(String topicYesanswer) {
        this.topicYesanswer = topicYesanswer;
    }

    public Integer getTopicScore() {
        return topicScore;
    }

    public void setTopicScore(Integer topicScore) {
        this.topicScore = topicScore;
    }

    public Integer getTopicLevel() {
        return topicLevel;
    }

    public void setTopicLevel(Integer topicLevel) {
        this.topicLevel = topicLevel;
    }

    public Date getTopicAddtime() {
        return topicAddtime;
    }

    public void setTopicAddtime(Date topicAddtime) {
        this.topicAddtime = topicAddtime;
    }

    public Long getFkTopicSubjectId() {
        return fkTopicSubjectId;
    }

    public void setFkTopicSubjectId(Long fkTopicSubjectId) {
        this.fkTopicSubjectId = fkTopicSubjectId;
    }

    public Subject getFkSubject() {
        return fkSubject;
    }

    public void setFkSubject(Subject fkSubject) {
        this.fkSubject = fkSubject;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "topicId=" + topicId +
                ", topicTitle='" + topicTitle + '\'' +
                ", topicType=" + topicType +
                ", topicSelectA='" + topicSelectA + '\'' +
                ", topicSelectB='" + topicSelectB + '\'' +
                ", topicSelectC='" + topicSelectC + '\'' +
                ", topicSelectD='" + topicSelectD + '\'' +
                ", topicYesanswer='" + topicYesanswer + '\'' +
                ", topicScore=" + topicScore +
                ", topicLevel=" + topicLevel +
                ", topicAddtime=" + topicAddtime +
                ", fkTopicSubjectId=" + fkTopicSubjectId +
                ", fkSubject=" + fkSubject +
                '}';
    }
}
