import store from '@/store/index';

class UserService {
	async setUserList() {
		 await store.dispatch('userStore/findUserList');
		 if(store.state.userStore.userList) {
			return true;
		 } else return false;
	}
}

export default new UserService();