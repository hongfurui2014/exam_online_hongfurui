<template>
  <div>
    <!-- 面包屑 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/admin' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>系统管理</el-breadcrumb-item>
      <el-breadcrumb-item>考试用户</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片 -->
    <el-card body-style="padding: 5px 10px;">
      <!-- 搜索表单 -->
      <el-form label-position="right" label-width="80px" :model="queryInfo" size="mini">
        <el-row :gutter="10">
          <el-col :xs="12" :sm="8" :md="6" :lg="6" :xl="4">
            <el-form-item label="登录账户">
              <el-input v-model="queryInfo.userQAccount"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="12" :sm="8" :md="6" :lg="6" :xl="4">
            <el-form-item label="真实姓名">
              <el-input v-model="queryInfo.userQRealname"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="12" :sm="8" :md="6" :lg="6" :xl="4">
            <el-form-item label="所属班级">
              <el-select v-model="queryInfo.fkUserQGradeId" placeholder="- 请选择 -">
                <el-option
                  v-for="item in queryInfo.gradeQ"
                  :key="item.gradeId"
                  :label="item.gradeName"
                  :value="item.gradeId"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <!-- 按钮 -->
      <el-row :gutter="10" class="importexport" style="margin-left: 0px;">
        <el-button icon="el-icon-search" @click="searchUserQ" type="success" size="mini">搜索</el-button>
        <el-button icon="el-icon-refresh" @click="clear" size="mini">清空搜索</el-button>
        <el-button
          icon="el-icon-circle-plus-outline"
          type="primary"
          @click="addBefore"
          size="mini"
        >添加考试用户</el-button>
      </el-row>
      <!-- 表格 -->
      <el-table :data="tableData" stripe border style="text-align: center">
        <el-table-column type="index" label="#"></el-table-column>
        <el-table-column prop="userQAccount" label="登录账户"></el-table-column>
        <el-table-column prop="userQRealname" label="真实姓名"></el-table-column>
        <el-table-column label="注册时间">
          <template v-slot:default="scope">{{scope.row.userQAddtime | dateFormat}}</template>
        </el-table-column>
        <el-table-column label="所属班级">
          <template v-slot:default="scope">{{scope.row.fkGradeQ.gradeName}}</template>
        </el-table-column>
        <el-table-column label="操作" width="130px;">
          <template v-slot:default="scope">
            <el-button
              @click="updateUserQ(scope.row.userQId)"
              icon="el-icon-edit"
              type="text"
              size="mini"
            >修改</el-button>
            <el-button
              icon="el-icon-delete"
              @click="deleteUserQ(scope.row.userQId)"
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
        :page-sizes="[20, 50, 100, 150, 200]"
        :page-size="queryInfo.rows"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        background
      ></el-pagination>
    </el-card>

    <!-- 添加功能模态框 -->
    <el-dialog title="添加用户" :visible.sync="addUserQDialogVisible" width="300px">
      <el-form
        label-position="right"
        label-width="80px"
        :model="addUserQForm"
        :rules="userQFormRule"
        size="mini"
        ref="addFormRef"
      >
        <el-row>
          <el-form-item label="登录账户" prop="userQAccount">
            <el-input v-model="addUserQForm.userQAccount"></el-input>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="账户密码" prop="userQPassword">
            <el-input v-model="addUserQForm.userQPassword" show-password></el-input>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="真实姓名" prop="userQRealname">
            <el-input v-model="addUserQForm.userQRealname"></el-input>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="所属班级" prop="fkUserQGradeId">
            <el-select v-model="addUserQForm.fkUserQGradeId" placeholder="- 请选择 -">
              <el-option
                v-for="item in addUserQForm.gradeQ"
                :key="item.gradeId"
                :label="item.gradeName"
                :value="item.gradeId"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-row>
      </el-form>
      <!-- 确定取消按钮 -->
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="addUserQYes" size="mini">确 定</el-button>
        <el-button @click="addUserQDialogVisible = false" size="mini">取 消</el-button>
      </span>
    </el-dialog>
    <!-- 修改功能模态框 -->
    <el-dialog title="修改用户" :visible.sync="updateUserQDialogVisible" width="300px">
      <el-form
        label-position="right"
        label-width="80px"
        :model="updateUserQForm"
        :rules="userQFormRule"
        size="mini"
        ref="updateFormRef"
      >
        <el-row>
          <el-form-item label="登录账户" prop="userQAccount">
            <el-input disabled v-model="updateUserQForm.userQAccount"></el-input>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="真实姓名" prop="userQRealname">
            <el-input disabled v-model="updateUserQForm.userQRealname"></el-input>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="所属班级" prop="fkUserQGradeId">
            <el-select disabled v-model="updateUserQForm.fkUserQGradeId" placeholder="- 请选择 -" width="100%">
              <el-option
                v-for="item in updateUserQForm.gradeQ"
                :key="item.gradeId"
                :label="item.gradeName"
                :value="item.gradeId"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-row>
      </el-form>
      <!-- 确定取消按钮 -->
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="updateUserQYes" size="mini">确 定</el-button>
        <el-button @click="updateUserQDialogVisible = false" size="mini">取 消</el-button>
      </span>
    </el-dialog>
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
        rows: 20, //每页显示条数
        userQAccount: "",
        userQRealname: "",
        gradeQ: [],
        fkUserQGradeId: "",
      },
      //添加form
      addUserQForm: {
        userQAccount: "",
        userQPassword: "",
        userQRealname: "",
        gradeQ: [],
        fkUserQGradeId: "",
      },
      //修改form
      updateUserQForm: {
        userQId: "",
        userQAccount: "",
        userQRealname: "",
        gradeQ: [],
        fkUserQGradeId: "",
      },
      //添加表单验证规则
      userQFormRule: {
        userQAccount: [
          { required: true, message: "请输入登录账户", trigger: "blur" },
          { min: 3, max: 16, message: "长度在 3 到 16 个字符", trigger: "blur" }
        ],
        userQPassword: [
          { required: true, message: "请输入账户密码", trigger: "blur" },
          { min: 5, max: 20, message: "长度在 5 到 20 个字符", trigger: "blur" }
        ],
        userQRealname: [
          { required: true, message: "请输入真实姓名", trigger: "blur" },
          { min: 2, max: 7, message: "长度在 2 到 7 个字符", trigger: "blur" }
        ],
        fkUserQGradeId: [
          { required: true, message: "请选择所属班级", trigger: "blur" }
        ]
      },
      //添加模态框是否显示
      addUserQDialogVisible: false,
      //修改模态框是否显示
      updateUserQDialogVisible: false
    };
  },
  //钩子函数，已加载完成
  mounted() {
    //为搜索表单添加班级列表
    this.getSearchGrades();
    //加载表格数据
    this.getUserQs();
  },
  methods: {
    //为搜索表单添加班级列表
    getSearchGrades() {
      this.$http
        .get("school/grade/findGrades")
        .then(response => {
          const res = response.data;
          if (res.httpCode === 200) {
            this.queryInfo.gradeQ = res.data;
          }
        })
        .catch(error => {
          console.log(error);
          this.$notify.error({
            title: error.response.data.message
          });
        });
    },
    //为添加表单添加班级列表
    getAddGrades() {
      this.$http
        .get("school/grade/findGrades")
        .then(response => {
          const res = response.data;
          if (res.httpCode === 200) {
            this.addUserQForm.gradeQ = res.data;
          }
        })
        .catch(error => {
          this.$notify.error({
            title: error.response.data.message
          });
        });
    },
    //为更新表单添加班级列表
    getUpdateGrades() {
      this.$http
        .get("school/grade/findGrades")
        .then(response => {
          const res = response.data;
          if (res.httpCode === 200) {
            this.updateUserQForm.gradeQ = res.data;
          }
        })
        .catch(error => {
          this.$notify.error({
            title: error.response.data.message
          });
        });
    },
    //搜索
    searchUserQ() {
      this.queryInfo.page = 1;
      this.getUserQs();
    },
    //清空搜索
    clear() {
      this.queryInfo.page = 1;
      this.queryInfo.userQName = "";
      this.queryInfo.userQAccount = "";
      this.queryInfo.userQRealname = "";
      this.queryInfo.fkUserQGradeId = "";
      this.getUserQs();
    },
    //每页显示多少条改变
    handleSizeChange(newRows) {
      this.queryInfo.rows = newRows;
      this.getUserQs();
    },
    //当前页码改变
    handleCurrentChange(newPage) {
      this.queryInfo.page = newPage;
      this.getUserQs();
    },
    //分页获取表格数据
    getUserQs() {
      this.$http
        .get("user/userQ/findUserQsByPage", {
          params: {
            page: this.queryInfo.page,
            rows: this.queryInfo.rows,
            userQAccount: this.queryInfo.userQAccount,
            userQRealname: this.queryInfo.userQRealname,
            fkUserQGradeId: this.queryInfo.fkUserQGradeId
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
            title: "pre:AuthorizationFilter" ? "抱歉，您咱没有权限查看考试用户列表！" : error.respon.data.message
          });
        });
    },
    //添加
    addUserQYes() {
      this.$refs.addFormRef.validate(valid => {
        if (valid) {
          //校验通过
          this.$http
            .post("user/userQ/addUserQ", this.addUserQForm)
            .then(response => {
              const res = response.data;
              if (res.httpCode === 201) {
                this.addUserQDialogVisible = false;
                this.getUserQs();
                this.$notify.success({
                  title: res.message
                });
              } else if (res.httpCode === 600) {
                this.addUserQForm.userQName = "";
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
    deleteUserQ(userQId) {
      this.$confirm("此操作将永久删除该用户, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          //确定删除
          this.$http
            .delete("user/userQ/delUserQById", {
              params: {
                userQId: userQId
              }
            })
            .then(response => {
              const res = response.data;
              if (res.httpCode === 204) {
                this.$notify.success({
                  title: res.message
                });
                this.queryInfo.page = 1;
                this.getUserQs();
              } else if (res.httpCode === 600) {
                this.$notify.error({
                  title: res.message
                });
              }
            })
            .catch(error => {
              console.log(error);
              this.$notify.error({
                title: "pre:AuthorizationFilter" ? "抱歉，您的权限暂未开放，请联系系统管理员！" : error.respon.data.message
              });
            });
        })
        .catch(() => {});
    },
    //修改
    updateUserQ(userQId) {
      this.$http
        .get("user/userQ/findUserQById", {
          params: {
            userQId: userQId
          }
        })
        .then(response => {
          const res = response.data;
          if (res.httpCode === 200) {
            this.getUpdateGrades();
            this.updateUserQForm.userQId = userQId;
            this.updateUserQForm.userQAccount = res.data.userQAccount;
            this.updateUserQForm.userQRealname = res.data.userQRealname;
            this.updateUserQForm.fkUserQGradeId = res.data.fkUserQGradeId;
            this.updateUserQDialogVisible = true;
          }
        })
        .catch(error => {});
    },
    //确认修改
    updateUserQYes() {
      this.$refs.updateFormRef.validate(valid => {
        if (valid) {
          //校验通过
          this.$http
            .put("user/userQ/updateUserQ", this.updateUserQForm)
            .then(response => {
              const res = response.data;
              if (res.httpCode === 201) {
                this.updateUserQDialogVisible = false;
                this.getUserQs();
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
              console.log(error);
              this.$notify.error({
                title: "pre:AuthorizationFilter" ? "抱歉，您的权限暂未开放，请联系系统管理员！" : error.respon.data.message
              });
            });
        }
      });
    },
    //按下添加用户按钮
    addBefore() {
      (this.addUserQForm.userQAccount = ""),
        (this.addUserQForm.userQRealname = ""),
        (this.addUserQForm.userQPassword = ""),
        (this.addUserQForm.fkUserQGradeId = ""),
        this.getAddGrades(),
        (this.addUserQDialogVisible = true);
    }
  }
};
</script>

<style lang='less' scoped>
el-table {
  text-align: center;
}
</style>