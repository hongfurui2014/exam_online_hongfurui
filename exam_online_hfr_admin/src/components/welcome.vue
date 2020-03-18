<template>
  <div>
    <el-card shadow="always" body-style="padding: 5px 20px;">
      welcome
    </el-card>
  </div>
</template>


<script>
export default {
  data() {
    return {
      value1: ""
    };
  },
  methods: {
    clickBtn: function() {
      console.log(this.value1);
    }
  }
};
</script>

<style lang='less' scoped>
</style>