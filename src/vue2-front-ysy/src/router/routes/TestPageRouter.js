/**
 * @author clubbboy@naver.com
 * @create date 2022-07-16
 * @desc   Router 모듈화
 */

export default [
	{
		path: '/excelUpDown',
		name: 'excelUpDown',
		component: () => import('@/views/testPages/ExcelUpDown.vue'),
	},
	{
		path: '/summerNoteVue',
		name: 'summerNoteVue',
		component: () => import('@/views/contents/Editor.vue'),
	},
	{
		path: '/summerNote',
		name: 'summerNote',
		component: () => import('@/components/1_molecules/editors/FSummerNote.vue'),
	},
	{
		path: '/adminFreeBoard',
		name: 'adminFreeBoard',
		component: () => import('@/views/testPages/AdminFreeBoard.vue'),
	},
	{
		path: '/summernote2',
		name: 'summernote2',
		component: () => import('@/components/1_molecules/editors/SummerNote.vue'),
	},
];
