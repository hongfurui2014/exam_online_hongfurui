<template>
  <div>
    <!-- 面包屑 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>系统管理</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片 -->
    <el-card body-style="padding: 5px 10px;">
      <!-- 搜索表单 -->
      <el-form label-position="right" label-width="80px" :model="queryInfo" size="mini">
        <el-row :gutter="10">
          <el-col :xs="12" :sm="8" :md="6" :lg="6" :xl="4">
            <el-form-item label="登录账户">
              <el-input v-model="queryInfo.userAccount"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="12" :sm="8" :md="6" :lg="6" :xl="4">
            <el-form-item label="真实姓名">
              <el-input v-model="queryInfo.userRealname"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="12" :sm="8" :md="6" :lg="6" :xl="4">
            <el-form-item label="所属班级">
              <el-select v-model="queryInfo.fkUserGradeId" placeholder="- 请选择 -">
                <el-option
                  v-for="item in queryInfo.grade"
                  :key="item.gradeId"
                  :label="item.gradeName"
                  :value="item.gradeId"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="12" :sm="8" :md="6" :lg="6" :xl="4">
            <el-form-item label="所属角色">
              <el-select v-model="queryInfo.fkUserRoleId" placeholder="- 请选择 -">
                <el-option
                  v-for="item in queryInfo.role"
                  :key="item.roleId"
                  :label="item.roleName"
                  :value="item.roleId"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <!-- 按钮 -->
      <el-row :gutter="10" class="importexport" style="margin-left: 0px;">
        <el-button icon="el-icon-search" @click="searchUser" type="success" size="mini">搜索</el-button>
        <el-button icon="el-icon-refresh" @click="clear" size="mini">清空搜索</el-button>
        <el-button
          icon="el-icon-circle-plus-outline"
          type="primary"
          @click="addBefore"
          size="mini"
        >添加用户</el-button>
      </el-row>
      <!-- 表格 -->
      <el-table :data="tableData" stripe border style="text-align: center">
        <el-table-column type="index" label="#"></el-table-column>
        <el-table-column prop="userAccount" label="登录账户"></el-table-column>
        <el-table-column prop="userRealname" label="真实姓名"></el-table-column>
        <el-table-column label="注册时间">
          <template v-slot:default="scope">{{scope.row.userAddtime | dateFormat}}</template>
        </el-table-column>
        <el-table-column label="所属班级">
          <template v-slot:default="scope">{{scope.row.fkGrade.gradeName}}</template>
        </el-table-column>
        <el-table-column label="所属角色">
          <template v-slot:default="scope">
            <span v-if="scope.row.fkRole.roleName == '站长（开发者）'">
              <el-tag type="danger">{{scope.row.fkRole.roleName }}</el-tag>
            </span>
            <span v-else>
              {{scope.row.fkRole.roleName }}
            </span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="130px;">
          <template v-slot:default="scope">
            <el-button
              @click="updateUser(scope.row.userId)"
              icon="el-icon-edit"
              type="text"
              size="mini"
            >修改</el-button>
            <el-button
              icon="el-icon-delete"
              @click="deleteUser(scope.row.userId)"
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
    <el-dialog title="添加用户" :visible.sync="addUserDialogVisible" width="300px">
      <el-form
        label-position="right"
        label-width="80px"
        :model="addUserForm"
        :rules="userFormRule"
        size="mini"
        ref="addFormRef"
      >
        <el-row>
          <el-form-item label="登录账户" prop="userAccount">
            <el-input v-model="addUserForm.userAccount"></el-input>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="账户密码" prop="userPassword">
            <el-input v-model="addUserForm.userPassword" show-password></el-input>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="真实姓名" prop="userRealname">
            <el-input v-model="addUserForm.userRealname"></el-input>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="所属班级" prop="fkUserGradeId">
            <el-select v-model="addUserForm.fkUserGradeId" placeholder="- 请选择 -" width="100%">
              <el-option
                v-for="item in addUserForm.grade"
                :key="item.gradeId"
                :label="item.gradeName"
                :value="item.gradeId"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="所属角色" prop="fkUserRoleId">
            <el-select v-model="addUserForm.fkUserRoleId" placeholder="- 请选择 -">
              <el-option
                v-for="item in addUserForm.role"
                :key="item.roleId"
                :label="item.roleName"
                :value="item.roleId"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-row>
      </el-form>
      <!-- 确定取消按钮 -->
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="addUserYes" size="mini">确 定</el-button>
        <el-button @click="addUserDialogVisible = false" size="mini">取 消</el-button>
      </span>
    </el-dialog>
    <!-- 修改功能模态框 -->
    <el-dialog title="修改用户" :visible.sync="updateUserDialogVisible" width="300px">
      <el-form
        label-position="right"
        label-width="80px"
        :model="updateUserForm"
        :rules="userFormRule"
        size="mini"
        ref="updateFormRef"
      >
        <el-row>
          <el-form-item label="登录账户" prop="userAccount">
            <el-input disabled v-model="updateUserForm.userAccount"></el-input>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="真实姓名" prop="userRealname">
            <el-input disabled v-model="updateUserForm.userRealname"></el-input>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="所属班级" prop="fkUserGradeId">
            <el-select v-model="updateUserForm.fkUserGradeId" placeholder="- 请选择 -" width="100%">
              <el-option
                v-for="item in updateUserForm.grade"
                :key="item.gradeId"
                :label="item.gradeName"
                :value="item.gradeId"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="所属角色" prop="fkUserRoleId">
            <el-select v-model="updateUserForm.fkUserRoleId" placeholder="- 请选择 -">
              <el-option
                v-for="item in updateUserForm.role"
                :key="item.roleId"
                :label="item.roleName"
                :value="item.roleId"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-row>
      </el-form>
      <!-- 确定取消按钮 -->
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="updateUserYes" size="mini">确 定</el-button>
        <el-button @click="updateUserDialogVisible = false" size="mini">取 消</el-button>
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
        rows: 5, //每页显示条数
        userAccount: "",
        userRealname: "",
        grade: [],
        fkUserGradeId: "",
        role: [],
        fkUserRoleId: ""
      },
      //添加form
      addUserForm: {
        userAccount: "",
        userPassword: "",
        userRealname: "",
        grade: [],
        fkUserGradeId: "",
        role: [],
        fkUserRoleId: ""
      },
      //修改form
      updateUserForm: {
        userId: "",
        userAccount: "",
        userRealname: "",
        grade: [],
        fkUserGradeId: "",
        role: [],
        fkUserRoleId: ""
      },
      //添加表单验证规则
      userFormRule: {
        userAccount: [
          { required: true, message: "请输入登录账户", trigger: "blur" },
          { min: 3, max: 16, message: "长度在 3 到 16 个字符", trigger: "blur" }
        ],
        userPassword: [
          { required: true, message: "请输入账户密码", trigger: "blur" },
          { min: 5, max: 20, message: "长度在 5 到 20 个字符", trigger: "blur" }
        ],
        userRealname: [
          { required: true, message: "请输入真实姓名", trigger: "blur" },
          { min: 2, max: 7, message: "长度在 2 到 7 个字符", trigger: "blur" }
        ],
        fkUserGradeId: [
          { required: true, message: "请选择所属班级", trigger: "blur" }
        ],
        fkUserRoleId: [
          { required: true, message: "请选择所属角色", trigger: "blur" }
        ]
      },
      //添加模态框是否显示
      addUserDialogVisible: false,
      //修改模态框是否显示
      updateUserDialogVisible: false
    };
  },
  //钩子函数，已加载完成
  mounted() {
    //为搜索表单添加班级列表
    this.getSearchGrades();
    //为搜索表单添加角色列表
    this.getSearchRoles();
    //加载表格数据
    this.getUsers();
  },
  methods: {
    //为搜索表单添加班级列表
    getSearchGrades() {
      this.$http
        .get("school/grade/findGrades")
        .then(response => {
          const res = response.data;
          if (res.httpCode === 200) {
            this.queryInfo.grade = res.data;
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
            this.addUserForm.grade = res.data;
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
            this.updateUserForm.grade = res.data;
          }
        })
        .catch(error => {
          this.$notify.error({
            title: error.response.data.message
          });
        });
    },
    //为搜索表单添加角色列表
    getSearchRoles() {
      this.$http
        .get("user/role/findRoles")
        .then(response => {
          const res = response.data;
          if (res.httpCode === 200) {
            this.queryInfo.role = res.data;
          }
        })
        .catch(error => {
          console.log(error);
          this.$notify.error({
            title: error.response.data.message
          });
        });
    },
    //为添加表单添加角色列表
    getAddRoles() {
      this.$http
        .get("user/role/findRoles")
        .then(response => {
          const res = response.data;
          if (res.httpCode === 200) {
            this.addUserForm.role = res.data;
          }
        })
        .catch(error => {
          console.log(error);
          this.$notify.error({
            title: error.response.data.message
          });
        });
    },
    //为修改表单添加角色列表
    getUpdateRoles() {
      this.$http
        .get("user/role/findRoles")
        .then(response => {
          const res = response.data;
          if (res.httpCode === 200) {
            this.updateUserForm.role = res.data;
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
    searchUser() {
      this.queryInfo.page = 1;
      this.getUsers();
    },
    //清空搜索
    clear() {
      this.queryInfo.page = 1;
      this.queryInfo.userName = "";
      this.queryInfo.userAccount = "";
      this.queryInfo.userRealname = "";
      this.queryInfo.fkUserGradeId = "";
      this.queryInfo.fkUserRoleId = "";
      this.getUsers();
    },
    //每页显示多少条改变
    handleSizeChange(newRows) {
      this.queryInfo.rows = newRows;
      this.getUsers();
    },
    //当前页码改变
    handleCurrentChange(newPage) {
      this.queryInfo.page = newPage;
      this.getUsers();
    },
    //分页获取表格数据
    getUsers() {
      this.$http
        .get("user/user/findUsersByPage", {
          params: {
            page: this.queryInfo.page,
            rows: this.queryInfo.rows,
            userAccount: this.queryInfo.userAccount,
            userRealname: this.queryInfo.userRealname,
            fkUserGradeId: this.queryInfo.fkUserGradeId,
            fkUserRoleId: this.queryInfo.fkUserRoleId
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
    //添加
    addUserYes() {
      this.$refs.addFormRef.validate(valid => {
        if (valid) {
          //校验通过
          this.$http
            .post("user/user/addUser", this.addUserForm)
            .then(response => {
              const res = response.data;
              if (res.httpCode === 201) {
                //this.addUserForm.userName = "";
                this.addUserDialogVisible = false;
                this.getUsers();
                this.$notify.success({
                  title: res.message
                });
              } else if (res.httpCode === 600) {
                this.addUserForm.userName = "";
                this.$notify.error({
                  title: res.message
                });
              }
            })
            .catch(error => {
              console.log(error);
              this.$notify.error({
                title: error.response.data.message
              });
            });
        }
      });
    },
    //删除
    deleteUser(userId) {
      this.$confirm("此操作将永久删除该用户, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          //删除
          this.$http
            .delete("user/user/delUserById", {
              params: {
                userId: userId
              }
            })
            .then(response => {
              const res = response.data;
              if (res.httpCode === 204) {
                this.$notify.success({
                  title: res.message
                });
                this.queryInfo.page = 1;
                this.getUsers();
              } else if (res.httpCode === 600) {
                this.$notify.error({
                  title: res.message
                });
              }
            })
            .catch(error => {
              console.log(error);
              this.$notify.error({
                title: error.response.data.message
              });
            });
        })
        .catch(() => {});
    },
    //修改
    updateUser(userId) {
      this.$http
        .get("user/user/findUserById", {
          params: {
            userId: userId
          }
        })
        .then(response => {
          const res = response.data;
          if (res.httpCode === 200) {
            this.getUpdateGrades();
            this.getUpdateRoles();
            this.updateUserForm.userId = userId;
            this.updateUserForm.userAccount = res.data.userAccount;
            this.updateUserForm.userRealname = res.data.userRealname;
            this.updateUserForm.fkUserGradeId = res.data.fkUserGradeId;
            this.updateUserForm.fkUserRoleId = res.data.fkUserRoleId;

            this.updateUserDialogVisible = true;
          }
        })
        .catch(error => {});
    },
    //确认修改
    updateUserYes() {
      this.$refs.updateFormRef.validate(valid => {
        if (valid) {
          //校验通过
          this.$http
            .put("user/user/updateUser", this.updateUserForm)
            .then(response => {
              const res = response.data;
              if (res.httpCode === 201) {
                this.updateUserDialogVisible = false;
                this.getUsers();
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
                title: error.response.data.message
              });
            });
        }
      });
    },
    //按下添加用户按钮
    addBefore() {
      (this.addUserForm.userAccount = ""),
        (this.addUserForm.userRealname = ""),
        (this.addUserForm.userPassword = ""),
        (this.addUserForm.fkUserGradeId = ""),
        (this.addUserForm.fkUserRoleId = ""),
        this.getAddGrades(),
        this.getAddRoles(),
        (this.addUserDialogVisible = true);
    }
  }
};
</script>

<style lang='less' scoped>
el-table {
  text-align: center;
}
</style>