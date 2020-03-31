<template>
  <div>
    <!-- 面包屑 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/admin' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>学校操作</el-breadcrumb-item>
      <el-breadcrumb-item>班级管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片 -->
    <el-card body-style="padding: 5px 10px;">
      <!-- 搜索表单 -->
      <el-form label-position="right" label-width="80px" :model="searchgradeForm" size="mini">
        <el-row :gutter="10">
          <el-col :xs="12" :sm="8" :md="6" :lg="6" :xl="4">
            <el-form-item label="班级名称">
              <el-input v-model="searchgradeForm.gradeName"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <!-- 按钮 -->
      <el-row :gutter="10" class="importexport" style="margin-left: 0px;">
        <el-button icon="el-icon-search" @click="searchGrade" type="success" size="mini">搜索</el-button>
        <el-button icon="el-icon-refresh" @click="clear" size="mini">清空搜索</el-button>
        <el-button
          icon="el-icon-circle-plus-outline"
          type="primary"
          @click="addGradeDialogVisible=true; addGradeForm.gradeName='';"
          size="mini"
        >添加班级</el-button>
      </el-row>
      <!-- 表格 -->
      <el-table :data="tableData" stripe border>
        <el-table-column type="index" label="#"></el-table-column>
        <el-table-column prop="gradeName" label="班级名称"></el-table-column>
        <el-table-column label="操作" width="130px;">
          <template v-slot:default="scope">
            <el-button
              @click="updateGrade(scope.row.gradeId)"
              icon="el-icon-edit"
              type="text"
              size="mini"
            >修改</el-button>
            <el-button
              icon="el-icon-delete"
              @click="deleteGrade(scope.row.gradeId)"
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
    <!-- 添加功能模态框 -->
    <el-dialog title="添加班级" :visible.sync="addGradeDialogVisible" width="300px">
      <el-form
        label-position="right"
        label-width="80px"
        :model="addGradeForm"
        size="mini"
        :rules="gradeFormRule"
        ref="addFormRef"
      >
        <el-row>
          <el-form-item label="班级名称" prop="gradeName">
            <el-input v-model="addGradeForm.gradeName"></el-input>
          </el-form-item>
        </el-row>
      </el-form>
      <!-- 确定取消按钮 -->
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="addGradeYes" size="mini">确 定</el-button>
        <el-button @click="addGradeDialogVisible = false" size="mini">取 消</el-button>
      </span>
    </el-dialog>
    <!-- 修改功能模态框 -->
    <el-dialog title="修改班级" :visible.sync="updateGradeDialogVisible" width="300px">
      <el-form
        label-position="right"
        label-width="80px"
        :model="updateGradeForm"
        size="mini"
        :rules="gradeFormRule"
        ref="updateFormRef"
      >
        <el-row>
          <el-form-item label="班级名称" prop="gradeName">
            <el-input v-model="updateGradeForm.gradeName"></el-input>
          </el-form-item>
        </el-row>
      </el-form>
      <!-- 确定取消按钮 -->
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="updateGradeYes" size="mini">确 定</el-button>
        <el-button @click="updateGradeDialogVisible = false" size="mini">取 消</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      gradeFormRule: {
        gradeName: [
          { required: true, message: "请输入班级名", trigger: "blur" }
        ]
      },
      //搜索表单
      searchgradeForm: {
        gradeName: ""
      },
      //修改表单
      updateGradeForm: {
        gradeId: 0,
        gradeName: ""
      },
      //表格数据
      tableData: [],
      //总条数
      total: 0,
      //分页查询参数
      queryInfo: {
        page: 1, //当前页数
        rows: 5, //每页显示条数
        gradeName: ""
      },
      //添加form
      addGradeForm: {
        gradeName: ""
      },
      //添加模态框是否显示
      addGradeDialogVisible: false,
      //修改模态框是否显示
      updateGradeDialogVisible: false
    };
  },
  //狗子函数，已加载完成
  mounted() {
    this.getGrades();
  },
  methods: {
    //搜索
    searchGrade() {
      this.queryInfo.page = 1;
      this.queryInfo.gradeName = this.searchgradeForm.gradeName;
      this.getGrades();
    },
    //清空搜索
    clear() {
      this.queryInfo.page = 1;
      this.queryInfo.gradeName = "";
      this.searchgradeForm.gradeName = "";
      this.getGrades();
    },
    //每页显示多少条改变
    handleSizeChange(newRows) {
      this.queryInfo.rows = newRows;
      this.getGrades();
    },
    //当前页码改变
    handleCurrentChange(newPage) {
      this.queryInfo.page = newPage;
      this.getGrades();
    },
    //分页获取表格数据
    getGrades() {
      this.$http
        .get("school/grade/findGradesByPage", {
          params: this.queryInfo
        })
        .then(response => {
          const res = response.data;
          if (res.httpCode === 200) {
            this.tableData = res.data.list;
            this.total = res.data.total;
          }
        })
        .catch(error => {
          this.$notify.error({
            title: "pre:AuthorizationFilter" ? "抱歉，您咱没有权限查看班级列表！" : error.respon.data.message
          });
        });
    },
    //添加
    addGradeYes() {
      this.$refs.addFormRef.validate(valid => {
        if (valid) {
          //校验通过
          this.$http
            .post("school/grade/addGrade", this.addGradeForm)
            .then(response => {
              const res = response.data;
              if (res.httpCode === 201) {
                this.addGradeForm.gradeName = "";
                this.addGradeDialogVisible = false;
                this.getGrades();
                this.$notify.success({
                  title: res.message
                });
              } else if (res.httpCode === 600) {
                this.addGradeForm.gradeName = "";
                this.$notify.error({
                  title: res.message
                });
              }
            })
            .catch(error => {
              this.$notify.error({
                title: "pre:AuthorizationFilter" ? "抱歉，您的权限暂未开放，请联系系统管理员！" : error.respon.data.message
              });
            });
        }
      });
    },
    //删除
    deleteGrade(gradeId) {
      this.$confirm("此操作将永久删除该班级, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          //删除
          this.$http
            .delete("school/grade/delGradeById", {
              params: {
                gradeId: gradeId
              }
            })
            .then(response => {
              const res = response.data;
              if (res.httpCode === 204) {
                this.$notify.success({
                  title: res.message
                });
                this.queryInfo.page = 1;
                this.getGrades();
              } else if (res.httpCode === 600) {
                this.$notify.error({
                  title: res.message
                });
              }
            })
            .catch(error => {
              this.$notify.error({
                title: "pre:AuthorizationFilter" ? "抱歉，您的权限暂未开放，请联系系统管理员！" : error.respon.data.message
              });
            });
        })
        .catch(() => {});
    },
    //修改
    updateGrade(gradeId) {
      this.$http
        .get("school/grade/findGradeById", {
          params: {
            gradeId: gradeId
          }
        })
        .then(response => {
          const res = response.data;
          if (res.httpCode === 200) {
            this.updateGradeForm.gradeId = gradeId;
            this.updateGradeForm.gradeName = res.data.gradeName;
            this.updateGradeDialogVisible = true;
          }
        })
        .catch(error => {});
    },
    //确认修改
    updateGradeYes() {
      this.$refs.updateFormRef.validate(valid => {
        if (valid) {
          //校验通过
          this.$http
            .put("school/grade/updateGrade", this.updateGradeForm)
            .then(response => {
              const res = response.data;
              if (res.httpCode === 201) {
                this.updateGradeDialogVisible = false;
                this.getGrades();
                this.$notify.success({
                  title: res.message
                });
              } else if (res.httpCode === 600) {
                this.$notify.error({
                  title: res.message
                });
              }
            })
            .catch(error => {
              this.$notify.error({
                title: "pre:AuthorizationFilter" ? "抱歉，您的权限暂未开放，请联系系统管理员！" : error.respon.data.message
              });
            });
        }
      });
    }
  }
};
</script>

<style lang='less' scoped>
</style>