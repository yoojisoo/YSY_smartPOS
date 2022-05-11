const pages = [
  {
    path: "/signIn",
    name: "signIn",
    component: () => import("@/components/sign/SignIn.vue"),
  },
  {
    path: "/signUp",
    name: "signUp",
    component: () => import("@/components/sign/SignUp.vue"),
  },
  {
    path: "/403",
    component: () => import("@/views/error/NotFound.vue"),
  }, // 권한 없는 페이지 - 페이지 생성하고나서 변경하기
  {
    path: "/404",
    component: () => import("@/views/error/NotFound.vue"),
  }, // 정의되지 않은 나머지 모든 페이지들 - 404 페이지로 redirect
  {
    path: "/:pathMatch(.*)*",
    redirect: "/404",
  },
];

export default pages;
