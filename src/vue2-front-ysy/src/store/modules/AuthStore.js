import authService from '@/service/auth/AuthService.js';
import axios from 'axios';

const authStore = {
	namespaced: true,
	state: {
		loginData: {
			isLogin: null,
			user_id: null,
			user_name: null,
		},
		adminPage: {
			chartDataSet: [],
		},
		singUpKey: {
			key: '',
		},
	},
	getters: {
		isLogin: state => {
			console.log('🎀 isLogin ===> ' + state.loginData.isLogin);
			return state.loginData.isLogin;
		},
		getSignUpKey: state => {
			return state.singUpKey.key;
		},
		getUserId: state => {
			return state.loginData.user_id;
		},
		getUserName: state => {
			return state.loginData.user_name;
		},
		getUser: state => {
			return state.loginData;
		},
	},
	mutations: {
		setUserInfo: (state, payload) => {
			/*  기본 셋팅 키
				user_id
				user_name
				access_token
				access_token_exp
				refresh_token
				refresh_token_exp
			*/
			var keys = Object.keys(payload);
			keys.forEach(key => {
				state.loginData[key] = payload[key];
			});
			state.loginData.isLogin = true;
		},
		setSignUpKey: (state, key) => {
			state.singUpKey.key = key;
		},
		clearUserInfo: state => {
			axios.defaults.headers.common['access_token'] = '';
			var keys = Object.keys(state.loginData);
			keys.forEach(key => {
				state.loginData[key] = '';
			});
		},
	},
	actions: {
		async setUserInfo({ commit }, userInfo) {
			let payload = await authService.setLoginData(userInfo);
			console.log('AuthStore setUserInfo ✔️✔️✔️✔️✔️✔️✔️');
			console.log(payload);
			if (payload !== null && payload !== undefined) {
				console.log('authStore setUserInfo ✔️');
				commit('setUserInfo', payload);
				return true;
			} else {
				console.log('authStore setUserInfo ERROR !!!!!!!!!!!');
				return false;
			}
		},
		setSignUpKey: ({ commit }, key) => {
			commit('setSignUpKey', key);
		},
		clearUserInfo: ({ commit }) => {
			commit('clearUserInfo');
		},
	},
};

export default authStore;
