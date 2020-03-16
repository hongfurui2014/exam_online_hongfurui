<template>
  <div>
    <el-card shadow="always" body-style="padding: 5px 20px;">
      <el-date-picker
        v-model="value1"
        type="datetime"
        format="yyyy-MM-dd hh:mm:ss"
        value-format="yyyy-MM-dd hh:mm:ss"
        placeholder="选择日期时间"
      ></el-date-picker>
      <el-button type="primary" @click="clickBtn">打印选择的时间</el-button>
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