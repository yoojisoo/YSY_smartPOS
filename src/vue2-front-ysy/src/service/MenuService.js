/**
 * @author 신민경
 * @create date 2022-06-21 14:27:19
 * @desc 메뉴 가져오는 service
 */

import axios from 'axios';

class MenuService {
	async findMenuList(userId) {
		let url = '';
		if (userId == '' || userId == null || userId == undefined) {
			url = '/ysy/v1/findDefaultMenuList'; // 로그인된 상태가 아니기 때문에 권한 처리 x
		} else url = '/ysy/v1/user/findMenuList?userId=' + userId; // 로그인된 상태기 때문에 권한 처리 o

		try {
			let res = await axios.get(url);
			console.log('menuService findMenuList');
			console.log(res);

			if (res.statusText === 'OK' && res.data.objList !== null) {
				console.log('🟢 MenuService findMenuList');
				console.log(res.data.objList);
				console.log('🔴 MenuService findMenuList');
				return res.data.objList;
			} else {
				console.log('❌ MenuService findMenuList ❌');
				return res;
			}
		} catch (error) {
			console.log('MenuService findMenuList error => ' + error);
			return error;
		}
	}
}

export default new MenuService();
