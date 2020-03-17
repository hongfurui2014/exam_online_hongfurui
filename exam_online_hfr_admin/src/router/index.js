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
      path: '/home',
      component: Home,
      redirect: '/welcome',
      children: [
        { path: '/welcome', component: Welcome },
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
    },
    {
      path: '/',
      redirect: '/login'
    }
  ]
})

// 挂载路由导航守卫
router.beforeEach((to, from, next) => {
  // to 将要访问的路径
  // from 代表从哪个路径跳转而来
  // next 是一个函数，表示放行
  //     next()  放行    next('/login')  强制跳转
  //if (to.path === '/login') return next()
  next()
})

export default router