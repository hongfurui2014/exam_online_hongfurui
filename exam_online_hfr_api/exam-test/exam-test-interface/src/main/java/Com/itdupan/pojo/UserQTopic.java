package Com.itdupan.pojo;

import com.itdupan.pojo.UserQ;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "hfr_user_q_topic")
public class UserQTopic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userQTopicId;

    private String userQTopicUseranswer;

    private Long fkTopicId;

    private Topic fkTopic;

    private Long fkTestId;

    private Test fkTest;

    private Long fkUserQId;

    private UserQ fkUserQ;

    public UserQTopic() {
    }

    public Long getUserQTopicId() {
        return userQTopicId;
    }

    public void setUserQTopicId(Long userQTopicId) {
        this.userQTopicId = userQTopicId;
    }

    public String getUserQTopicUseranswer() {
        return userQTopicUseranswer;
    }

    public void setUserQTopicUseranswer(String userQTopicUseranswer) {
        this.userQTopicUseranswer = userQTopicUseranswer;
    }

    public Long getFkTopicId() {
        return fkTopicId;
    }

    public void setFkTopicId(Long fkTopicId) {
        this.fkTopicId = fkTopicId;
    }

    public Topic getFkTopic() {
        return fkTopic;
    }

    public void setFkTopic(Topic fkTopic) {
        this.fkTopic = fkTopic;
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
        return "UserQTopic{" +
                "userQTopicId=" + userQTopicId +
                ", userQTopicUseranswer='" + userQTopicUseranswer + '\'' +
                ", fkTopicId=" + fkTopicId +
                ", fkTopic=" + fkTopic +
                ", fkTestId=" + fkTestId +
                ", fkTest=" + fkTest +
                ", fkUserQId=" + fkUserQId +
                ", fkUserQ=" + fkUserQ +
                '}';
    }
}
