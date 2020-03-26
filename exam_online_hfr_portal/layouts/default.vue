<template>
  <div>
    <div class="head">
      <el-menu
        :default-active="$route.path"
        class="container"
        mode="horizontal"
        router
      >
        <el-menu-item index="/logo" disabled="" id="logo">
          <img src="@/static/itpanlogo.png" style="height:45px; width:45px" @click="toHome" alt="在线考试系统" />
        </el-menu-item>
        <el-menu-item index="/"><i class="el-icon-discount"></i>首页</el-menu-item>
        <el-menu-item index="/examCenter"><i class="el-icon-copy-document"></i>考试中心</el-menu-item>
        <el-menu-item index="/historyExam"><i class="el-icon-document-copy"></i>历史考试</el-menu-item>
        <el-menu-item index="/login" style="float:right;" v-if="$store.state.isLogin == false">登录/注册</el-menu-item>
        <el-menu-item
          style="float:right;"
          index="/login"
          v-if="$store.state.isLogin == true"
        >退出登录</el-menu-item>
        <el-menu-item
          disabled
          style="float:right; color:#666;font-weight: bold"
          index=""
          v-if="$store.state.isLogin == true"
        >您好：{{$store.state.user.userRealname}}</el-menu-item>
      </el-menu>
    </div>
    <div class="container">
      <nuxt />
    </div>
    <div class="container footer">footer</div>
  </div>
</template>

<script>
import Vue from "vue";
import axios from "axios";
axios.defaults.baseURL = "http://zuul.itdupan.com/api/"; //可以设置根路径

// 允许携带cookie
axios.defaults.withCredentials = true;

Vue.prototype.$http = axios;

export default {
  data() {
    return {
      activeIndex: "/"
    };
  },
  methods: {
    toHome() {
      this.$router.push("/");
    },
    //获取已登录用户的信息
    getLoginUserInfo() {
      this.$http
        .get("auth/auth/verifyQ")
        .then(response => {
          const res = response.data;
          if (res.httpCode === 200) {
            this.$store.commit("setUser", res.data);
            this.$store.commit("setIsLogin", true);
          }
        })
        .catch(error => {
          this.$router.push("/login"); //转到登录页
        });
    }
  },
  mounted() {
    this.getLoginUserInfo();
  }
};
</script>

<style lang='less' scoped>
.head {
  border-bottom: 1px #ccc solid;
  box-shadow: 2px 2px 3px #ccc !important;
  background-color: #fff;
}

.container {
  max-width: 1250px;
  min-width: 1250px;

  margin: 0 auto;
}

.main {
  height: 100%;
  background-color: red;
}

.footer {
  text-align: center;
}

.el-menu-item.is-disabled{
  opacity: 1;
}

#logo{
  cursor: pointer;
}

// .nuxt-link-exact-active,
// .nuxt-link-active,
// .text-none {
//   text-decoration: none;
// }
</style>