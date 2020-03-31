// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

import './assets/css/global.css'

import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

import axios from 'axios'
axios.defaults.baseURL = "http://zuul.itdupan.com/api/"	//可以设置根路径

// 允许携带cookie
axios.defaults.withCredentials = true

//在request拦截器中展示进度条
axios.interceptors.request.use(function (config) {
  NProgress.start();
  return config;
},
  error => {
    // 对请求错误做些什么
    return Promise.reject(error);
  }
);

//在response拦截器中隐藏进度条
axios.interceptors.response.use(function (config) {
  NProgress.done();
  return config;
},
  error => {
    // 对请求错误做些什么
    NProgress.done();
    return Promise.reject(error);
  }
);

Vue.prototype.$http = axios

Vue.config.productionTip = false

Vue.use(ElementUI)

//格式化时间
Vue.filter('dateFormat', function (originVal) {
  const dt = new Date(originVal)

  const y = dt.getFullYear()
  const m = (dt.getMonth() + 1 + '').padStart(2, '0')	//不足两位前面加'0'
  const d = (dt.getDate() + '').padStart(2, '0')

  const hh = (dt.getHours() + '').padStart(2, '0')
  const mm = (dt.getMinutes() + '').padStart(2, '0')
  const ss = (dt.getSeconds() + '').padStart(2, '0')

  return `${y}-${m}-${d} ${hh}:${mm}:${ss}`
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
