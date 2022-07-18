/**
 * @author clubbboy@naver.com
 * @create date 2022-07-16
 * @desc   Router 모듈화
 */

 export default [
	{ path: '/excelUpDown', name:"excelUpDown", component: () => import('@/views/testPages/ExcelUpDown.vue') }, 
	
];