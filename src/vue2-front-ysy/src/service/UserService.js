import axios from 'axios';

class UserService {
	// 모든 유저 목록 조회
	async fn_getUserList() {
		try {
			let url = 'ysy/v1/manager/getUserList?size=10';
			let res = await axios.get(url);

			if (res) {
				console.log('✅ UserService fn_getUserList');
				console.log(res.data);
				return res.data;
			} else {
				console.log('❌ UserService fn_getUserList ❌');
				return null;
			}
		} catch (error) {
			console.log('UserService fn_getUserList error => ' + error);
			return error;
		}
	}

	// 유저 상세정보 조회 : 1명의 아이디로 어드레스 조회
	async fn_getUserDetail(userId) {
		try {
			let url = 'ysy/v1/manager/getUserDetail?userId=' + userId;
			let res = await axios.get(url);

			if (res) {
				console.log('✅ UserService fn_getUserDetail');
				console.log(res.data);
				return res.data;
			} else {
				console.log('❌ UserService fn_getUserDetail ❌');
				return null;
			}
		} catch (error) {
			console.log('UserService fn_getUserDetail error => ' + error);
			return error;
		}
	}

	async getFilterUserList(userId) {
		try {
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
		} catch (error) {
			console.log('UserService getFilterUserList error => ' + error);
			return error;
		}
	}

	async getUserMenuList(userId) {
		try {
			let url = 'ysy/v1/manager/getUserMenuList?userId=' + userId;
			let res = await axios.get(url);
			console.log(res);

			if (res.statusText === 'OK' && res.data.objList !== null) {
				console.log('✅ UserService getUserMenuList');
				console.log(res.data.objList);
				return res.data.objList;
			} else {
				console.log('❌ UserService getUserMenuList ❌');
				return null;
			}
		} catch (error) {
			console.log('UserService getUserMenuList error => ' + error);
			return error;
		}
	}
}

export default new UserService();
