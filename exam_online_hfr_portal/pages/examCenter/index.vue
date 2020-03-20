<template>
  <div>
    <el-card class="box-card">
      <el-table :data="tableData" stripe size="mini">
        <el-table-column prop="testName" label="考试名称"></el-table-column>
        <el-table-column label="考试时间">
          <template v-slot:default="scope">{{scope.row.testBeforetime}} ~ {{scope.row.testAftertime}}</template>
        </el-table-column>
        <el-table-column prop="testTimesum" label="考试时长(分钟)" width="120px;">
          
        </el-table-column>
        <el-table-column prop="testTopicsum" label="题量" width="50px;">
          
        </el-table-column>
        <el-table-column prop="testScores" label="卷面总分" width="80px;">
          
        </el-table-column>
        <el-table-column prop="testPass" label="及格分数" width="80px;">
          
        </el-table-column>
        <el-table-column label="所属科目" width="140px;">
          <template v-slot:default="scope">{{scope.row.fkSubject.subjectName }}</template>
        </el-table-column>

        <el-table-column label="操作" width="130px;">
          <template v-slot:default="scope">
            <el-button
              @click="updateTopic(scope.row.topicId)"
              icon="el-icon-edit"
              type="text"
              size="mini"
            >修改</el-button>
            <el-button
              icon="el-icon-delete"
              @click="deleteTopic(scope.row.topicId)"
              type="text"
              size="mini"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页 -->
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.page"
        :page-sizes="[5, 10, 20, 50, 100]"
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
        rows: 5, //每页显示条数
        grade: [],
        fkTestGradeId: "",
        subject: [],
        fkTestSubjectId: ""
      },
    };
  },
  //钩子函数，已加载完成
  mounted() {
    //加载表格数据
    this.getTests();
  },
  methods:{
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
    getTests() {
      this.$http
        .get("test/test/findTestsByPage", {
          params: {
            page: this.queryInfo.page,
            rows: this.queryInfo.rows,
            fkTestGradeId: this.queryInfo.fkTestGradeId,
            fkTestSubjectId: this.queryInfo.fkTestSubjectId
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
    },
  }
};
</script>

<style lang='less' scoped>
.el-card {
  margin: 20px 20px 0px 20px;
}
</style>