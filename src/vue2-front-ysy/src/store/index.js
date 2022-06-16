import Vue from 'vue';
import Vuex from 'vuex';
import createPersistedState from 'vuex-persistedstate';

Vue.use(Vuex);

import authStore from './modules/authStore.js';
import userStore from './modules/userStore';
import noticeStore from './modules/noticeStore';

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
