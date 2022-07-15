import axios from 'axios';

class UserService {
	// 모든 유저 목록 조회
	async fn_getUserList() {
		let url = 'ysy/v1/getUserList?size=10';
		let res = await axios.get(url);

		if (res) {
			console.log('✅ UserService fn_getUserList');
			console.log(res.data);
			return res.data;
		} else {
			console.log('❌ UserService fn_getUserList ❌');
			return null;
		}
	}

	// 유저 상세정보 조회 : 1명의 아이디로 어드레스 조회
	async fn_getUserDetail(userId) {
		let url = 'ysy/v1/getUserDetail?userId=' + userId;
		let res = await axios.get(url);

		if (res) {
			console.log('✅ UserService fn_getUserDetail');
			console.log(res.data);
			return res.data;
		} else {
			console.log('❌ UserService fn_getUserDetail ❌');
			return null;
		}
	}

	async getFilterUserList(userId) {
		let url = 'ysy/v1/manager/getFilterUserList?userId=' + userId;
		let res = await axios.get(url);

		if (res.statusText === 'OK' && res.data.objList !== null) {
			console.log('✅ UserService getFilterUserList');
			console.log(res);
			return res.data.objList;
		} else {
			console.log('❌ UserService getFilterUserList ❌');
			return null;
		}
	}

	async getUserMenuList(userId) {
		let url = 'ysy/v1/getUserMenuList?userId=' + userId;
		let res = await axios.get(url);

		if (res.statusText === 'OK' && res.data.dataList !== null) {
			console.log('✅ UserService getUserMenuList');
			console.log(res.data.dataList);
			return res.data.dataList;
		} else {
			console.log('❌ UserService getUserMenuList ❌');
			return null;
		}
	}
}

export default new UserService();
