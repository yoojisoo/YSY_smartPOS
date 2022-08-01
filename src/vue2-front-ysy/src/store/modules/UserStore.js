import axios from 'axios';
import userService from '@/service/UserService.js';
import { YsyUtil } from '@/mixin/MixinGlobal.js';

const userStore = {
	namespaced: true,
	state: {
		userList: [],
		filterUserList: [],
		userAddr: {},
		conditionItems: {},
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
		setConditionItems(state, conditionItems) {
			state.conditionItems = conditionItems;
		},
	},
	actions: {
		// 모든 유저 목록 조회 : 조건이 있다면 조건으로 아니면 모든 유저 조회 ... size 어떻게 계속 가져오는지 ?
		async fn_getUserList({ commit }) {
			try {
				let userList = await userService.fn_getUserList();
				if (userList !== null && userList !== undefined) {
					console.log('🟢 userStore fn_getUserList');
					console.log(userList.objList);
					commit('setUserList', userList.objList);
				}
			} catch (error) {
				console.log('MenuStore findMenuList error => ' + error);
			}
		},
		// 유저 상세정보 조회 : 1명의 아이디로 어드레스 조회	- yoojisoo
		async fn_getUserAddr({ commit }, userId) {
			try {
				let userAddr = await userService.fn_getUserAddr(userId);
				if (userAddr !== null && userAddr !== undefined) {
					console.log('🟢 userStore fn_getUserAddr');
					console.log(userAddr.objList);
					commit('setUserAddr', userAddr.objList);
				}
			} catch (error) {
				console.log('⛔ UserStore fn_getUserAddr error => ' + error);
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
		async fn_getConditionItems({ commit }) {
			try {
				let conditionItemList = await userService.fn_getConditionItems();
				if (conditionItemList !== null && conditionItemList !== undefined) {
					console.log('✅ userStore fn_getConditionItems');
					console.log(conditionItemList);
					commit('setConditionItems', conditionItemList);
				}
			} catch (error) {
				console.log('⛔ UserStore fn_getConditionItems error => ' + error);
			}
		},


	},
};

export default userStore;
