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
		path: '/summernote2',
		name: 'summernote2',
		component: () => import('@/components/1_molecules/editors/SummerNote.vue'),
	},
	{
		path: '/adminBoardMod',
		name: 'adminBoardMod',
		component: () => import('@/views/testPages/AdminFreeBoardMod.vue'),
	},
	{
		path: '/adminSchedule',
		name: 'adminSchedule',
		component: () => import('@/views/testPages/AdminSchedule.vue'),
	},
	{
		path: '/chatingSample',
		name: 'chatingSample',
		component: () => import('@/views/testPages/ChatingSample.vue'),
	},
	{
		path: '/templateSample',
		name: 'templateSample',
		component: () => import('@/views/testPages/TemplateSample.vue'),
	},
];
