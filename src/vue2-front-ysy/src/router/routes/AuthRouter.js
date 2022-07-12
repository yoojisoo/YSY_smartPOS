/**
 * @author 신민경
 * @create date 2022-07-11 16:29:52
 * @desc   Router 모듈화
 */

export default [
	/** Auth Pages
	 * 1. Manager 영역
	 * 2. User 영역
	 * 3. MyInfo (공통) */

	/** Manager 영역
	 * 1. Store (매장)
	 * 2. Product (상품)
	 * 3. Calculate (정산)
	 * 3. User Manager (사용자)
	 * 4. Menu Manager (메뉴)*/
	{
		// 매장관리
		path: '/storeMng',
		name: 'storeMng',
		component: () => import('@/views/auth/manager/store/StoreMng.vue'),
	},
	{
		// 상품관리
		path: '/prodMng',
		name: 'prodMng',
		component: () => import('@/views/auth/manager/product/ProdMng.vue'),
	},
	{
		// 일일정산
		path: '/dailyCalculate',
		name: 'dailyCalculate',
		component: () => import('@/views/auth/manager/calculate/DailyCalculate.vue'),
	},
	{
		// 월별정산
		path: '/monthlyCalculate',
		name: 'monthlyCalculate',
		component: () => import('@/views/auth/manager/calculate/MonthlyCalculate.vue'),
	},
	{
		// 연별정산
		path: '/yearlyCalculate',
		name: 'yearlyCalculate',
		component: () => import('@/views/auth/manager/calculate/YearlyCalculate.vue'),
	},
	{
		// 사용자관리
		path: '/userMng',
		name: 'userMng',
		component: () => import('@/views/auth/manager/user/UserMng.vue'),
	},
	{
		// 메뉴권한관리
		path: '/menuRoleMng',
		name: 'menuRoleMng',
		component: () => import('@/views/auth/manager/menu/MenuRoleMng.vue'),
	},
	{
		// 메뉴버튼관리
		path: '/menuBtnMng',
		name: 'menuBtnMng',
		component: () => import('@/views/auth/manager/menu/MenuBtnMng.vue'),
	},

	/** User 영역
	 * 1. Reservation (예약)
	 * 2. Delivery (배달) */
	{
		// 예약현황
		path: '/reservationStatus',
		name: 'reservationStatus',
		component: () => import('@/views/auth/user/reservation/ReservationStatus.vue'),
	},
	{
		// 예약내역
		path: '/reservationHistory',
		name: 'reservationHistory',
		component: () => import('@/views/auth/user/reservation/ReservationHistory.vue'),
	},
	{
		// 배달현황
		path: '/deliveryStatus',
		name: 'deliveryStatus',
		component: () => import('@/views/auth/user/delivery/DeliveryStatus.vue'),
	},
	{
		// 배달내역
		path: '/deliveryHistory',
		name: 'deliveryHistory',
		component: () => import('@/views/auth/user/delivery/DeliveryHistory.vue'),
	},

	/** 공통 영역 */
	{
		path: '/myInfo',
		name: 'myInfo',
		icon: 'mdi-account',
		component: () => import('@/views/auth/MyInfo.vue'),
	},
];
