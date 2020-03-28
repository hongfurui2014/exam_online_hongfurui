<template>
  <div style="margin: 20px;">
    <el-card class="box-card">
        <el-card class="box-card topic" v-for="(topic, index) in topics" :key="topic.topicId" >
            <div slot="header">
                <span style="">第{{index + 1}}题：</span>
                <span style="">({{topic.fkTopic.topicScore}}分)</span>
                <span><el-tag type="success" size="mini" v-if="topic.fkTopic.topicLevel == 0">简单</el-tag></span>
                <span><el-tag type="warning" size="mini" v-if="topic.fkTopic.topicLevel == 1">中等</el-tag></span>
                <span><el-tag type="danger" size="mini" v-if="topic.fkTopic.topicLevel == 2">高难</el-tag></span>
                <span class="title">{{topic.fkTopic.topicTitle}}</span>
            </div>
            <!-- 单选题 -->
            <div><el-radio v-model="topic.userQTopicUseranswer" label="A" v-if="topic.fkTopic.topicType == 0 && topic.userQTopicUseranswer == 'A'">选项A：{{topic.fkTopic.topicSelectA}}</el-radio></div>
            <div><el-radio disabled v-model="topic.userQTopicUseranswer" label="A" v-if="topic.fkTopic.topicType == 0 && topic.userQTopicUseranswer != 'A'">选项A：{{topic.fkTopic.topicSelectA}}</el-radio></div>

            <div><el-radio v-model="topic.userQTopicUseranswer" label="B" v-if="topic.fkTopic.topicType == 0 && topic.userQTopicUseranswer == 'B'">选项B：{{topic.fkTopic.topicSelectB}}</el-radio></div>
            <div><el-radio disabled v-model="topic.userQTopicUseranswer" label="B" v-if="topic.fkTopic.topicType == 0 && topic.userQTopicUseranswer != 'B'">选项B：{{topic.fkTopic.topicSelectB}}</el-radio></div>

            <div><el-radio v-model="topic.userQTopicUseranswer" label="C" v-if="topic.fkTopic.topicType == 0 && topic.userQTopicUseranswer == 'C'">选项C：{{topic.fkTopic.topicSelectC}}</el-radio></div>
            <div><el-radio disabled v-model="topic.userQTopicUseranswer" label="C" v-if="topic.fkTopic.topicType == 0 && topic.userQTopicUseranswer != 'C'">选项C：{{topic.fkTopic.topicSelectC}}</el-radio></div>

            <div><el-radio v-model="topic.userQTopicUseranswer" label="D" v-if="topic.fkTopic.topicType == 0 && topic.userQTopicUseranswer == 'D'">选项D：{{topic.fkTopic.topicSelectD}}</el-radio></div>
            <div><el-radio disabled v-model="topic.userQTopicUseranswer" label="D" v-if="topic.fkTopic.topicType == 0 && topic.userQTopicUseranswer != 'D'">选项D：{{topic.fkTopic.topicSelectD}}</el-radio></div>
            
            <div style="margin-top: 10px; color: red;font-size: 14px; font-weight: bold;" v-if="topic.fkTopic.topicType == 0 && topic.userQTopicUseranswer != topic.fkTopic.topicYesanswer">我的答案：{{topic.userQTopicUseranswer}} | 正确答案： {{topic.fkTopic.topicYesanswer}}</div>
            <div style="margin-top: 10px; color: green;font-size: 14px; font-weight: bold;" v-if="topic.fkTopic.topicType == 0 && topic.userQTopicUseranswer == topic.fkTopic.topicYesanswer">我的答案：{{topic.userQTopicUseranswer}} | 正确答案： {{topic.fkTopic.topicYesanswer}}</div>
            <!-- 多选题 -->
            <el-checkbox-group v-model="topic.userQTopicUseranswer">
                <div><el-checkbox label="A" v-if="topic.fkTopic.topicType == 1 && topic.userQTopicUseranswer.join(',').search('A') != -1">选项A：{{topic.fkTopic.topicSelectA}}</el-checkbox></div>
                <div><el-checkbox disabled label="A" v-if="topic.fkTopic.topicType == 1 && topic.userQTopicUseranswer.join(',').search('A') == -1">选项A：{{topic.fkTopic.topicSelectA}}</el-checkbox></div>

                <div><el-checkbox label="B" v-if="topic.fkTopic.topicType == 1 && topic.userQTopicUseranswer.join(',').search('B') != -1">选项B：{{topic.fkTopic.topicSelectB}}</el-checkbox></div>
                <div><el-checkbox disabled label="B" v-if="topic.fkTopic.topicType == 1 && topic.userQTopicUseranswer.join(',').search('B') == -1">选项B：{{topic.fkTopic.topicSelectB}}</el-checkbox></div>

                <div><el-checkbox label="C" v-if="topic.fkTopic.topicType == 1 && topic.userQTopicUseranswer.join(',').search('C') != -1">选项B：{{topic.fkTopic.topicSelectC}}</el-checkbox></div>
                <div><el-checkbox disabled label="C" v-if="topic.fkTopic.topicType == 1 && topic.userQTopicUseranswer.join(',').search('C') == -1">选项B：{{topic.fkTopic.topicSelectC}}</el-checkbox></div>

                <div><el-checkbox label="D" v-if="topic.fkTopic.topicType == 1 && topic.userQTopicUseranswer.join(',').search('D') != -1">选项B：{{topic.fkTopic.topicSelectD}}</el-checkbox></div>
                <div><el-checkbox disabled label="D" v-if="topic.fkTopic.topicType == 1 && topic.userQTopicUseranswer.join(',').search('D') == -1">选项B：{{topic.fkTopic.topicSelectD}}</el-checkbox></div>
            </el-checkbox-group>

            <div style="margin-top: 10px; color: green;font-size: 14px; font-weight: bold;" v-if="topic.fkTopic.topicType == 1 && topic.userQTopicUseranswer == topic.fkTopic.topicYesanswer">我的答案：{{topic.userQTopicUseranswer.join(',')}} | 正确答案： {{topic.fkTopic.topicYesanswer}}</div>
            <div style="margin-top: 10px; color: red;font-size: 14px; font-weight: bold;" v-if="topic.fkTopic.topicType == 1 && topic.userQTopicUseranswer != topic.fkTopic.topicYesanswer">我的答案：{{topic.userQTopicUseranswer.join(',')}} | 正确答案： {{topic.fkTopic.topicYesanswer}}</div>
            <!-- 判断题 -->
            <div><el-radio v-model="topic.userQTopicUseranswer" label="对" v-if="topic.fkTopic.topicType == 2 && topic.userQTopicUseranswer == '对'">对</el-radio></div>
            <div><el-radio disabled v-model="topic.userQTopicUseranswer" label="对" v-if="topic.fkTopic.topicType == 2 && topic.userQTopicUseranswer != '对'">对</el-radio></div>

            <div><el-radio v-model="topic.userQTopicUseranswer" label="错" v-if="topic.fkTopic.topicType == 2 && topic.userQTopicUseranswer == '错'">错</el-radio></div>
            <div><el-radio disabled v-model="topic.userQTopicUseranswer" label="错" v-if="topic.fkTopic.topicType == 2 && topic.userQTopicUseranswer != '错'">错</el-radio></div>

            <div style="margin-top: 10px; color: red;font-size: 14px; font-weight: bold;" v-if="topic.fkTopic.topicType == 2 && topic.userQTopicUseranswer != topic.fkTopic.topicYesanswer">我的答案：{{topic.userQTopicUseranswer}} | 正确答案： {{topic.fkTopic.topicYesanswer}}</div>
            <div style="margin-top: 10px; color: green;font-size: 14px; font-weight: bold;" v-if="topic.fkTopic.topicType == 2 && topic.userQTopicUseranswer == topic.fkTopic.topicYesanswer">我的答案：{{topic.userQTopicUseranswer}} | 正确答案： {{topic.fkTopic.topicYesanswer}}</div>
            
        </el-card>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      tabPosition: "left",
      testId: this.$route.params.id,
      user: {
        userQId: "",
        userRealname: ""
      },
      topics: []
    };
  },
  mounted() {
    this.$http
        .get("auth/auth/verifyQ")
        .then(response => {
          const res = response.data;
          if (res.httpCode === 200) {
            this.$store.commit("setUser", res.data);
            this.$store.commit("setIsLogin", true);
          }else if (res.httpCode === 401) {
            this.$router.push("/login"); //转到登录页
            return;
          }
        })
        .catch(error => {
        });
        
    this.loadTopics();
  },
  methods: {
    //加载所有试题以及学生答案
    async loadTopics() {
      //获取当前用户
      const res = await this.$http.get("auth/auth/verifyQ");
      this.user.userQId = res.data.data.userId;
      this.user.userRealname = res.data.data.userRealname;

      const res2 = await this.$http.get(
        "test/userQTopic/findUserQTopicByTestIdAndUserQId",
        {
          params: {
            fkTestId: this.testId,
            fkUserQId: this.user.userQId
          }
        }
      );

      this.topics = res2.data.data;

      //处理一下用户的多项选择题所选的答案，将字符串转化为数组
      for(let i = 0; i < this.topics.length; i++){
          if(this.topics[i].fkTopic.topicType == 1){
              this.topics[i].userQTopicUseranswer = this.topics[i].userQTopicUseranswer.split(',')
          }
      }
    }
  }
};
</script>

<style lang='less' scoped>
.topic {
  margin: 5px 0px 20px 10px;
}

.title {
  font-weight: bolder;
  font-size: 15px;
}
</style>