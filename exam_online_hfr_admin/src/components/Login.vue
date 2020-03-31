<template>
  <div class="login_container">
    <div class="login_box">
      <!-- 头像区域 -->
      <div class="avatar_box">
        <img src="../assets/favicon.png" alt />
      </div>

      <!-- 登录表单区域 -->
      <el-form
        :model="loginForm"
        :rules="loginFormRules"
        label-width="0px"
        class="login_form"
        size="mini"
        ref="loginFormRef"
      >
        <div style="padding: 10px;text-align: center; color: #666;font-size: 12px;">在线考试系统 - 后台管理登录</div>
        <!-- 用户名 -->
        <el-form-item prop="username">
          <el-input v-model="loginForm.username" prefix-icon="el-icon-user" placeholder="用户名"></el-input>
        </el-form-item>

        <!-- 密码 -->
        <el-form-item prop="password">
          <el-input
            show-password
            v-model="loginForm.password"
            prefix-icon="el-icon-lock"
            placeholder="密码"
          ></el-input>
        </el-form-item>

        <!-- 按钮 -->
        <el-form-item class="btns">
          <el-button type="primary" @click="login" style="width: 100%;">立 即 登 录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      loginForm: {
        username: "demo_user",
        password: "demo_user"
      },

      //验证规则
      loginFormRules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          { min: 3, max: 16, message: "长度在 3 到 16 个字符", trigger: "blur" }
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 5, max: 20, message: "长度在 5 到 20 个字符", trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    login() {
      this.$refs.loginFormRef.validate(valid => {
        //校验失败
        if (!valid) {
          return;
        }

        let param = new URLSearchParams();
        param.append("username", this.loginForm.username);
        param.append("password", this.loginForm.password);

        this.$http
          .post("auth/auth/login", param)
          .then(response => {
            const res = response.data;
            if (res.httpCode === 200) {
              this.$message.success(res.message);
              this.$router.push("/"); //转到首页
            } else if (res.httpCode === 600) {
              this.$message.error(res.message);
            }
          })
          .catch(error => {
            console.log(error);
            this.$notify.error({
              title: error.response.data.message
            });
          });
      });
    }
  }
};
</script>

<style lang='less' scoped>
.login_container {
  background-color: #2b4b6b;
  height: 100%;
}

.login_box {
  z-index: 1000;
  width: 270px;
  height: 240px;
  background-color: #fff;
  border-radius: 5px;
  border: 1px #ccc solid;

  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);

  .avatar_box {
    height: 100px;
    width: 100px;
    border: 2px solid #eee;
    border-radius: 50%;
    padding: 10px;
    box-shadow: 0 0 20px #ddd;

    position: absolute;
    left: 50%;
    transform: translate(-50%, -50%);

    background-color: #ddd;

    img {
      width: 100%;
      height: 100%;
      border-radius: 50%;
      background-color: #eee;
    }
  }
}

.login_form {
  position: absolute;
  bottom: 0px;
  width: 100%;
  padding: 0px 20px;
  box-sizing: border-box;
}

.btns {
  //display: flex;
  //justify-content:flex-end
  text-align: center;
}
</style>