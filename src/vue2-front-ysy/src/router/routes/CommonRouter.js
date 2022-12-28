/**
 * @author 신민경
 * @create date 2022-07-11
 * @desc   Router 모듈화
 */

export default [
	/** Auth Free Pages
	 * 1. Main Page
	 * 2. Community */

	/** Main Page */
	{
		path: '/',
		name: 'home',
		icon: 'mdi-home',
		component: () => import('@/views/common/Home.vue'),
	},

	{
		path: '/newArticles',
		name: 'newArticles',
		component: () => import('@/views/common/NewArticles.vue'),
	},

	/** Community */
	{
		// 시스템공지사항
		path: '/noticeSystem',
		name: 'systemNotice',
		component: () => import('@/views/common/NoticeSystem.vue'),
	},
	{
		// 스토어공지사항
		path: '/noticeStore',
		name: 'storeNotice',
		component: () => import('@/views/common/NoticeStore.vue'),
	},
	{
		// QnA
		path: '/qna',
		name: 'qna',
		component: () => import('@/views/common/Qna.vue'),
	},
];
