<template>
  <div>
    <el-card class="box-card">
      <el-table :data="tableData" stripe size="mini">
        <el-table-column prop="testName" label="考试名称"></el-table-column>
        <el-table-column label="考试时间" width="275px;">
          <template
            v-slot:default="scope"
          >{{scope.row.testBeforetime}} ~ {{scope.row.testAftertime}}</template>
        </el-table-column>
        <el-table-column prop="testTimesum" label="考试时长(分钟)" width="115px;"></el-table-column>
        <el-table-column prop="testTopicsum" label="题量" width="50px;"></el-table-column>
        <el-table-column prop="testScores" label="卷面总分" width="80px;"></el-table-column>
        <el-table-column prop="testPass" label="及格分数" width="80px;"></el-table-column>
        <el-table-column label="所属班级" width="120px;">
          <template v-slot:default="scope">{{scope.row.fkGrade.gradeName }}</template>
        </el-table-column>
        <el-table-column label="所属科目" width="120px;">
          <template v-slot:default="scope">{{scope.row.fkSubject.subjectName }}</template>
        </el-table-column>

        <el-table-column label="操作" width="100px;">
          <template v-slot:default="scope">
            <el-button
              v-if="scope.row.testState == 0"
              size="mini"
              type="warning"
              disabled
              round
            >暂未开始</el-button>
            <el-button
              v-if="scope.row.testState == 1"
              size="mini"
              type="success"
              round
            ><nuxt-link :to="{name: 'test-id', params: {id: scope.row.testId}}" style="text-decoration: none;color:#666;">进入考试</nuxt-link></el-button>
            <el-button v-if="scope.row.testState == 2" size="mini" disabled type="info" round>考试结束</el-button>
            <el-button v-if="scope.row.testState == 3" size="mini" disabled type="primary" round>答卷完毕</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页 -->
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.page"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="queryInfo.rows"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        background
      ></el-pagination>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      //表格数据
      tableData: [],
      //总条数
      total: 0,
      //分页查询参数
      queryInfo: {
        page: 1, //当前页数
        rows: 10, //每页显示条数
        grade: [],
        fkTestGradeId: ""
      },
      user: {
        userId: "",
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
          this.$router.push("/login");
          return;
        });

    //加载表格数据
    this.getTests();
  },
  methods: {
    //每页显示多少条改变
    handleSizeChange(newRows) {
      this.queryInfo.rows = newRows;
      this.getTests();
    },
    //当前页码改变
    handleCurrentChange(newPage) {
      this.queryInfo.page = newPage;
      this.getTests();
    },
    //分页获取表格数据
    async getTests() {
      const res =  await this.$http.get("auth/auth/verifyQ")
      this.user = res.data.data

      const res2 = await this.$http.get("user/userQ/findUserQById", {
        params:{
          userQId: res.data.data.userId
        }
      })

      this.$http
        .get("test/test/findTestsByPageByGradeIdAndUserQId", {
          params: {
            page: this.queryInfo.page,
            rows: this.queryInfo.rows,
            fkTestGradeId: res2.data.data.fkUserQGradeId,
            userQId: res.data.data.userId
          }
        })
        .then(response => {
          const res = response.data;
          if (res.httpCode === 200) {
            this.tableData = res.data.list;
            this.total = res.data.total;
          }
        })
        .catch(error => {
          console.log(error);
          this.$notify.error({
            title: error.response.data.message
          });
        });
    }
  }
};
</script>

<style lang='less' scoped>
.el-card {
  margin: 20px;
}
</style>