import axios from 'axios';

class UserService {
	async getUserList() {
		let url = 'ysy/v1/getUserList';
		let res = await axios.get(url);

		if (res) {
			console.log('🟢 UserService getUserList');
			console.log(res.data);
			console.log('🔴 UserService getUserList');
			return res.data;
		} else {
			console.log('❌ UserService getUserList ❌');
			return null;
		}
	}

	async getFilterUserList(userId) {
		let url = 'ysy/v1/getFilterUserList?userId=' + userId;
		let res = await axios.get(url);

		if (res.statusText === 'OK' && res.data.dataList !== null) {
			console.log('🟢 UserService getFilterUserList');
			console.log(res.data.dataList);
			console.log('🔴 UserService getFilterUserList');
			return res.data.dataList;
		} else {
			console.log('❌ UserService getFilterUserList ❌');
			return null;
		}
	}

	async getUserMenuList(userId) {
		let url = 'ysy/v1/getUserMenuList?userId=' + userId;
		let res = await axios.get(url);

		if (res.statusText === 'OK' && res.data.dataList !== null) {
			console.log('🟢 UserService getUserMenuList');
			console.log(res.data.dataList);
			console.log('🔴 UserService getUserMenuList');
			return res.data.dataList;
		} else {
			console.log('❌ UserService getUserMenuList ❌');
			return null;
		}
	}
}

export default new UserService();
