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

	/**
	 * QnA
	 * server controller -> JPA를 통해 DB 데이터 find
	 */
	async findQnA() {
		try {
			let url = 'ysy/v1/findQnA?size=5';
			let res = await axios.get(url);
			if (res.status === 200) {
				console.log('NoticeService - findQnA ==> start');
				console.log(res.data.objList);
				console.log('NoticeService - findQnA ==> end');
				return res.data.objList;
			} else {
				console.log('❌ NoticeService - findQnA ERROR STATUS : ' + res.status);
				return null;
			}
		} catch (error) {
			console.log('MenuService findQnA error => ' + error);
			return error;
		}
	}
}

export default new NoticeService();
