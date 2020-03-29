<template>
  <div style="margin: 20px;">
    <div class="register_box">
      <!-- 登录表单区域 -->
      <el-form
        :model="addUserQForm"
        :rules="userQFormRule"
        label-width="0px"
        class="register_form"
        size="mini"
        ref="addFormRef"
      >
        <div
          style="padding: 25px; color: #666;font-size: 18px; text-align: center;font-weight: bold;"
        >在线考试系统注册</div>
        <!-- 登录账户 -->
        <el-form-item prop="userQAccount">
          <el-input
            v-model="addUserQForm.userQAccount"
            prefix-icon="el-icon-user"
            placeholder="登录账户"
          ></el-input>
        </el-form-item>

        <!-- 真实姓名 -->
        <el-form-item prop="userQRealname">
          <el-input
            v-model="addUserQForm.userQRealname"
            prefix-icon="el-icon-user"
            placeholder="真实姓名"
          ></el-input>
        </el-form-item>

        <!-- 密码 -->
        <el-form-item prop="userQPassword">
          <el-input
            show-password
            v-model="addUserQForm.userQPassword"
            prefix-icon="el-icon-lock"
            placeholder="密码"
          ></el-input>
        </el-form-item>

        <!-- 所属班级 -->
        <el-form-item prop="fkUserQGradeId">
          <el-select
            v-model="addUserQForm.fkUserQGradeId"
            placeholder="- 请选择所属班级 -"
            style="width: 100%;"
          >
            <el-option
              v-for="item in addUserQForm.gradeQ"
              :key="item.gradeId"
              :label="item.gradeName"
              :value="item.gradeId"
            ></el-option>
          </el-select>
        </el-form-item>

        <!-- 按钮 -->
        <el-form-item>
          <el-button type="primary" @click="addUserQYes" style="width: 100%;">立 即 注 册</el-button>
        </el-form-item>

        <el-form-item style="margin-top: 0px;">
          <el-button
            type="text"
            style="float: right;border: solid 1px solid;margin-top: 0px;"
            @click="toLogin"
          >立即登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      //添加form
      addUserQForm: {
        userQAccount: "",
        userQPassword: "",
        userQRealname: "",
        gradeQ: [],
        fkUserQGradeId: ""
      },

      //添加表单验证规则
      userQFormRule: {
        userQAccount: [
          { required: true, message: "请输入登录账户", trigger: "blur" },
          { min: 3, max: 16, message: "长度在 3 到 16 个字符", trigger: "blur" }
        ],
        userQPassword: [
          { required: true, message: "请输入账户密码", trigger: "blur" },
          { min: 5, max: 20, message: "长度在 5 到 20 个字符", trigger: "blur" }
        ],
        userQRealname: [
          { required: true, message: "请输入真实姓名", trigger: "blur" },
          { min: 2, max: 7, message: "长度在 2 到 7 个字符", trigger: "blur" }
        ],
        fkUserQGradeId: [
          { required: true, message: "请选择所属班级", trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    toLogin() {
      this.$router.push("/login");
    },
    //为添加表单添加班级列表
    getAddGrades() {
      this.$http
        .get("school/grade/findGrades")
        .then(response => {
          const res = response.data;
          if (res.httpCode === 200) {
            this.addUserQForm.gradeQ = res.data;
          }
        })
        .catch(error => {});
    },
    //注册考试用户
    addUserQYes() {
      this.$refs.addFormRef.validate(valid => {
        if (valid) {
          //校验通过
          this.$http
            .post("user/userQ/addUserQ", this.addUserQForm)
            .then(response => {
              const res = response.data;
              if (res.httpCode === 201) {
                this.$message.success("注册成功！");
                this.$router.push("/login");
              } else if (res.httpCode === 600) {
                this.addUserQForm.userQName = "";
                this.$message.error(res.message);
              }
            })
            .catch(error => {});
        }
      });
    }
  },
  mounted() {
    this.getAddGrades();
  }
};
</script>

<style lang='less' scoped>
.register_box {
  width: 270px;
  height: 345px;
  background-color: #fff;
  border-radius: 5px;
  border: 1px solid #ccc;

  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}

.register_form {
  position: absolute;
  top: 0px;
  width: 100%;
  padding: 0px 20px;
  box-sizing: border-box;
}

.btns {
  text-align: center;
}
</style>