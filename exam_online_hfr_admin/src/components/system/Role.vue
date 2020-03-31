<template>
  <div>
    <!-- 面包屑 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/admin' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>学校操作</el-breadcrumb-item>
      <el-breadcrumb-item>角色权限</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片 -->
    <el-card body-style="padding: 5px 10px;">
      <!-- 搜索表单 -->
      <el-form label-position="right" label-width="80px" :model="searchroleForm" size="mini">
        <el-row :gutter="10">
          <el-col :xs="12" :sm="8" :md="6" :lg="6" :xl="4">
            <el-form-item label="角色名称">
              <el-input v-model="searchroleForm.roleName"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <!-- 按钮 -->
      <el-row :gutter="10" class="importexport" style="margin-left: 0px;">
        <el-button icon="el-icon-search" @click="searchRole" type="success" size="mini">搜索</el-button>
        <el-button icon="el-icon-refresh" @click="clear" size="mini">清空搜索</el-button>
        <el-button
          icon="el-icon-circle-plus-outline"
          type="primary"
          @click="addRoleDialogVisible=true; addRoleForm.roleName='';"
          size="mini"
        >添加角色</el-button>
      </el-row>
      <!-- 表格 -->
      <el-table :data="tableData" stripe border>
        <el-table-column type="index" label="#"></el-table-column>
        <el-table-column prop="roleName" label="角色名称"></el-table-column>
        <el-table-column label="操作" width="250px;">
          <template v-slot:default="scope">
            <el-button
              @click="updateRole(scope.row.roleId)"
              icon="el-icon-edit"
              type="text"
              size="mini"
            >修改</el-button>
            <el-button
              icon="el-icon-delete"
              @click="deleteRole(scope.row.roleId)"
              type="text"
              size="mini"
            >删除</el-button>
            <el-button
              icon="el-icon-setting"
              type="text"
              @click="updateRightsDialog(scope.row.roleId)"
              size="mini"
            >权限查看与分配</el-button>
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
    <el-dialog title="添加角色" :visible.sync="addRoleDialogVisible" width="300px">
      <el-form
        label-position="right"
        label-width="80px"
        :model="addRoleForm"
        size="mini"
        :rules="roleFormRule"
        ref="addFormRef"
      >
        <el-row>
          <el-form-item label="角色名称" prop="roleName">
            <el-input v-model="addRoleForm.roleName"></el-input>
          </el-form-item>
        </el-row>
      </el-form>
      <!-- 确定取消按钮 -->
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="addRoleYes" size="mini">确 定</el-button>
        <el-button @click="addRoleDialogVisible = false" size="mini">取 消</el-button>
      </span>
    </el-dialog>
    <!-- 修改功能模态框 -->
    <el-dialog title="修改角色" :visible.sync="updateRoleDialogVisible" width="300px">
      <el-form
        label-position="right"
        label-width="80px"
        :model="updateRoleForm"
        size="mini"
        :rules="roleFormRule"
        ref="updateFormRef"
      >
        <el-row>
          <el-form-item label="角色名称" prop="roleName">
            <el-input v-model="updateRoleForm.roleName"></el-input>
          </el-form-item>
        </el-row>
      </el-form>
      <!-- 确定取消按钮 -->
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="updateRoleYes" size="mini">确 定</el-button>
        <el-button @click="updateRoleDialogVisible = false" size="mini">取 消</el-button>
      </span>
    </el-dialog>

    <!-- 修改权限模态框 -->
    <el-dialog
      title="权限查看与分配"
      :visible.sync="updateRightsDialogVisible"
      width="300px"
      @close="updateRightsDialogclose"
    >
      <el-tree
        :data="treeRightsData"
        show-checkbox
        node-key="rightsId"
        :default-checked-keys="defaultCheckedKeys"
        :props="defaultProps"
        highlight-current
        default-expand-all
        ref="treeRef"
        check-on-click-node
      ></el-tree>
      <!-- 确定取消按钮 -->
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="updateRightsYes" size="mini">确 定</el-button>
        <el-button @click="updateRightsDialogVisible = false" size="mini">取 消</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      roleFormRule: {
        roleName: [{ required: true, message: "请输入角色名", trigger: "blur" }]
      },
      //搜索表单
      searchroleForm: {
        roleName: ""
      },
      //修改表单
      updateRoleForm: {
        roleId: 0,
        roleName: ""
      },
      //表格数据
      tableData: [],
      //总条数
      total: 0,
      //分页查询参数
      queryInfo: {
        page: 1, //当前页数
        rows: 5, //每页显示条数
        roleName: ""
      },
      //添加form
      addRoleForm: {
        roleName: ""
      },
      //添加模态框是否显示
      addRoleDialogVisible: false,
      //修改模态框是否显示
      updateRoleDialogVisible: false,
      //修改权限模态框是否显示
      updateRightsDialogVisible: false,
      treeRightsData: [], //权限树形控件
      defaultProps: {
        children: "children",
        label: "rightsAuthname"
      },
      defaultCheckedKeys: [], //默认选中的节点
      updateRightsRoleId: 0
    };
  },
  //钩子函数，已加载完成
  mounted() {
    this.getRoles();
  },
  methods: {
    //搜索
    searchRole() {
      this.queryInfo.page = 1;
      this.queryInfo.roleName = this.searchroleForm.roleName;
      this.getRoles();
    },
    //清空搜索
    clear() {
      this.queryInfo.page = 1;
      this.queryInfo.roleName = "";
      this.searchroleForm.roleName = "";
      this.getRoles();
    },
    //每页显示多少条改变
    handleSizeChange(newRows) {
      this.queryInfo.rows = newRows;
      this.getRoles();
    },
    //当前页码改变
    handleCurrentChange(newPage) {
      this.queryInfo.page = newPage;
      this.getRoles();
    },
    //分页获取表格数据
    getRoles() {
      this.$http
        .get("user/role/findRolesByPage", {
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
            title: "pre:AuthorizationFilter" ? "抱歉，您咱没有权限查看角色列表！" : error.respon.data.message
          });
        });
    },
    //添加
    addRoleYes() {
      this.$refs.addFormRef.validate(valid => {
        if (valid) {
          //校验通过
          this.$http
            .post("user/role/addRole", this.addRoleForm)
            .then(response => {
              const res = response.data;
              if (res.httpCode === 201) {
                this.addRoleForm.roleName = "";
                this.addRoleDialogVisible = false;
                this.getRoles();
                this.$notify.success({
                  title: res.message
                });
              } else if (res.httpCode === 600) {
                this.addRoleForm.roleName = "";
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
    //删除
    deleteRole(roleId) {
      this.$confirm("此操作将永久删除该角色, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          //确定删除
          this.$http
            .delete("user/role/delRoleById", {
              params: {
                roleId: roleId
              }
            })
            .then(response => {
              const res = response.data;
              if (res.httpCode === 204) {
                this.$notify.success({
                  title: res.message
                });
                this.queryInfo.page = 1;
                this.getRoles();
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
    updateRole(roleId) {
      this.$http
        .get("user/role/findRoleById", {
          params: {
            roleId: roleId
          }
        })
        .then(response => {
          const res = response.data;
          if (res.httpCode === 200) {
            this.updateRoleForm.roleId = roleId;
            this.updateRoleForm.roleName = res.data.roleName;
            this.updateRoleDialogVisible = true;
          }
        })
        .catch(error => {
          this.$notify.error({});
        });
    },
    //确认修改
    updateRoleYes() {
      this.$refs.updateFormRef.validate(valid => {
        if (valid) {
          //校验通过
          this.$http
            .put("user/role/updateRole", this.updateRoleForm)
            .then(response => {
              const res = response.data;
              if (res.httpCode === 201) {
                this.updateRoleDialogVisible = false;
                this.getRoles();
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
    },
    //修改权限模态框弹出
    async updateRightsDialog(roleId) {
      this.updateRightsRoleId = roleId;
      this.updateRightsDialogVisible = true;
      //查询所有菜单
      // const res = await this.$http.get("user/rights/findAll");
      // this.treeRightsData = res.data.data;

      this.$http
        .get("user/rights/findAll")
        .then(response => {
          const res = response.data;
          if (res.httpCode === 200) {
            this.treeRightsData = res.data;
          }
        })
        .catch(error => {
          this.$notify.error({
          title: "pre:AuthorizationFilter" ? "抱歉，您咱没有权限查看权限菜单列表！" : error.respon.data.message
          });
        });

      //根据角色id查询出该角色所对应的所有菜单id
      const res2 = await this.$http.get(
        "user/roleRights/getRightsIdsByRoleId",
        {
          params: {
            fkRoleId: roleId
          }
        }
      );
      this.defaultCheckedKeys = res2.data.data;
    },
    //确定修改权限
    async updateRightsYes() {

      let keys = [...this.$refs.treeRef.getCheckedKeys(), ...this.$refs.treeRef.getHalfCheckedKeys()];
      let param = new URLSearchParams();
      param.append("fkRoleId", this.updateRightsRoleId);
      param.append("rightsList", keys);

      this.$http
        .put("user/roleRights/updateRights", param)
        .then(response => {
          const res = response.data;
          if (res.httpCode === 201) {
          this.$notify.success({
            title: res.message
          });
          }
        })
        .catch(error => {
          this.$notify.error({
          title: "pre:AuthorizationFilter" ? "抱歉，您的权限暂未开放，请联系系统管理员！" : error.respon.data.message
          });
        });

      this.updateRightsDialogVisible = false;
    },
    updateRightsDialogclose() {
      this.defaultCheckedKeys = [];
    }
  }
};
</script>

<style lang='less' scoped>
</style>