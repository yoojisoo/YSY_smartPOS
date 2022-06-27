import store from '@/store/index';
import axios from 'axios';

class UserService {
	async setUserList() {
		let url = 'ysy/v1/getUserList';
		let res = await axios.get(url);
		if (res) {
			console.log('UserService setUserList ==> start');
			console.log(res.data);
			console.log('UserService setUserList ==> end');
			return res.data;
		} else {
			console.log('UserService setUserList ==> error !!!!!!!!!!!!!!!!!!!!!!!!!');
			return null;
		}
	}
}

export default new UserService();
