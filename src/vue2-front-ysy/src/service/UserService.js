import axios from 'axios';

class UserService {
	// 모든 유저 목록 조회 : 조건이 있다면 조건으로 아니면 모든 유저 조회
	
	
	async fn_getUserList() {
		try {
			let res = await axios.post('/ysy/v1/manager/getUserListCondition');
			console.log('🟢 UserService fn_getUserList');
			return res.data;
		} catch (error) {
			console.log('⛔ UserService fn_getUserList error =>' + error);
		}

		//🌵🌵🌵🌵🌵🌵🌵🌵🌵🌵🌵🌵이거 왜 안되는지 모르겠음
		// await axios
		// 	.post('/ysy/v1/getUserListCondition')
		// 	.then(res => {
		// 		if(res.data !== null || res.data !== undefined) {
		// 			console.log('🟢 UserService fn_getUserList');
		// 			console.log(res.data);
		// 		}
		// 		return res.data.objList;
		// 	})
		// 	.catch(error => {
		// 		console.log('⛔ UserService fn_getUserList error => ');
		// 		return error;
		// 	});
	}

	// 유저 1명의 아이디로 어드레스 조회
	async fn_getUserAddr(userId) {
		try {
			let res = await axios.get('ysy/v1/manager/getUserAddr?userId=' + userId);
				console.log('🟢 UserService fn_getUserAddr');
				console.log(res.data);
				return res.data;
		} catch (error) {
			console.log('⛔ UserService fn_getUserAddr error =>' + error);
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

	async fn_getConditionItems() {
		try {
			let url = 'ysy/v1/manager/getConditionItems';
			let res = await axios.get(url);
			console.log('getConditionItems --------------------------------------->>>');
			console.log(res.data);
			// if (res.statusText === 'OK' && res.data.objList !== null) {
			// 	console.log('✅ UserService getFilterUserList');
			// 	console.log(res);
			// 	return res.data.objList;
			// } else {
			// 	console.log('❌ UserService getFilterUserList ❌');
			// 	return null;
			// }
		} catch (error) {
			console.log('UserService getFilterUserList error => ' + error);
			return error;
		}
	}
}

export default new UserService();
