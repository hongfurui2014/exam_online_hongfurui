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
        <el-menu-item index="/admin" style="border-bottom: 1px solid #555;">
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
              <span>后台用户</span>
            </template>
          </el-menu-item>

          <el-menu-item index="/system/userQ">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>考试用户</span>
            </template>
          </el-menu-item>

          <el-menu-item index="/system/role">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>角色权限</span>
            </template>
          </el-menu-item>
        </el-submenu>

        <el-submenu index="3">
          <template slot="title">
            <i class="el-icon-setting"></i>
            <span>考试管理</span>
          </template>

          <el-menu-item index="/test/topic">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>试题管理</span>
            </template>
          </el-menu-item>

          <el-menu-item index="/test/test">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>试卷管理</span>
            </template>
          </el-menu-item>

          <el-menu-item index="/test/statistics">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>考试统计</span>
            </template>
          </el-menu-item>
        </el-submenu>

        <el-submenu index="4">
          <template slot="title">
            <i class="el-icon-setting"></i>
            <span>学校操作</span>
          </template>

          <el-menu-item index="/school/grade">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>班级管理</span>
            </template>
          </el-menu-item>

          <el-menu-item index="/school/subject">
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
        <span style="margin-left: 10px;">在线考试系统 - 后台管理</span>

        <div>

          <el-tooltip effect="dark" content="项目 github 卑微求Star">
              <img src="../assets/favicon.png" style="height:40px; width:40px;border-radius: 50%; cursor: pointer;" @click="github"/>
          </el-tooltip>

          &nbsp;&nbsp;&nbsp;
          
          <el-tooltip effect="light" content="退出登录">
              <span class="user" @click="logout">您好：{{user.userRealname}}</span>
          </el-tooltip>
          
        </div>

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
      isCollapse: false,
      user: {
        userId: "",
        userRealname: ""
      }
    };
  },
  methods: {
    toggleCollapse() {
      this.isCollapse = !this.isCollapse;
    },
    //获取已登录用户的信息
    getLoginUserInfo() {
      this.$http
        .get("auth/auth/verify")
        .then(response => {
          const res = response.data;
          if (res.httpCode === 200) {
            this.user = res.data;
          }
        })
        .catch(error => {});
    },
    //github
    github(){
      window.open('https://github.com/hongfurui2014/exam_online_hongfurui')
    },
    //退出登录
    async logout(){
      const res = await this.$http.delete('auth/auth/logout')
      if(res.data.httpCode == 200){
        this.$message.success("退出成功！");
        this.$router.push("/ogin");
      }
    }
  },
  mounted() {
    this.getLoginUserInfo();
  }
};
</script>

<style lang='less' scoped>
.user{
  color: #555;
  font-size: 14px;
  font-weight: bolder;
}

.user:hover{
  cursor: pointer
}

.el-header {
  background-color: #fff;
  border-bottom: 1px solid #ddd;
  padding: 0px 40px;

  display: flex;
  justify-content: space-between;
  align-items: center;

  color: #666;
  font-size: 20px;

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
</style>