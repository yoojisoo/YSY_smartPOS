const menus = [
  {
    path: "/",
    name: "home",
    icon: "mdi-home",
    component: () => import("@/views/contents/Home.vue"),
  },
  {
    path: "/favorite",
    name: "favorite",
    icon: "mdi-home",
    component: () => import("@/views/contents/Favorite.vue"),
  },
  {
    path: "/layout",
    name: "layout",
    icon: "mdi-home",
    component: () => import("@/views/contents/Layout.vue"),
  },
  {
    path: "/transfer",
    name: "transfer",
    icon: "mdi-home",
    component: () => import("@/views/contents/Transfer.vue"),
  },
  {
    path: "/device",
    name: "device",
    icon: "mdi-home",
    component: () => import("@/views/contents/Device.vue"),
  },
  {
    path: "/function",
    name: "function",
    icon: "mdi-home",
    component: () => import("@/views/contents/Function.vue"),
  },
  {
    path: "/history",
    name: "history",
    icon: "mdi-home",
    component: () => import("@/views/contents/History.vue"),
  },
  {
    path: "/log",
    name: "log",
    icon: "mdi-home",
    component: () => import("@/views/contents/Log.vue"),
  },
  {
    path: "/system",
    name: "system",
    icon: "mdi-home",
    component: () => import("@/views/contents/System.vue"),
  },
];

export default menus;
