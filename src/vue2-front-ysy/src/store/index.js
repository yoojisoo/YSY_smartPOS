import Vue from 'vue';
import Vuex from 'vuex';
import createPersistedState from 'vuex-persistedstate';

Vue.use(Vuex);

import authStore from './modules/AuthStore.js';
import userStore from './modules/UserStore';
import noticeStore from './modules/NoticeStore';

const store = new Vuex.Store({
	modules: {
		authStore,
		userStore,
		noticeStore,
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
