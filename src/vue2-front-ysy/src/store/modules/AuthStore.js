import authService from '@/service/AuthService.js';
import axios from 'axios';
import jwt_decode from 'jwt-decode';

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
			console.log('➡️ mutations setUserInfo');
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
		async signUp(signUpInfo) {
			try {
				let res = await authService.signUp(signUpInfo);
				return res;
			} catch (error) {
				console.log('AuthStore actions signUp error => ' + error);
				return error;
			}
		},

		async signIn({ dispatch }, params) {
			try {
				let res = await authService.signIn(params);

				if (res.headers && res.headers.access_token != undefined) {
					dispatch('setUserInfo', res.headers);
					return res;
				}

				if (res.response != undefined && res.response.data.status == 401) {
					//로그인 인증 에러시 처리 부분 -> id / pwd 에러시
					console.log('login error msg -> ' + res.response.data.error);
					return 'ID 또는 패스워드가 잘못되었습니다.';
				}

				if (res.data != undefined && res.data.msg != undefined) {
					//sns 로그인 에러
					return res.data.msg;
				}
			} catch (error) {
				console.log('authStore actions signIn => ' + error);
				return error;
			}
		},

		setUserInfo({ commit }, res) {
			if (res !== null && res !== undefined) {
				var decodedHeader_access = jwt_decode(res.access_token, { payload: true });
				var decodedHeader_refresh = jwt_decode(res.refresh_token, { payload: true });

				axios.defaults.headers.common['access_token'] = res.access_token;

				let payload = {
					user_id: decodedHeader_access.username,
					user_name: decodedHeader_access.name,
					access_token: res.access_token,
					access_token_exp: decodedHeader_access.exp,
					refresh_token: res.refresh_token,
					refresh_token_exp: decodedHeader_refresh.exp,
				};

				console.log('✅ authStore setUserInfo');
				commit('setUserInfo', payload);
				return true;
			} else {
				console.log('❌ authStore setUserInfo ❌');
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
