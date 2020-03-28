<template>
  <div>
    <!-- 面包屑 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/admin' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>操作日志</el-breadcrumb-item>
      <el-breadcrumb-item>后台日志</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片 -->
    <el-card body-style="padding: 5px 10px;">
      <!-- 搜索表单 -->
      <el-form label-position="right" label-width="80px" :model="queryInfo" size="mini">
        <el-row :gutter="10">
          <el-col :xs="12" :sm="8" :md="6" :lg="6" :xl="4">
            <el-form-item label="操作用户">
              <el-select v-model="queryInfo.infoUsername" placeholder="- 请选择 -">
                <el-option
                  v-for="item in queryInfo.user"
                  :key="item.userId"
                  :label="item.userRealname"
                  :value="item.userRealname"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <!-- 按钮 -->
      <el-row :gutter="10" class="importexport" style="margin-left: 0px;">
        <el-button icon="el-icon-search" @click="searchTopic" type="success" size="mini">搜索</el-button>
        <el-button icon="el-icon-refresh" @click="clear" size="mini">清空搜索</el-button>
      </el-row>
      <!-- 表格 -->
      <el-table :data="tableData" stripe border class="topic">
        <el-table-column type="index" label="#"></el-table-column>
        <el-table-column prop="infoUsername" label="后台操作用户" width="120px;"></el-table-column>
        <el-table-column label="操作时间" width="170px;">
          <template v-slot:default="scope">
            {{ scope.row.infoVisittime | dateFormat}}
          </template>
        </el-table-column>
        <el-table-column prop="infoIp" label="访问ip" width="140px;"></el-table-column>
        <el-table-column label="描述" width="170px;">
            <template v-slot:default="scope">
                <div style="text-align: left;">
                    {{ scope.row.infoDescribe}}
                </div>
            </template>
        </el-table-column>
        <el-table-column label="访问类与方法">
            <template v-slot:default="scope">
                <div style="text-align: left;">
                    {{ scope.row.infoClassandmethod}}
                </div>
            </template>
        </el-table-column>
      </el-table>
      <!-- 分页 -->
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.page"
        :page-sizes="[50, 100, 150, 200, 400]"
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
        rows: 50, //每页显示条数
        infoUsername: "",
        user: []
      }
    };
  },
  //钩子函数，已加载完成
  mounted() {
    //为搜索表单添加后台用户列表
    this.getSearchUsers();
    //加载表格数据
    this.getInfos();
  },
  methods: {
    //为搜索表单添加班级列表
    getSearchUsers() {
      this.$http
        .get("user/user/findUsers")
        .then(response => {
          const res = response.data;
          if (res.httpCode === 200) {
            this.queryInfo.user = res.data;
          }
        })
        .catch(error => {
          console.log(error);
          this.$notify.error({
            title: error.response.data.message
          });
        });
    },
    //搜索
    searchTopic() {
      this.queryInfo.page = 1;
      this.getInfos();
    },
    //清空搜索
    clear() {
      this.queryInfo.page = 1;
      this.queryInfo.topicType = "";
      this.queryInfo.infoUsername = "";
      this.getInfos();
    },
    //每页显示多少条改变
    handleSizeChange(newRows) {
      this.queryInfo.rows = newRows;
      this.getTopics();
    },
    //当前页码改变
    handleCurrentChange(newPage) {
      this.queryInfo.page = newPage;
      this.getTopics();
    },
    //分页获取表格数据
    getInfos() {
      this.$http
        .get("user/info/findInfosByPage", {
          params: {
            page: this.queryInfo.page,
            rows: this.queryInfo.rows,
            infoUsername: this.queryInfo.infoUsername
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
.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
</style>