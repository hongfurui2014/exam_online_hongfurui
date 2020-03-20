<template>
  <div>
    <div class="head">
      <el-menu
        :default-active="$route.path"
        mode="horizontal"
        @select="handleSelect"
        class="container"
      >
        <el-menu-item>
          <img src="@/static/itpanlogo.png" style="height:50px; width:50px" alt="在线考试系统" />
        </el-menu-item>
        <el-menu-item index="/">
          <nuxt-link to="/">首页</nuxt-link>
        </el-menu-item>
        <el-menu-item index="/examCenter">
          <nuxt-link to="/examCenter" class="text-none">考试中心</nuxt-link>
        </el-menu-item>
        <el-menu-item index="/historyExam">
          <nuxt-link to="/historyExam" class="text-none">历史考试</nuxt-link>
        </el-menu-item>
        <el-menu-item index="/login" style="float:right;" v-if="$store.state.isLogin == false">
          <nuxt-link to="/login" class="text-none">登录/注册</nuxt-link>
        </el-menu-item>
        <el-menu-item style="float:right;" v-if="$store.state.isLogin == true">
          <nuxt-link to="/login" class="text-none">退出登录</nuxt-link>
        </el-menu-item>
        <el-menu-item
          style="float:right;"
          v-if="$store.state.isLogin == true"
        >您好：{{$store.state.user.userRealname}}</el-menu-item>
      </el-menu>
    </div>
    <div class="container">
      <nuxt />
    </div>
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
      activeIndex: "/",
      user: {
        userId: "",
        userRealname: ""
      }
    };
  },
  methods: {
    handleSelect(key, keyPath) {},
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
        .catch(error => {});
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

  display: block;
  margin: 0 auto;
  border-bottom: 0px #ccc solid;
}

.nuxt-link-exact-active,
.nuxt-link-active,
.text-none {
  text-decoration: none;
}
</style>