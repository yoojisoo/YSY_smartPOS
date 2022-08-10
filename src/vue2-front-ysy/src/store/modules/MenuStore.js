/**
 * @author 신민경
 * @create date 2022-06-21 14:15:14
 * @desc 메뉴 리스트 저장 공간
 */

import menuService from '@/service/MenuService.js';
import CommonService from '@/service/CommonService.js';
import YsyUtil from '@/mixin/YsyUtil';

const menuStore = {
	namespaced: true,

	state: {
		menuList: [],
		adminMenuList: [],
		filterMenuList: [],
		errorMsg: '',
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
		setErrorMsg(state, errorMsg) {
			state.errorMsg = errorMsg;
		},
	},

	actions: {
		async findMenuList({ commit }, params) {
			try {
				let res = await CommonService.fn_getDataList(params.url, null);

				if (res.data && res.data.status === 'OK' && res.data.objList !== null) {
					YsyUtil.log('res.data.objList', res.data.objList);
					commit('setMenuList', res.data.objList);
				} else {
					commit('setErrorMsg', res.data.msg);
					YsyUtil.log('❌ menuStore findMenuList ❌');
				}

				//if (menuList !== null && menuList !== undefined) {
				//	if (menuList.length > 0) {
				//		console.log('✅ menuStore findMenuList');
				//		console.log(menuList);
				//		commit('setMenuList', menuList);
				//	} else {
				//		console.log('Menu List Length 0');
				//	}
				//} else {
				//	console.log('❌ menuStore findMenuList ❌');
				//}
			} catch (error) {
				YsyUtil.log('❌❌❌ MenuStore findMenuList error => ', error);
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
