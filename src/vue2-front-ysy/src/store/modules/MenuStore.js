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
		filterMenuList: [],
	},

	getters: {
		getMenuList: state => state.menuList,
		getAdminMenuList: state => state.adminMenuList,
		getFilterMenuList: state => state.filterMenuList,
	},

	mutations: {
		setMenuList(state, menuList) {
			state.menuList = menuList;
		},
		setAdminMenuList(state, menuList) {
			state.adminMenuList = menuList;
		},
		setFilterMenuList(state, menuList) {
			state.filterMenuList = menuList;
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
		async findFilterMenuList({ commit }, userId) {
			try {
				let menuList = await menuService.getFilterMenuList(userId);
				if (menuList !== null && menuList !== undefined) {
					console.log('✅ userStore findFilterMenuList');
					commit('setFilterMenuList', menuList);
				} else {
					console.log('❌ userStore findFilterMenuList ❌');
				}
			} catch (error) {
				console.log('UserStore findFilterMenuList error => ' + error);
			}
		},
	},
};

export default menuStore;
