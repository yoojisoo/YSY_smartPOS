import store from '@/store/index';
// import jwt_decode from 'jwt-decode';
import axios from 'axios';
// import router from '@/router';

class UserService {
	async setUserList() {
		 await store.dispatch('userStore/findUserList');
		 if(store.state.userStore.userList) {
			return true;
		 } else return false;
	}
}

export default new UserService();