import Vue from 'vue';
import Vuex from 'vuex';
import createPersistedState from 'vuex-persistedstate';

Vue.use(Vuex);

import authStore from './modules/AuthStore.js';
import menuStore from './modules/MenuStore.js';
import noticeStore from './modules/NoticeStore';
import userStore from './modules/UserStore';

const store = new Vuex.Store({
	modules: {
		authStore,
		menuStore,
		noticeStore,
		userStore,
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
