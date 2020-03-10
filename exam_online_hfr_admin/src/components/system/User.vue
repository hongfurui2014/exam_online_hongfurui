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
      <!-- 搜索框 -->
      <el-form label-position="right" label-width="70px" :model="formLabelAlign" size="mini">
        <el-row :gutter="10">
          <el-col :xs="12" :sm="8" :md="6" :lg="6" :xl="4">
            <el-form-item label="名称空间">
              <el-input v-model="formLabelAlign.name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="12" :sm="8" :md="6" :lg="6" :xl="4">
            <el-form-item label="名称">
              <el-input v-model="formLabelAlign.name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="12" :sm="8" :md="6" :lg="6" :xl="4">
            <el-form-item label="名称">
              <el-input v-model="formLabelAlign.name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="12" :sm="8" :md="6" :lg="6" :xl="4">
            <el-form-item label="名称">
              <el-input v-model="formLabelAlign.name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="12" :sm="8" :md="6" :lg="6" :xl="4">
            <el-form-item label="名称">
              <el-input v-model="formLabelAlign.name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="12" :sm="8" :md="6" :lg="6" :xl="4">
            <el-form-item label="名称空间">
              <el-input v-model="formLabelAlign.name"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <!-- 导入导出框 -->
      <el-row :gutter="10" class="importexport" style="margin-left: 0px;">
        <el-button icon="el-icon-search" type="success" size="mini">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini">重置</el-button>
        <el-button icon="el-icon-circle-plus-outline" type="primary" @click="addUserDialogVisible=true" size="mini">添加</el-button>
        <el-button icon="el-icon-upload2" type="info" size="mini">导入</el-button>
        <el-button icon="el-icon-download" type="warning" size="mini">导出</el-button>
      </el-row>
      <!-- 添加用户对话框 -->
      <el-dialog title="添加用户" :visible.sync="addUserDialogVisible" width="30%">
        <span>这是一段信息</span>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="addUserDialogVisible = false" size="mini">确 定</el-button>
          <el-button @click="addUserDialogVisible = false" size="mini">取 消</el-button>
        </span>
      </el-dialog>
      <!-- 表格 -->
      <el-table :data="tableData" stripe border>
        <el-table-column type="index" label="#"></el-table-column>
        <el-table-column prop="date" label="日期" width="200px;"></el-table-column>
        <el-table-column prop="name" label="姓名" width="100px;"></el-table-column>
        <el-table-column label="状态" width="100px;">
          <template v-slot="scope">
            <el-switch v-model="scope.row.state"></el-switch>
          </template>
        </el-table-column>
        <el-table-column prop="address" label="地址"></el-table-column>
        <el-table-column label="操作" width="150px;">
          <template>
            <el-button icon="el-icon-edit" type="text" size="mini">修改</el-button>
            <el-button icon="el-icon-delete" type="text" size="mini">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页 -->
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage4"
        :page-sizes="[15, 30, 45, 60]"
        :page-size="15"
        layout="total, sizes, prev, pager, next, jumper"
        :total="200"
        background
      ></el-pagination>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      formLabelAlign: {
        name: ""
      },
      tableData: [
        {
          date: "2018-03-16 11:33:00",
          name: "王小虎",
          state: true,
          address: "上海市普陀区金沙江路 1518 弄"
        },
        {
          date: "2016-05-04",
          name: "王小虎",
          state: true,
          address: "上海市普陀区金沙江路 1517 弄"
        },
        {
          date: "2018-03-16 11:33:00",
          name: "王小虎",
          state: false,
          address: "上海市普陀区金沙江路 1519 弄"
        },
        {
          date: "2016-05-03",
          name: "王小虎",
          state: true,
          address: "上海市普陀区金沙江路 1516 弄"
        }
      ],
      currentPage4: 1, //当前页数
      addUserDialogVisible: false
    };
  },
  methods: {
    //每页显示多少条改变
    handleSizeChange(newSize) {
      console.log(newSize);
    },
    //页码改变
    handleCurrentChange(newPage) {
      console.log(newPage);
    }
  }
};
</script>

<style lang='less' scoped>
</style>