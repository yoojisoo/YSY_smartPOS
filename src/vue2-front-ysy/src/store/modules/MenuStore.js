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
				let menuList = await menuService.findMenuList(userId);
				if (menuList !== null && menuList !== undefined) {
					if (menuList.length > 0) {
						console.log('✅ menuStore findMenuList');
						console.log(menuList);
						commit('setMenuList', menuList);
					} else {
						console.log('Menu List Length 0');
					}
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
