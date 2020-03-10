<template>
  <el-container>
    <el-aside :width="isCollapse ? '64px' : '200px'">
      <div class="toggle-button" @click="toggleCollapse">|||</div>
      <el-menu
        :default-active="$route.path"
        background-color="#304156"
        text-color="#fff"
        active-text-color="#409EFF"
        unique-opened
        :collapse="isCollapse"
        :collapse-transition="false"
        router
      >
        <el-menu-item index="/welcome" style="border-bottom: 1px solid #555;">
          <i class="el-icon-s-home"></i>
          <span>后台首页</span>
        </el-menu-item>

        <el-submenu index="2">
          <template slot="title">
            <i class="el-icon-setting"></i>
            <span>系统管理</span>
          </template>

          <el-menu-item index="/system/user">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>用户管理</span>
            </template>
          </el-menu-item>

          <el-menu-item index="/system/role">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>角色管理</span>
            </template>
          </el-menu-item>

          <el-menu-item index="2-3">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>菜单管理</span>
            </template>
          </el-menu-item>
        </el-submenu>

        <el-submenu index="3">
          <template slot="title">
            <i class="el-icon-setting"></i>
            <span>考试管理</span>
          </template>

          <el-menu-item index="3-1">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>试题管理</span>
            </template>
          </el-menu-item>

          <el-menu-item index="3-2">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>考卷管理</span>
            </template>
          </el-menu-item>

          <el-menu-item index="3-3">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>考试统计</span>
            </template>
          </el-menu-item>
        </el-submenu>

        <el-submenu index="4">
          <template slot="title">
            <i class="el-icon-setting"></i>
            <span>教师操作</span>
          </template>

          <el-menu-item index="4-1">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>班级管理</span>
            </template>
          </el-menu-item>

          <el-menu-item index="2-2">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>科目管理</span>
            </template>
          </el-menu-item>
        </el-submenu>

        <el-submenu index="5">
          <template slot="title">
            <i class="el-icon-setting"></i>
            <span>日志列表</span>
          </template>

          <el-menu-item index="5-1">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>操作日志</span>
            </template>
          </el-menu-item>
        </el-submenu>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div>
          <img src="../assets/itpanlogo.png" style="height:45px; width:45px" alt />
          <span style="margin-left: 10px;">在线考试系统 - 后台管理</span>
        </div>

        <el-dropdown>
          <span class="el-dropdown-link">
            洪福锐
            <i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item divided>个人中心</el-dropdown-item>
            <el-dropdown-item divided>修改密码</el-dropdown-item>
            <el-dropdown-item divided>退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-header>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  data() {
    return {
      isCollapse: false
    };
  },
  methods: {
    toggleCollapse() {
      this.isCollapse = !this.isCollapse;
    }
  }
};
</script>

<style lang='less' scoped>
.el-header {
  background-color: #fff;
  border-bottom: 1px solid #ddd;
  padding: 0px 40px;

  display: flex;
  justify-content: space-between;

  align-items: center;

  color: #888;
  font-size: 20px;
  font-weight: bold;

  box-shadow: 3px 3px 4px #ddd !important;

  > div {
    display: flex;
    align-items: center;
  }
}

.el-aside {
  background-color: #304156;

  .el-menu {
    border-right: 0px;

    .el-submenu {
      border-bottom: 1px solid #555;
    }
  }
}

.el-main {
  //padding: 0px 20px;
  //margin: 10px;
  //background-color: red;
  //margin: 10px;
}

.el-container {
  height: 100%;
}

.toggle-button {
  background-color: #4a5064;
  font-size: 10px;
  line-height: 24px;
  color: #fff;
  text-align: center;
  letter-spacing: 0.2em;
  cursor: pointer;
}

.el-dropdown-link {
  cursor: pointer;
  color: #409eff;
}
.el-icon-arrow-down {
  font-size: 12px;
}
</style>