import jwt_decode from 'jwt-decode';
import Axios from 'axios';

const authStore = {
	state: {
		loginData: {},
		adminPage: {
			chartDataSet: [],
		},
		singUpKey: {
			key: '',
		},
	},
	getters: {
		isLogin: state => {
			console.log("state.loginData.user_id=>"+state.loginData.user_id)
			return state.loginData.user_id == undefined || state.loginData.user_id == "" ? false : true;
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
			/*  기본 셋팅 키
				user_id
				user_name
				access_token
				access_token_exp
				refresh_token
				refresh_token_exp
			*/
			var keys = Object.keys(userInfo);
			keys.forEach(key=>{
				state.loginData[key] = userInfo[key];
			});
		},
		clearUserInfo: state => {
			console.log("authStore mutations clearUserInfo");
			Axios.defaults.headers.common['access_token'] ="";
			state.loginData = {};
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
		clearUserInfo: ({ commit }) => {
			console.log("auth store clearUserInfo");
			commit('clearUserInfo');
		},
	},
};

export default authStore;
