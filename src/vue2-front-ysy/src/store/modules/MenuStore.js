/**
 * @author 신민경
 * @create date 2022-06-21 14:15:14
 * @desc 메뉴 리스트 저장 공간
 */

import axios from 'axios';

const menuStore = {
	namespaced: true,
	state: {
		menuList: [],
	},
	getters: {
		getMenuList: state => state.menuList,
	},
	mutations: {
		setMenuList(state, menuList) {
			state.menuList = menuList;
		},
	},
	actions: {
		async findMenuList({ commit }) {
			let url = 'ysy/v1/menu/getMenuList';
			let res = await axios.get(url);
			if (res) {
				console.log('menuStore findMenuList ==> start');
				console.log(res.data);
				console.log('menuStore findMenuList ==> end');
				commit('setMenuList', res.data);
			} else {
				console.log('menuStore findMenuList ==> error !!!!!!!!!!!!!!!!!!!!!!!!!');
			}
		},
	},
};

export default menuStore;
