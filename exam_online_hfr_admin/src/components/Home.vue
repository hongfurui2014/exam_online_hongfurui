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
          <i class="el-icon-house"></i>
          <span>后台首页</span>
        </el-menu-item>

        <el-submenu :index="right.rightsId + ''" v-for="right in rights" :key="right.rightsId">
          <template slot="title">
            <i :class="right.rightsIcon"></i>
            <span>{{right.rightsAuthname}}</span>
          </template>

          <el-menu-item :index="r.rightsPath + ''" v-for="r in right.children" :key="r.rightsId">
            <template slot="title">
              <i :class="r.rightsIcon"></i>
              <span>{{r.rightsAuthname}}</span>
            </template>
          </el-menu-item>

        </el-submenu>

      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <span style="margin-left: 10px;">在线考试系统 - 后台管理</span>

        <div>
          <el-tooltip effect="dark" content="项目Github 卑微求Star">
            <img
              src="../assets/favicon.png"
              style="height:40px; width:40px;border-radius: 50%; cursor: pointer;"
              @click="github"
            />
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
      },
      rights: []
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

            //根据用户id去后台获取对应的角色以及对应的所有菜单权限，返回菜单列表用于界面展示菜单
            this.$http
              .get("user/user/findRolesRightsByUserId", {
                params: {
                  userId: this.user.userId
                }
              })
              .then(response => {
                const res2 = response.data;
                if (res2.httpCode === 200) {
                  this.rights = res2.data;
                }
              })
              .catch(error => {});
          }
        })
        .catch(error => {});
    },
    //github
    github() {
      window.open("https://github.com/hongfurui2014/exam_online_hongfurui");
    },
    //退出登录
    async logout() {
      const res = await this.$http.delete("auth/auth/logout");
      if (res.data.httpCode == 200) {
        this.$message.success("退出成功！");
        this.$router.push("/login");
      }
    }
  },
  mounted() {
    this.getLoginUserInfo();
  }
};
</script>

<style lang='less' scoped>
.user {
  color: #555;
  font-size: 14px;
  font-weight: bolder;
}

.user:hover {
  cursor: pointer;
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