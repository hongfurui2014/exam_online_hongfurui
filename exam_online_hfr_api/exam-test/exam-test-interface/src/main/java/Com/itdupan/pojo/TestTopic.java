package Com.itdupan.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "hfr_test_topic")
public class TestTopic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long testTopicId;

    private Long fkTestId;

    private Long fkTopicId;

    public TestTopic() {
    }

    public TestTopic(Long fkTestId, Long fkTopicId) {
        this.fkTestId = fkTestId;
        this.fkTopicId = fkTopicId;
    }

    public Long getTestTopicId() {
        return testTopicId;
    }

    public void setTestTopicId(Long testTopicId) {
        this.testTopicId = testTopicId;
    }

    public Long getFkTestId() {
        return fkTestId;
    }

    public void setFkTestId(Long fkTestId) {
        this.fkTestId = fkTestId;
    }

    public Long getFkTopicId() {
        return fkTopicId;
    }

    public void setFkTopicId(Long fkTopicId) {
        this.fkTopicId = fkTopicId;
    }


}
