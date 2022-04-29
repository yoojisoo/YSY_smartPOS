const pages = [
    {
        path: '/signIn',
        component: () => import('@/views/SignIn.vue'),
    },
    {
        path: '/403', // 권한 없는 페이지
        component: () => import('@/views/NotFound.vue'), // 페이지 생성하고나서 변경하기
    },
    {
        path: '/404',
        component: () => import('@/views/NotFound.vue'),
    },
    {
        path: '/:pathMatch(.*)*', // 정의되지 않은 나머지 모든 페이지들
        redirect: '/404', // '/404' 페이지로 redirect
    },
]

export default pages