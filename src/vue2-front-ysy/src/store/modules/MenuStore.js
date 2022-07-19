/**
 * @author 신민경
 * @create date 2022-06-21 14:15:14
 * @desc 메뉴 리스트 저장 공간
 */

import menuService from '@/service/MenuService.js';

const menuStore = {
	namespaced: true,

	state: {
		menuList: [],
		adminMenuList: [],
	},

	getters: {
		getMenuList: state => state.menuList,
		getAdminMenuList: state => state.adminMenuList,
	},

	mutations: {
		setMenuList(state, menuList) {
			state.menuList = menuList;
		},
		setAdminMenuList(state, menuList) {
			state.adminMenuList = menuList;
		},
	},

	actions: {
		async findMenuList({ commit }, userId) {
			try {
				let res = await menuService.findMenuList(userId);
				if (res) {
					console.log('✅ menuStore findMenuList');
					console.log(res);
					commit('setMenuList', res);
				} else {
					console.log('❌ menuStore findMenuList ❌');
				}
			} catch (error) {
				console.log('MenuStore findMenuList error => ' + error);
			}
		},
	},
};

export default menuStore;
