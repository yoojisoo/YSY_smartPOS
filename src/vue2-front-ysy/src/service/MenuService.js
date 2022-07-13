/**
 * @author 신민경
 * @create date 2022-06-21 14:27:19
 * @desc 메뉴 가져오는 service
 */

import axios from 'axios';

class MenuService {
	async findMenuList(userId) {
		if (userId == '' || userId == null || userId == undefined) {
			var url = 'ysy/v1/menu/findDefaultMenuList';
		} else var url = 'ysy/v1/menu/findMenuList?userId=' + userId;

		let res = await axios.get(url);

		if (res.statusText === 'OK' && res.data.objList !== null) {
			console.log('🟢 MenuService findMenuList');
			console.log(res.data.objList);
			console.log('🔴 MenuService findMenuList');
			return res.data.objList;
		} else {
			console.log('❌ MenuService findMenuList ❌');
			return null;
		}
	}
}

export default new MenuService();
