/**
 * @author 신민경
 * @create date 2022-06-21 14:27:19
 * @desc 메뉴 가져오는 service
 */

import axios from 'axios';

class MenuService {
	async setMenuList() {
		let url = 'ysy/v1/menu/getMenuList';
		let res = await axios.get(url);
		if (res) {
			console.log('MenuService setMenuList ==> start');
			console.log(res.data);
			console.log('MenuService setMenuList ==> end');
			return res.data;
		} else {
			console.log('MenuService setMenuList ==> error !!!!!!!!!!!!!!!!!!!!!!!!!');
			return null;
		}
	}
}

export default new MenuService();
