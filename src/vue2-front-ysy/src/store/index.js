import Vue from 'vue';
import Vuex from 'vuex';
import createPersistedState from 'vuex-persistedstate';

Vue.use(Vuex);

import authStore from './modules/authStore.js';
import gridStore from './modules/GridStore';
import noticeStore from './modules/NoticeStore';

const store = new Vuex.Store({
	modules: {
		authStore,
		gridStore,
		noticeStore
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
