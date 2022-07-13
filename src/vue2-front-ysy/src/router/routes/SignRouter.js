/**
 * @author 신민경
 * @create date 2022-07-11 16:29:52
 * @desc   Router 모듈화
 */

import store from '@/store/index.js';

/** 로그인 상태를 체크해서
 * 이동가능한 페이지인지 확인 */
const isLoginChk = () => (to, from, next) => {
	console.log(store.getters['isLogin']);
	if (store.getters['isLogin']) {
		alert('로그인 상태로는 이동하실 수 없는 페이지입니다.\n로그아웃을 먼저 진행해주세요.');
		next('/');
	}
	return next();
};

export default [
	{
		// 로그인
		path: '/signIn',
		name: 'signIn',
		component: () => import('@/views/auth/SignIn.vue'),
		beforeEnter: isLoginChk(),
	},
	{
		// 회원가입
		path: '/signUp',
		name: 'signUp',
		component: () => import('@/views/auth/SignUp.vue'),
		beforeEnter: isLoginChk(),
	},
	{
		// 카카오 로그인
		path: '/kakaoLogin',
		name: 'kakaoLogin',
		component: () => import('@/views/auth/KakaoLogin.vue'),
		props: true,
	},
	{
		// 네이버 로그인
		path: '/naverLogin',
		name: 'naverLogin',
		component: () => import('@/views/auth/NaverLogin.vue'),
		props: true,
	},
];
