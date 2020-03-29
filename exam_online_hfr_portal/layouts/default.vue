<template>
  <div>
    <div class="head">
      <el-menu :default-active="$route.path" class="container" mode="horizontal" router>
        <el-menu-item index="/logo" disabled id="logo">
          <img
            src="@/static/itpanlogo.png"
            style="height:45px; width:45px"
            @click="toHome"
            alt="在线考试系统"
          />
        </el-menu-item>
        <el-menu-item index="/">
          <i class="el-icon-discount"></i>首页
        </el-menu-item>
        <el-menu-item index="/examCenter">
          <i class="el-icon-copy-document"></i>考试中心
        </el-menu-item>
        <el-menu-item index="/historyExam">
          <i class="el-icon-document-copy"></i>历史考试
        </el-menu-item>
        <el-menu-item index="/login" style="float:right;" v-if="$store.state.isLogin == false">登录/注册</el-menu-item>
        <el-menu-item
          style="float:right;"
          index="/login"
          v-if="$store.state.isLogin == true"
          @click="logoutQ"
        >退出登录</el-menu-item>
        <el-menu-item
          disabled
          style="float:right; color:#666;font-weight: bold"
          index
          v-if="$store.state.isLogin == true"
        >您好：{{$store.state.user.userRealname}}</el-menu-item>
      </el-menu>
    </div>
    <div class="container main" ref="main">
      <nuxt />
    </div>
    <div class="container footer">
      <p>
        © 2020&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;开发者：洪福锐&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;<a
          target="_blank"
          style="text-decoration:none;color: #666;"
          href="https://github.com/hongfurui2014/exam_online_hongfurui"
        >项目Github</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;<a
          target="_blank"
          style="text-decoration:none;color: #666;"
          href="http://hongfurui.itdupan.com/"
        >后台入口</a>
      </p>
      <p>
        粤ICP备18050176号-2&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;<a
          target="_blank"
          href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=44081102000045"
          style="text-decoration:none;color: #666;"
        >
          <img
            src="@/static/beian.png"
            style="display:inline-block;width: 20px; height: 20px;position: relative;bottom: -5px;"
          /> 粤公网安备44081102000045号
        </a>
      </p>
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
      clientHeight: ""
    };
  },
  methods: {
    toHome() {
      this.$router.push("/");
    },
    //退出登录
    async logoutQ() {
      const res = await this.$http.delete("auth/auth/logoutQ");
      if (res.data.httpCode == 200) {
        this.$message.success("退出成功！");
        this.$store.commit("setIsLogin", false);
        this.$router.push("/login");
      }
    },
    changeFixed(clientHeight) {
      //动态修改样式
      this.$refs.main.style.minHeight = clientHeight - 190 + "px";
    }
  },
  mounted() {
    this.clientHeight = `${document.documentElement.clientHeight}`;

    //es6写法
    window.onresize = () => {
      return (() => {
        this.clientHeight = `${document.documentElement.clientHeight}`;
      })();
    };

    //一般js写法
    // window.onresize = function temp() {
    //   this.clientHeight = `${document.documentElement.clientHeight}`;
    // };
  },
  watch: {
    clientHeight: function() {
      this.changeFixed(this.clientHeight);
    }
  }
};
</script>

<style lang='less' scoped>
.head {
  border-bottom: 1px #ccc solid;
  box-shadow: 2px 2px 3px #ccc !important;
  background-color: #fff;

  width: 100%;

  // position: fixed;
  // top: 0px;
  // z-index: 9999;
}

.container {
  max-width: 1250px;
  min-width: 1250px;

  margin: 0 auto;
}

.main {
  // border: solid red 1px;
}

.footer {
  text-align: center;
  font-size: 12px;
  font-weight: bold;
  color: #666;
  padding: 8px 0px 20px 0px;

  // border: 1px solid red;
}

.el-menu-item.is-disabled {
  opacity: 1;
}

#logo {
  cursor: pointer;
}
</style>