import Vue from "vue";
import VueRouter from "vue-router";
// import vMenus from "../assets/common/vMenus.js";
// import v_pages from "../assets/common/vPages.js";

Vue.use(VueRouter);
// vMenus.getMenuList();
// const routes = vMenus.concat(v_pages);
const routes = [
  { path: "/"         , name: "home"  , icon: "mdi-home", component: () => import("../views/homeLayout.vue"),},
  { path: "/userPage" , name: "user"  , icon: "mdi-home", component: () => import("../views/userLayout.vue"), },
  
  
  
  
  { path: "/adminPage", name: "admin" , icon: "mdi-home", component: () => import("../views/adminLayout.vue"), },




  { path: "/signIn"   , name: "signIn"                  , component: () => import("../views/sign/SignIn.vue"),},
  { path: "/signUp"   , name: "signUp"                  , component: () => import("../views/sign/SignUp.vue"), },
  { path: "/403"                                        , component: () => import("../views/error/NotFound.vue"), }, // 권한 없는 페이지 - 페이지 생성하고나서 변경하기
  { path: "/404"                                        , component: () => import("../views/error/NotFound.vue"), }, // 정의되지 않은 나머지 모든 페이지들 - 404 페이지로 redirect
  { path: "/:pathMatch(.*)*", redirect: "/404", },
]

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
