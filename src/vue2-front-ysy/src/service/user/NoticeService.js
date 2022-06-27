import axios from 'axios';

class NoticeService {
	// 시스템 공지사항
	async setNoticeSystemList() {
		let url = 'ysy/v1/getSystemNotice';
		let res = await axios.get(url);
		if (res) {
			console.log('NoticeService setNoticeSystemList ==> start');
			console.log(res.data);
			console.log('NoticeService setNoticeSystemList ==> end');
			return res.data;
		} else {
			console.log('NoticeService setNoticeSystemList ==> error !!!!!!!!!!!!!!!!!!!!!!!!!');
			return null;
		}
	}

	// 스토어 공지사항
	async setNoticeStoreList() {
		let url = 'ysy/v1/getSystemNotice'; // url 스토어로 변경해야함 !
		let res = await axios.get(url);
		if (res) {
			console.log('NoticeService setNoticeStoreList ==> start');
			console.log(res.data);
			console.log('NoticeService setNoticeStoreList ==> end');
			return res.data;
		} else {
			console.log('NoticeService setNoticeStoreList ==> error !!!!!!!!!!!!!!!!!!!!!!!!!');
			return null;
		}
	}
}

export default new NoticeService();
