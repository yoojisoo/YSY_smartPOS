const menus = [
  {
    path: "/",
    name: "home",
    icon: "mdi-home",
    component: () => import("@/views/ysy/com/contents/Home.vue"),
  },
  {
    path: "/favorite",
    name: "favorite",
    icon: "mdi-home",
    component: () => import("@/views/ysy/com/contents/Favorite.vue"),
  },
  {
    path: "/layout",
    name: "layout",
    icon: "mdi-home",
    component: () => import("@/views/ysy/com/contents/Layout.vue"),
  },
  {
    path: "/transfer",
    name: "transfer",
    icon: "mdi-home",
    component: () => import("@/views/ysy/com/contents/Transfer.vue"),
  },
  {
    path: "/device",
    name: "device",
    icon: "mdi-home",
    component: () => import("@/views/ysy/com/contents/Device.vue"),
  },
  {
    path: "/function",
    name: "function",
    icon: "mdi-home",
    component: () => import("@/views/ysy/com/contents/Function.vue"),
  },
  {
    path: "/history",
    name: "history",
    icon: "mdi-home",
    component: () => import("@/views/ysy/com/contents/History.vue"),
  },
  {
    path: "/log",
    name: "log",
    icon: "mdi-home",
    component: () => import("@/views/ysy/com/contents/Log.vue"),
  },
  {
    path: "/system",
    name: "system",
    icon: "mdi-home",
    component: () => import("@/views/ysy/com/contents/System.vue"),
  },
];

export default menus;
