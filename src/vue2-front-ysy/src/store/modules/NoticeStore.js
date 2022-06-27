import noticeService from '@/service/user/NoticeService.js';

const noticeStore = {
	namespaced: true,
	state: {
		noticeSystemList: [],
		noticeStoreList: [],
		// QnaList: [],
	},
	getters: {
		// 시스템 공지사항
		getNoticeSystemList: state => {
			return state.noticeSystemList;
		},

		// 스토어 공지사항
		getNoticeStoreList: state => {
			return state.noticeStoreList;
		},
	},
	mutations: {
		// 시스템 공지사항
		setNoticeSystemList(state, noticeSystemList) {
			state.noticeSystemList = noticeSystemList;
		},

		// 스토어 공지사항
		setNoticeStoreList(state, noticeStoreList) {
			state.noticeStoreList = noticeStoreList;
		},
	},
	actions: {
		// 시스템 공지사항
		async findNoticeSystemInfo({ commit }) {
			let noticeSystemList = await noticeService.setNoticeSystemList();
			if (noticeSystemList !== null && noticeSystemList !== undefined) {
				console.log('noticeStore findNoticeSystemInfo ✔️');
				commit('setNoticeSystemList', noticeSystemList);
			} else {
				console.log('noticeStore findNoticeSystemInfo ERROR !!!!!!!!!!!');
			}
		},

		// 스토어 공지사항
		async findNoticeStoreInfo({ commit }) {
			let noticeStoreList = await noticeService.setNoticeStoreList();
			if (noticeStoreList !== null && noticeStoreList !== undefined) {
				console.log('noticeStore findNoticeStoreInfo ✔️');
				commit('setNoticeStoreList', noticeStoreList);
			} else {
				console.log('noticeStore findNoticeStoreInfo ERROR !!!!!!!!!!!');
			}
		},
	},
};

export default noticeStore;
