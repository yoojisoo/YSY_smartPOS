import axios from 'axios';
import userService from '@/service/auth/UserService.js';

const userStore = {
	namespaced: true,
	state: {
		userList: [],
		filterUserList: [],
		menuList: [],
		userDetail: {},
	},
	getters: {
		getUserList: state => state.userList,
		getFilterUserList: state => state.filterUserList,
		getMenuList: state => state.menuList,
		getUserDetail: state => state.userDetail,
	},
	mutations: {
		setUserList(state, userList) {
			state.userList = userList;
		},
		setFilterUserList(state, userList) {
			state.filterUserList = userList;
		},
		setUserMenuList(state, menuList) {
			state.menuList = menuList;
		},
		setUserDetail(state, userDetail) {
			state.userDetail = userDetail;
		},
	},
	actions: {
		//모든 유저 목록 조회	- yoojisoo
		async fn_getUserList({ commit }) {
			let userList = await userService.fn_getUserList();
			if (userList !== null && userList !== undefined) {
				console.log('✅ userStore fn_getUserList');
				commit('setUserList', userList.objList);
			} else {
				console.log('❌ userStore fn_getUserList ❌');
			}
		},

		async findFilterUserList({ commit }, userId) {
			let filterUserList = await userService.getFilterUserList(userId);
			if (filterUserList !== null && filterUserList !== undefined) {
				console.log('✅ userStore findFilterUserList');
				commit('setFilterUserList', filterUserList);
			} else {
				console.log('❌ userStore findFilterUserList ❌');
			}
		},

		async findUserMenuList({ commit }, userId) {
			let userMenuList = await userService.getUserMenuList(userId);
			if (userMenuList !== null && userMenuList !== undefined) {
				console.log('✅ userStore findUserMenuList');
				commit('setUserMenuList', userMenuList);
			} else {
				console.log('❌ userStore findUserMenuList ❌');
			}
		},

		// 유저 상세정보 조회 : 1명의 아이디로 어드레스 조회	- yoojisoo
		async fn_getUserDetail({ commit }, userId) {
			let userDetail = await userService.fn_getUserDetail(userId);
			if (userDetail !== null && userDetail !== undefined) {
				console.log('✅ userStore fn_getUserDetail');
				console.log(userDetail);
				commit('setUserDetail', userDetail);
			} else {
				console.log('❌ userStore fn_getUserDetail ❌');
			}
		},
	},
};

export default userStore;
