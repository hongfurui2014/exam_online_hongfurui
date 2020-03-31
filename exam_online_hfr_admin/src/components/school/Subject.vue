<template>
  <div>
    <!-- 面包屑 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/admin' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>学校操作</el-breadcrumb-item>
      <el-breadcrumb-item>科目管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片 -->
    <el-card body-style="padding: 5px 10px;">
      <!-- 搜索表单 -->
      <el-form label-position="right" label-width="80px" :model="searchsubjectForm" size="mini">
        <el-row :gutter="10">
          <el-col :xs="12" :sm="8" :md="6" :lg="6" :xl="4">
            <el-form-item label="科目名称">
              <el-input v-model="searchsubjectForm.subjectName"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <!-- 按钮 -->
      <el-row :gutter="10" class="importexport" style="margin-left: 0px;">
        <el-button icon="el-icon-search" @click="searchSubject" type="success" size="mini">搜索</el-button>
        <el-button icon="el-icon-refresh" @click="clear" size="mini">清空搜索</el-button>
        <el-button
          icon="el-icon-circle-plus-outline"
          type="primary"
          @click="addSubjectDialogVisible=true; addSubjectForm.subjectName='';"
          size="mini"
        >添加科目</el-button>
      </el-row>
      <!-- 表格 -->
      <el-table :data="tableData" stripe border>
        <el-table-column type="index" label="#"></el-table-column>
        <el-table-column prop="subjectName" label="科目名称"></el-table-column>
        <el-table-column label="操作" width="130px;">
          <template v-slot:default="scope">
            <el-button
              @click="updateSubject(scope.row.subjectId)"
              icon="el-icon-edit"
              type="text"
              size="mini"
            >修改</el-button>
            <el-button
              icon="el-icon-delete"
              @click="deleteSubject(scope.row.subjectId)"
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
    <el-dialog title="添加科目" :visible.sync="addSubjectDialogVisible" width="300px">
      <el-form
        label-position="right"
        label-width="80px"
        :model="addSubjectForm"
        size="mini"
        :rules="subjectFormRule"
        ref="addFormRef"
      >
        <el-row>
          <el-form-item label="科目名称" prop="subjectName">
            <el-input v-model="addSubjectForm.subjectName"></el-input>
          </el-form-item>
        </el-row>
      </el-form>
      <!-- 确定取消按钮 -->
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="addSubjectYes" size="mini">确 定</el-button>
        <el-button @click="addSubjectDialogVisible = false" size="mini">取 消</el-button>
      </span>
    </el-dialog>
    <!-- 修改功能模态框 -->
    <el-dialog title="修改科目" :visible.sync="updateSubjectDialogVisible" width="300px">
      <el-form
        label-position="right"
        label-width="80px"
        :model="updateSubjectForm"
        size="mini"
        :rules="subjectFormRule"
        ref="updateFormRef"
      >
        <el-row>
          <el-form-item label="科目名称" prop="subjectName">
            <el-input v-model="updateSubjectForm.subjectName"></el-input>
          </el-form-item>
        </el-row>
      </el-form>
      <!-- 确定取消按钮 -->
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="updateSubjectYes" size="mini">确 定</el-button>
        <el-button @click="updateSubjectDialogVisible = false" size="mini">取 消</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      subjectFormRule: {
        subjectName: [
          { required: true, message: "请输入科目名", trigger: "blur" }
        ]
      },
      //搜索表单
      searchsubjectForm: {
        subjectName: ""
      },
      //修改表单
      updateSubjectForm: {
        subjectId: 0,
        subjectName: ""
      },
      //表格数据
      tableData: [],
      //总条数
      total: 0,
      //分页查询参数
      queryInfo: {
        page: 1, //当前页数
        rows: 5, //每页显示条数
        subjectName: ""
      },
      //添加form
      addSubjectForm: {
        subjectName: ""
      },
      //添加模态框是否显示
      addSubjectDialogVisible: false,
      //修改模态框是否显示
      updateSubjectDialogVisible: false
    };
  },
  //狗子函数，已加载完成
  mounted() {
    this.getSubjects();
  },
  methods: {
    //搜索
    searchSubject() {
      this.queryInfo.page = 1;
      this.queryInfo.subjectName = this.searchsubjectForm.subjectName;
      this.getSubjects();
    },
    //清空搜索
    clear() {
      this.queryInfo.page = 1;
      this.queryInfo.subjectName = "";
      this.searchsubjectForm.subjectName = "";
      this.getSubjects();
    },
    //每页显示多少条改变
    handleSizeChange(newRows) {
      this.queryInfo.rows = newRows;
      this.getSubjects();
    },
    //当前页码改变
    handleCurrentChange(newPage) {
      this.queryInfo.page = newPage;
      this.getSubjects();
    },
    //分页获取表格数据
    getSubjects() {
      this.$http
        .get("school/subject/findSubjectsByPage", {
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
            title: "pre:AuthorizationFilter" ? "抱歉，您咱没有权限查看科目列表！" : error.respon.data.message
          });
        });
    },
    //添加
    addSubjectYes() {
      this.$refs.addFormRef.validate(valid => {
        if (valid) {
          //校验通过
          this.$http
            .post("school/subject/addSubject", this.addSubjectForm)
            .then(response => {
              const res = response.data;
              if (res.httpCode === 201) {
                this.addSubjectForm.subjectName = "";
                this.addSubjectDialogVisible = false;
                this.getSubjects();
                this.$notify.success({
                  title: res.message
                });
              } else if (res.httpCode === 600) {
                this.addSubjectForm.subjectName = "";
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
    deleteSubject(subjectId) {
      this.$confirm("此操作将永久删除该科目, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          //删除
          this.$http
            .delete("school/subject/delSubjectById", {
              params: {
                subjectId: subjectId
              }
            })
            .then(response => {
              const res = response.data;
              if (res.httpCode === 204) {
                this.$notify.success({
                  title: res.message
                });
                this.queryInfo.page = 1;
                this.getSubjects();
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
    updateSubject(subjectId) {
      this.$http
        .get("school/subject/findSubjectById", {
          params: {
            subjectId: subjectId
          }
        })
        .then(response => {
          const res = response.data;
          if (res.httpCode === 200) {
            this.updateSubjectForm.subjectId = subjectId;
            this.updateSubjectForm.subjectName = res.data.subjectName;
            this.updateSubjectDialogVisible = true;
          }
        })
        .catch(error => {});
    },
    //确认修改
    updateSubjectYes() {
      this.$refs.updateFormRef.validate(valid => {
        if (valid) {
          //校验通过
          this.$http
            .put("school/subject/updateSubject", this.updateSubjectForm)
            .then(response => {
              const res = response.data;
              if (res.httpCode === 201) {
                this.updateSubjectDialogVisible = false;
                this.getSubjects();
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