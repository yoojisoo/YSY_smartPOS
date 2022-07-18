import Vue from 'vue';
import Vuex from 'vuex';
import createPersistedState from 'vuex-persistedstate';

Vue.use(Vuex);

import authStore from './modules/AuthStore.js';
import menuStore from './modules/MenuStore.js';
import noticeStore from './modules/NoticeStore';
import userStore from './modules/UserStore';
import excelStore from './modules/ExcelStore';//by clubbboy@naver.com 2022 07 17

const store = new Vuex.Store({
	modules: {
		authStore,
		menuStore,
		noticeStore,
		userStore,
		excelStore,
	},
	plugins: [
		createPersistedState({
			paths: ['authStore.loginData'],
			storage: sessionStorage,
			key: 'loginData',
		}),
	],
});

export default store;
