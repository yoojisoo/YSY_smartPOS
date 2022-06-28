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
	},
	getters: {
		getUserList: state => state.userList,
	},
	mutations: {
		setUserList(state, userList) {
			state.userList = userList;
		},
	},
	actions: {
		async findUserList({ commit }) {
			let userList = await userService.setUserList();
			if (userList !== null && userList !== undefined) {
				console.log('userStore findUserList ✔️');
				console.log(userList);
				//let list = userList.products.map(x => {
				//	if (x.regDt !== undefined && x.regDt !== '') x.regDt = new Date(x.regDt);
				//	if (x.modDt !== undefined && x.modDt !== '') x.modDt = new Date(x.modDt);
				//	return x;
				//});
				commit('setUserList', userList);
			} else {
				console.log('userStore findUserList ERROR !!!!!!!!!!!');
			}
		},
	},
};

export default userStore;
