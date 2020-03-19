<template>
  <div>
    <div v-if="error.statusCode == 404">404错误</div>
    <div v-else>500错误</div>
  </div>
</template>

<script>
export default {
    props: ['error']
}
</script>

<style lang='less' scoped>
</style>