import axios from 'axios';
import userService from '@/service/UserService.js';
import { YsyUtil } from '@/mixin/MixinGlobal.js';

const userStore = {
	namespaced: true,
	state: {
		userList: [],
		filterUserList: [],
		userAddr: {},
	},
	getters: {
		getUserList: state => state.userList,
		getFilterUserList: state => state.filterUserList,
		getUserAddr: state => state.userAddr,
	},
	mutations: {
		setUserList(state, userList) {
			state.userList = userList;
		},
		setFilterUserList(state, userList) {
			state.filterUserList = userList;
		},
		setUserAddr(state, userAddr) {
			state.userAddr = userAddr;
		},
	},
	actions: {
		//모든 유저 목록 조회 : 조건이 있다면 조건으로 아니면 모든 유저 조회 ... size 어떻게 계속 가져오는지 ?
		async fn_getUserList({ commit }, condition) {
			try {
				let userList = await userService.fn_getUserList(condition);
				if (YsyUtil.methods.isNullAndEmpty(userList)) {
					console.log('🟢 userStore fn_getUserList');
					commit('setUserList', userList.objList);
				}
			} catch (error) {
				console.log('⛔ MenuService findMenuList error => ' + error);
			}
		},

		async findFilterUserList({ commit }, userId) {
			try {
				let filterUserList = await userService.getFilterUserList(userId);
				if (filterUserList !== null && filterUserList !== undefined) {
					console.log('✅ userStore findFilterUserList');
					console.log(filterUserList);
					commit('setFilterUserList', filterUserList);
				} else {
					console.log('❌ userStore findFilterUserList ❌');
				}
			} catch (error) {
				console.log('UserStore findFilterUserList error => ' + error);
			}
		},

		// 유저 상세정보 조회 : 1명의 아이디로 어드레스 조회	- yoojisoo
		async fn_getUserAddr({ commit }, userId) {
			try {
				let userAddr = await userService.fn_getUserAddr(userId);
				if (YsyUtil.methods.isNullAndEmpty(userAddr)) {
					console.log('🟢 userStore fn_getUserAddr');
					console.log(userAddr);
					commit('setUserAddr', userAddr);
				}
			} catch (error) {
				console.log('⛔ UserStore fn_getUserAddr error => ' + error);
			}
		},
	},
};

export default userStore;
