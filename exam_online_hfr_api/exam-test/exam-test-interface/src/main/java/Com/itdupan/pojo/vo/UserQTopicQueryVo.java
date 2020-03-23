package Com.itdupan.pojo.vo;

import Com.itdupan.pojo.Topic;

import java.util.List;

public class UserQTopicQueryVo {

    private List<List<Topic>> topicList;

    private Long testId;

    private Long userQId;

    public List<List<Topic>> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<List<Topic>> topicList) {
        this.topicList = topicList;
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public Long getUserQId() {
        return userQId;
    }

    public void setUserQId(Long userQId) {
        this.userQId = userQId;
    }

    @Override
    public String toString() {
        return "UserQTopicQueryVo{" +
                "topicList=" + topicList +
                ", testId=" + testId +
                ", userQId=" + userQId +
                '}';
    }
}
