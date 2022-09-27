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
		mode: 'user',
		userMenuList: [],
		adminMenuList: [],
		filterMenuList: [],
		errorMsg: '',
	},

	getters: {
		getUserMenuList: state => state.userMenuList,
		getAdminMenuList: state => state.adminMenuList,
		getFilterMenuList: state => state.filterMenuList,
	},

	mutations: {
		setUserMenuList(state, menuList) {
			state.userMenuList = menuList;
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
		modeChange(state) {
			if (state.mode == 'user') state.mode = 'admin';
			else state.mode = 'user';
		}
	},

	actions: {
		async findMenuList({ commit }, params) {
			try {
				let res = await CommonService.fn_getDataList(params.url, null);
				let adminMenuList = [];
				let userMenuList = [];

				if (res.data && res.data.status === 'OK' && res.data.objList !== null) {
					YsyUtil.log('res.data.objList', res.data.objList);

					// 220927 mnew2m 찾아온 res.data.objList를 is_admin을 보고 관리자메뉴와 사용자메뉴를 구분시켜둠.
					res.data.objList.forEach(menu => {
						if (menu.is_admin == "Y") adminMenuList.push(menu);
						else if (menu.is_admin == "N") userMenuList.push(menu);
					});

					commit('setUserMenuList', userMenuList);
					commit('setAdminMenuList', adminMenuList);
				} else {
					commit('setErrorMsg', res.data.msg);
					YsyUtil.log('❌ menuStore findMenuList ❌');
				}
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
		modeChange({ commit }) {
			commit('modeChange');
		}
	},
};

export default menuStore;
