/**
 * @author 신민경
 * @create date 2022-06-21 14:27:19
 * @desc 메뉴 가져오는 service
 */

import store from '@/store/index';

class MenuService {
	async setMenuList() {
		console.log('MenuService - setMenuList 진입');
		await store.dispatch('menuStore/findMenuList');
		if (store.state.menuStore.menuList) {
			return true;
		} else return false;
	}
}

export default new MenuService();
