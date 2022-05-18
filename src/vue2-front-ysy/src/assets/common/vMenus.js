// const menus = [
//   {
//     path: "/",
//     name: "관리자",
//     icon: "mdi-home",
//     component: () => import("../../views/contents/admin.vue"),
//   },
//   {
//     path: "/favorite",
//     name: "상품관리",
//     icon: "mdi-home",
//     component: () => import("@/views/contents/Favorite.vue"),
//   },
//   {
//     path: "/layout",
//     name: "정산관리",
//     icon: "mdi-home",
//     component: () => import("@/views/contents/Layout.vue"),
//   },
//   {
//     path: "/transfer",
//     name: "예약관리",
//     icon: "mdi-home",
//     component: () => import("@/views/contents/Transfer.vue"),
//   },
//   {
//     path: "/device",
//     name: "프로모션",
//     icon: "mdi-home",
//     component: () => import("@/views/contents/Device.vue"),
//   },
//   {
//     path: "/function",
//     name: "캘린더",
//     icon: "mdi-home",
//     component: () => import("@/views/contents/Function.vue"),
//   },
// ]

import axios from 'axios'
const menus=[
    {
        path: "/",
        name: "관리자",
        icon: "mdi-home",
        component: () => import("../../views/contents/admin.vue"),
    },
]


export default menus;
