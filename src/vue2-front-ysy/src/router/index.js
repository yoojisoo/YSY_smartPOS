import Vue from 'vue';
import VueRouter from 'vue-router';
import store from '@/store/index.js';

/** 로그인 상태를 체크해서
 * 이동가능한 페이지인지 확인 */
const isLoginChk = () => (to, from, next) => {
	//console.log(store);
	console.log(store.getters['isLogin']);
	if (store.getters['isLogin']) {
		alert('로그인 상태로는 이동하실 수 없는 페이지입니다.\n로그아웃을 먼저 진행해주세요.');
		next('/');
	}
	return next();
};

Vue.use(VueRouter);
// vMenus.getMenuList();
// const routes = vMenus.concat(v_pages);
const routes = [
	// 메인 메뉴들
	{
		path: '/',
		name: 'home',
		icon: 'mdi-home',
		component: () => import('../views/contents/Home.vue'),
	},
	{
		path: '/userInfo',
		name: 'userInfo',
		icon: 'mdi-account',
		component: () => import('../views/contents/UserInfo.vue'),
	},
	{
		path: '/userPage',
		name: 'user',
		icon: 'mdi-home',
		component: () => import('../views/userLayout.vue'),
	},
	{
		path: '/adminPage',
		name: 'admin',
		icon: 'mdi-home',
		component: () => import('../views/adminLayout.vue'),
	},

	// 회원가입 로그인 페이지
	{
		path: '/signIn',
		name: 'signIn',
		component: () => import('../views/sign/SignIn.vue'),
		beforeEnter: isLoginChk(),
	},
	{
		path: '/signUp',
		name: 'signUp',
		component: () => import('../views/sign/SignUp.vue'),
		beforeEnter: isLoginChk(),
	},

	// 공지사항 관련
	{
		path: '/systemNotice',
		name: 'systemNotice',
		component: () => import('../views/contents/SystemNotice.vue'),
	},
	{
		path: '/storeNotice',
		name: 'storeNotice',
		component: () => import('../views/contents/storeNotice.vue'),
	},
	{
		path: '/qnaNotice',
		name: 'qnaNotice',
		component: () => import('../views/contents/qnaNotice.vue'),
	},

	// 사용자관리 관련
	{
		path: '/userMng',
		name: 'userMng',
		component: () => import('../views/contents/userMng.vue'),
	},

	
	{
		path: '/kakaoLogin',
		name: 'kakaoLogin',
		component: () => import('../views/sign/KakaoLogin.vue'),
		props: true,
	},
	{
		path: '/naverLogin',
		name: 'naverLogin',
		component: () => import('../views/sign/NaverLogin.vue'),
		props: true,
	},


	








	// 에러페이지
	{ path: '/403', component: () => import('../views/error/NotFound.vue') }, // 권한 없는 페이지 - 페이지 생성하고나서 변경하기
	{ path: '/404', component: () => import('../views/error/NotFound.vue') }, // 정의되지 않은 나머지 모든 페이지들 - 404 페이지로 redirect
	{ path: '/:pathMatch(.*)*', redirect: '/404' },
];

const router = new VueRouter({
	mode: 'history',
	base: process.env.BASE_URL,
	routes,
});

export default router;
