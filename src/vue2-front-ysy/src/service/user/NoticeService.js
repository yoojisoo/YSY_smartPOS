import store from '@/store/index';

class NoticeService {
	async setNoticSystemList() {
		await store.dispatch('noticeStore/findNoticeSystemInfo')
		.catch(error=>{
			console.log("===============> NoticeService / setNoticSystemList error");
			console.log(error);
		});

		if(store.state.noticeStore.noticeSystemList) {
			console.log('store에 noticeSystemList 들어왔다 ~~~~~~~~');
			return true;
		} else return false;
	};
	// async setStoreNoticList() {
		
	// };
	// async setQnaList() {
		
	// };
}

export default new NoticeService();