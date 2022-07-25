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
];
