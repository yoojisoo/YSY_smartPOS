import jwt_decode from 'jwt-decode';

const authStore = {
	state: {
		loginSession: {
			header: '',
			token: '',
		},
		loginData: {
			user_id: '',
			user_name: '',
			access_token: '',
			access_token_exp: '',
			refresh_token: '',
			refresh_token_exp: '',
			is_login: false, // 로그인 현재상태
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
			return state.loginData.user_id == '' ? false : true;
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
		setUserInfo: (state, userInfo) => {
			// console.log(jwt_decode(userInfo.access_token.string, { payload: true }));
			state.loginData.user_id = userInfo.user_id;
			state.loginData.user_name = userInfo.user_name;
			state.loginData.access_token = userInfo.access_token;
			state.loginData.access_token_exp = userInfo.access_token_exp;
			state.loginData.refresh_token = userInfo.refresh_token;
			state.loginData.refresh_token_exp = userInfo.refresh_token_exp;
		},
		clearUserInfo: state => {
			state.loginData.user_id = '';
		},
		setSignUpKey: (state, key) => {
			state.singUpKey.key = key;
		},
	},
	actions: {
		setUserInfo: ({ commit }, userInfo) => {
			commit('setUserInfo', userInfo);
		},
		setSignUpKey: ({ commit }, key) => {
			commit('setSignUpKey', key);
		},
	},
};

export default authStore;
