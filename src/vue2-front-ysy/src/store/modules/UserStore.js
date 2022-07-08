import axios from 'axios';
import userService from '@/service/auth/UserService.js';

const userStore = {
	/**
	 * vuex에 정의된 상태를 가져올 때는 computed에서 return 받는다.
	 * getters는 computed에서 사용
	 * actions는 dispatch로 호출
	 * mutations와 actions는 methods에서 사용
	 *  */
	namespaced: true,
	state: {
		userList: [],
		filterUserList: [],
		menuList: [],
	},
	getters: {
		getUserList: state => state.userList,
		getFilterUserList: state => state.filterUserList,
		getMenuList: state => state.menuList,
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
	},
	actions: {
		async findUserList({ commit }) {
			let userList = await userService.getUserList();
			if (userList !== null && userList !== undefined) {
				console.log('✅ userStore findUserList');
				console.log(userList);
				commit('setUserList', userList);
			} else {
				console.log('❌ userStore findUserList ❌');
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
	},
};

export default userStore;
