<template>
  <div>
    <el-card class="box-card">首页</el-card>
  </div>
</template>

<script>
export default {
  mounted(){
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
          return;
        });
  }
};
</script>

<style lang='less' scoped>
.el-card {
  margin: 20px;
}
</style>