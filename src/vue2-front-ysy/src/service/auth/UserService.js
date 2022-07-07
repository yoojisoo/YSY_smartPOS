import store from '@/store/index';
import axios from 'axios';

class UserService {
	async getUserList() {
		let url = 'ysy/v1/getUserList';
		let res = await axios.get(url);
		if (res) {
			console.log('UserService getUserList ==> start');
			console.log(res.data);
			console.log('UserService getUserList ==> end');
			return res.data;
		} else {
			console.log('UserService getUserList ==> error !!!!!!!!!!!!!!!!!!!!!!!!!');
			return null;
		}
	}

	async getFilterUserList(userId) {
		let url = 'ysy/v1/getFilterUserList?userId=' + userId;
		let res = await axios.get(url);
		if (res) {
			console.log('UserService getFilterUserList ==> start');
			console.log(res.data);
			console.log('UserService getFilterUserList ==> end');
			return res.data;
		} else {
			console.log('UserService getFilterUserList ==> error !!!!!!!!!!!!!!!!!!!!!!!!!');
			return null;
		}
	}

	async getUserMenuList(userId) {
		let url = 'ysy/v1/getUserMenuList?userId=' + userId;
		let res = await axios.get(url);
		if (res) {
			console.log('UserService getUserMenuList ==> start');
			console.log(res.data);
			console.log('UserService getUserMenuList ==> end');
			return res.data;
		} else {
			console.log('UserService getUserMenuList ==> error !!!!!!!!!!!!!!!!!!!!!!!!!');
			return null;
		}
	}
}

export default new UserService();
