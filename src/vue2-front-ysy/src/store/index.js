import Vue from 'vue';
import Vuex from 'vuex';
import createPersistedState from 'vuex-persistedstate';

Vue.use(Vuex);

import authStore from './modules/AuthStore.js';
import menuStore from './modules/MenuStore.js';
import communityStore from './modules/CommunityStore';
import userStore from './modules/UserStore';
import excelStore from './modules/ExcelStore'; //by clubbboy@naver.com 2022 07 17
import adminFreeBoardStore from './modules/AdminFreeBoardStore'; //by clubbboy@naver.com 2022 07 17

const store = new Vuex.Store({
	modules: {
		authStore,
		menuStore,
		communityStore,
		userStore,
		excelStore,
		adminFreeBoardStore,
	},
	plugins: [
		createPersistedState({
			paths: ['authStore.loginData'],
			storage: localStorage,
		}),
	],
});

export default store;
