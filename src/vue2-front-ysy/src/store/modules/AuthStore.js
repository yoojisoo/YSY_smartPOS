import authService from '@/service/AuthService.js';
import axios from 'axios';
import jwt_decode from 'jwt-decode';

import { YsyUtil } from '@/assets/util/importFile.js';

const authStore = {
	namespaced: true,
	state: {
		loginData: {
			isLogin: null,
			user_id: null,
			user_name: null,
			access_token: null,
			access_token_exp: null,
			refresh_token: null,
			refresh_token_exp: null,
			isAdmin: null,
		},
		adminPage: {
			chartDataSet: [],
		},
		singUpKey: {
			key: '',
		},
		myRole: 'ADMIN',
	},
	getters: {
		isLogin: state => {
			return state.loginData.isLogin;
		},
		isAdmin: state => {
			console.log('🎀 isAdmin -> ' + state.loginData.isAdmin);
			return state.loginData.isAdmin;
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
			console.log('❤️ AuthStore -> setUserInfo');
			var keys = Object.keys(payload);
			keys.forEach(key => {
				state.loginData[key] = payload[key];
			});
			state.loginData.isLogin = true;
		},
		setIsAdmin: (state, isAdmin) => {
			console.log('❤️ AuthStore -> setIsAdmin' + isAdmin);
			state.loginData.isAdmin = isAdmin;
		},
		setSignUpKey: (state, key) => {
			state.singUpKey.key = key;
		},
		clearUserInfo: state => {
			YsyUtil.removeAccessToken(axios);
			var keys = Object.keys(state.loginData);
			keys.forEach(key => {
				state.loginData[key] = '';
			});
		},
	},
	actions: {
		async signUp({ dispatch }, signUpInfo) {
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
				console.log('YsyUtil.accessToken = ', YsyUtil.accessToken);
				console.log('res.headers[accessTokenKey]', res.headers[YsyUtil.accessToken]);
				if (res.headers && res.headers[YsyUtil.accessToken] != undefined) {
					dispatch('setUserInfo', res.headers);
					return res;
				}

				if (res.headers.errormsg != undefined) {
					//로그인 인증 에러시 처리 부분 -> id / pwd 에러시
					console.log('login error msg -> ' + res.headers.errormsg);
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

		setUserInfo({ commit, dispatch }, headers) {
			if (headers !== null && headers !== undefined) {
				const token = headers[YsyUtil.accessToken];
				var decodedHeader_access = jwt_decode(token, { payload: true });
				var decodedHeader_refresh = jwt_decode(headers.refresh_token, { payload: true });

				YsyUtil.setAccessToken(axios, token);

				let payload = {
					user_id: decodedHeader_access.username,
					user_name: decodedHeader_access.name,
					access_token: token,
					access_token_exp: decodedHeader_access.exp,
					refresh_token: headers.refresh_token,
					refresh_token_exp: decodedHeader_refresh.exp,
				};
				dispatch('isAdmin', payload.user_id);
				console.log('✅ authStore setUserInfo');
				console.log(payload);
				commit('setUserInfo', payload);
				return true;
			} else {
				console.log('❌ authStore setUserInfo ❌');
				return false;
			}
		},

		async isAdmin({ commit }, username) {
			console.log(username);
			try {
				let isAdmin = await authService.isAdmin(username);
				console.log('✅ authStore isAdmin');
				commit('setIsAdmin', isAdmin);
			} catch (error) {
				console.log('AuthStore isAdmin error -> ' + error);
			}
		},

		/**
		 * 	해당 사용자의 권한 정보 get
		 */
		// async getRole({ commit }){
		// 	let role = await authService.getRole();
		// 	console.log('✅ authStore isAdmin');
		// 	commit('setRole', role);
		// },

		setSignUpKey: ({ commit }, key) => {
			commit('setSignUpKey', key);
		},

		clearUserInfo: ({ commit }) => {
			commit('clearUserInfo');
		},
	},
};

export default authStore;
