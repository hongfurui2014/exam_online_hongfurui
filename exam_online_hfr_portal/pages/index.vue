<template>
  <div>
    <el-card class="box-card" style="font-size: 13px; color: #666;text-align: center;">系统若有bug以后有时间再慢慢修复优化了。奥利给！</el-card>
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