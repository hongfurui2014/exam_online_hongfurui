import Vue from 'vue'
import Router from 'vue-router'
import Login from '../components/Login'
import Home from '../components/Home'
import Welcome from '../components/Welcome'
import User from '../components/system/User'
import UserQ from '../components/system/UserQ'
import Role from '../components/system/Role'
import Grade from '../components/school/Grade'
import Subject from '../components/school/Subject'
import Topic from '../components/test/Topic'
import Test from '../components/test/Test'
import Statistics from '../components/test/Statistics'
import InfoList from '../components/info/InfoList'

import axios from 'axios'
axios.defaults.baseURL = "http://zuul.itdupan.com/api/"	//可以设置根路径
// 允许携带cookie
axios.defaults.withCredentials = true

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
        { path: '/system/userQ', component: UserQ },
        { path: '/system/role', component: Role },
        { path: '/school/grade', component: Grade },
        { path: '/school/subject', component: Subject },
        { path: '/test/topic', component: Topic },
        { path: '/test/test', component: Test },
        { path: '/test/statistics', component: Statistics },
        { path: '/info/InfoList', component: InfoList }
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
  const token_cookie = getCookie('HFR_TOKEN');
  if (to.path === '/login') { //访问登录页
    if (token_cookie) {
      return next('/admin');
    } else {
      return next();
    }
  } else {  //访问非登录页
    if (!token_cookie) {  //没有登录，转到登录页
      return next('/login');
    } else {  //已登录，查询当前访问路径，该用户是否有权限访问
      if(to.path === '/admin'){
        return next();
      }
      axios.get("auth/auth/verify")
        .then(response => {
          const res = response.data;
          if (res.httpCode === 200) {
            //根据用户id去后台获取对应的角色以及对应的所有菜单权限，返回菜单列表用于界面展示菜单
            axios.get("user/user/findRolesRightsByUserId", {
              params: {
                userId: res.data.userId
              }
            })
              .then(response => {
                const res2 = response.data;
                if (res2.httpCode === 200) {
                  const rights = res2.data;
                  let flag = false;
                  for (let i = 0; i < rights.length; i++) {
                    for (let j = 0; j < rights[i].children.length; j++) {
                      if (to.path === rights[i].children[j].rightsPath) {
                        flag = true;
                        return next();
                      }
                    }
                  }
                  if (!flag) {
                    return next('/admin');
                  }
                }
              })
              .catch(error => { });
          }
        })
        .catch(error => { });
    }
  }
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