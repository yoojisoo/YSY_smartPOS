const menus = [
    { path: '/'         , name: 'home'     , icon: 'mdi-home', component: () => import('@/views/HomeView.vue')     },      
    { path: '/favorite' , name: 'favorite' , icon: 'mdi-home', component: () => import('@/views/FavoriteView.vue') },
    { path: '/layout'   , name: 'layout'   , icon: 'mdi-home', component: () => import('@/views/LayoutView.vue')   },
    { path: '/transfer' , name: 'transfer' , icon: 'mdi-home', component: () => import('@/views/TransferView.vue') },
    { path: '/device'   , name: 'device'   , icon: 'mdi-home', component: () => import('@/views/DeviceView.vue')   },
    { path: '/function' , name: 'function' , icon: 'mdi-home', component: () => import('@/views/FunctionView.vue') },
    { path: '/history'  , name: 'history'  , icon: 'mdi-home', component: () => import('@/views/HistoryView.vue')  },
    { path: '/log'      , name: 'log'      , icon: 'mdi-home', component: () => import('@/views/LogView.vue')      },
    { path: '/system'   , name: 'system'   , icon: 'mdi-home', component: () => import('@/views/SystemView.vue')   }
]

export default menus