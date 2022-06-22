/**
 * @author 신민경
 * @create date 2022-06-21 14:15:14
 * @desc 메뉴 리스트 저장 공간
 */

import menuService from '@/service/auth/MenuService.js';

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
			let menuList = await menuService.setMenuList();
			if (menuList !== null && menuList !== undefined) {
				console.log('menuStore findMenuList ✔️');
				commit('setMenuList', menuList);
			} else {
				console.log('menuStore findMenuList ERROR !!!!!!!!!!!');
			}
		},
	},
};

export default menuStore;
