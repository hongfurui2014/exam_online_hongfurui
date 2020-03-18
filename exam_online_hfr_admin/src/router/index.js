import Vue from 'vue'
import Router from 'vue-router'
import Login from '../components/Login'
import Home from '../components/Home'
import Welcome from '../components/Welcome'
import User from '../components/system/User'
import Role from '../components/system/Role'
import Grade from '../components/school/Grade'
import Subject from '../components/school/Subject'
import Topic from '../components/test/Topic'
import Test from '../components/test/Test'

Vue.use(Router)

const router = new Router({
  routes: [
    {
      path: '/',
      component: Home,
      redirect: '/admin',
      children: [
        { path: '/admin', component: Welcome },
        { path: '/system/user', component: User },
        { path: '/system/role', component: Role },
        { path: '/school/grade', component: Grade },
        { path: '/school/subject', component: Subject },
        { path: '/test/topic', component: Topic },
        { path: '/test/test', component: Test }
      ]
    },
    {
      path: '/login',
      component: Login
    }
  ]
})

// 挂载路由导航守卫
router.beforeEach((to, from, next) => {
  // to 将要访问的路径
  // from 代表从哪个路径跳转而来
  // next 是一个函数，表示放行
  // next()  放行 next('/login')  强制跳转
  if (to.path === '/login') return next();
  const token_cookie = getCookie('HFR_TOKEN');
  if (!token_cookie) return next('/login');
  next();
})

function getCookie(name) {
  var strcookie = document.cookie;//获取cookie字符串
  var arrcookie = strcookie.split("; ");//分割
  //遍历匹配
  for (var i = 0; i < arrcookie.length; i++) {
    var arr = arrcookie[i].split("=");
    if (arr[0] == name) {
      return arr[1];
    }
  }
  return "";
}

export default router