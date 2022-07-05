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

		if (res) {
			console.log('MenuService findMenuList ==> start');
			console.log(res.data);
			console.log('MenuService findMenuList ==> end');
			return res.data;
		} else {
			console.log('MenuService findMenuList ==> error !!!!!!!!!!!!!!!!!!!!!!!!!');
			return null;
		}
	}
}

export default new MenuService();
