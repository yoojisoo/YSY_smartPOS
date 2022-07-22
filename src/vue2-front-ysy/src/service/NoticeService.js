import axios from 'axios';

class NoticeService {
	/**
	 * 시스템 공지사항
	 * server controller -> JPA를 통해 DB 데이터 find
	 */
	async findSystemNotice() {
		try {
			let url = 'ysy/v1/findSystemNotice?size=5';
			let res = await axios.get(url);
			if (res.status === 200) {
				console.log('NoticeService - findSystemNotice ==> start');
				console.log(res.data.objList);
				console.log('NoticeService - findSystemNotice ==> end');
				return res.data.objList;
			} else {
				console.log('❌ NoticeService - findSystemNotice ERROR STATUS : ' + res.status);
				return null;
			}
		} catch (error) {
			console.log('NoticeService findSystemNotice error => ' + error);
			return error;
		}
	}

	/**
	 * 스토어 공지사항
	 * server controller -> JPA를 통해 DB 데이터 find
	 */
	async findStoreNotice() {
		try {
			let url = 'ysy/v1/findStoreNotice?size=5';
			let res = await axios.get(url);
			if (res.status === 200) {
				console.log('NoticeService - findStoreNotice ==> start');
				console.log(res.data.objList);
				console.log('NoticeService - findStoreNotice ==> end');
				return res.data.objList;
			} else {
				console.log('❌ NoticeService - findStoreNotice ERROR STATUS : ' + res.status);
				return null;
			}
		} catch (error) {
			console.log('MenuService findStoreNotice error => ' + error);
			return error;
		}
	}

	/**
	 * 시스템 공지사항
	 * server controller -> JPA를 통해 입력 데이터 set
	 */
	async editSystemNotice(params) {
		let url = 'ysy/v1/editSystemNotice';
	}

	// class AuthService {
	// 	async signIn(params) {
	// 		let res = (await axios.post(params.url, params.data)).headers;
	// 		if (res.access_token !== null || res.access_token !== '') {
	// 			axios.defaults.headers.common['access_token'] = res.access_token;
	// 			return res;
	// 		} else {
	// 			console.log('AuthService setLoginData ==> error !!!!!!!!!!!');
	// 			return null;
	// 		}
	// 	}
	// }
}

export default new NoticeService();
