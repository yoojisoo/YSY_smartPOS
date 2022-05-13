const menus = [
  {
    path: "/",
    name: "고객관리",
    icon: "mdi-home",
    component: () => import("@/views/contents/Home.vue"),
  },
  {
    path: "/favorite",
    name: "상품관리",
    icon: "mdi-home",
    component: () => import("@/views/contents/Favorite.vue"),
  },
  {
    path: "/layout",
    name: "정산관리",
    icon: "mdi-home",
    component: () => import("@/views/contents/Layout.vue"),
  },
  {
    path: "/transfer",
    name: "예약관리",
    icon: "mdi-home",
    component: () => import("@/views/contents/Transfer.vue"),
  },
  {
    path: "/device",
    name: "프로모션",
    icon: "mdi-home",
    component: () => import("@/views/contents/Device.vue"),
  },
  {
    path: "/function",
    name: "캘린더",
    icon: "mdi-home",
    component: () => import("@/views/contents/Function.vue"),
  },
  // {
  //   path: "/history",
  //   name: "history",
  //   icon: "mdi-home",
  //   component: () => import("@/views/contents/History.vue"),
  // },
  // {
  //   path: "/log",
  //   name: "log",
  //   icon: "mdi-home",
  //   component: () => import("@/views/contents/Log.vue"),
  // },
  // {
  //   path: "/system",
  //   name: "system",
  //   icon: "mdi-home",
  //   component: () => import("@/views/contents/System.vue"),
  // },
];

export default menus;
