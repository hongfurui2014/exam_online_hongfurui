<template>
  <div>
    <el-card class="box-card">
      <el-table :data="tableData" stripe size="mini">
        <el-table-column prop="fkTest.testName" label="试卷名称"></el-table-column>
        <el-table-column label="答卷时间" width="275px;">
          <template
            v-slot:default="scope"
          >{{scope.row.testUserQStarttime}} ~ {{scope.row.testUserQCommittime}}</template>
        </el-table-column>
        <el-table-column prop="fkTest.testScores" label="卷面总分" width="80px;"></el-table-column>
        <el-table-column prop="fkTest.testPass" label="及格分数" width="80px;"></el-table-column>
        <el-table-column prop="testUserQScore" label="我的成绩" width="80px;"></el-table-column>

        <el-table-column label="操作" width="100px;">
          <template v-slot:default="scope">
            <el-button
              type="success"
              plain
              round
              size="mini"
            ><nuxt-link :to="{name: 'reviewTest-id', params: {id: scope.row.fkTest.testId}}" style="text-decoration: none;color:#666;">回顾试卷</nuxt-link></el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      //表格数据
      tableData: [],
      user: {
        userQId: "",
        userRealname: ""
      }
    };
  },
  //钩子函数，已加载完成
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
        
    //加载表格数据
    this.getTests();
  },
  methods: {
    //获取该考生下的所有考卷，填充到表格中
    async getTests() {
      const res = await this.$http.get("auth/auth/verifyQ");
      this.user.userQId = res.data.data.userId;
      this.user.userRealname = res.data.data.userRealname;

      const res2 = await this.$http.get(
        "test/testUserQ/findTestUserQByUserQId",
        {
          params: {
            userQId: this.user.userQId
          }
        }
      );
      this.tableData = res2.data.data;
    },
    //回顾试卷
    reviewTest(testId) {
      console.log(testId);
    }
  }
};
</script>

<style lang='less' scoped>
.el-card {
  margin: 20px;
}
</style>