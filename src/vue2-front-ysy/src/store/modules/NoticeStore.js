import noticeService from '@/service/NoticeService.js';

const noticeStore = {
	namespaced: true,
	state: {
		systemNoticeList: [],
		storeNoticeList: [],
		// QnaList: [],
	},
	getters: {
		// 시스템 공지사항 getter
		getSystemNoticeList: state => {
			return state.systemNoticeList;
		},

		// 스토어 공지사항 getter
		getStoreNoticeList: state => {
			return state.storeNoticeList;
		},
	},
	mutations: {
		// 시스템 공지사항 DB값 store에 set
		setSystemNoticeList(state, systemNoticeList) {
			state.systemNoticeList = systemNoticeList;
		},

		// 스토어 공지사항 DB값 store에 set
		setStoreNoticeList(state, storeNoticeList) {
			state.storeNoticeList = storeNoticeList;
		},
	},
	actions: {
		// 시스템 공지사항 데이터 가져오기
		async findSystemNotice({ commit }) {
			let systemNoticeList = await noticeService.findSystemNotice();
			if (systemNoticeList !== null && systemNoticeList !== undefined) {
				console.log('noticeStore - findSystemNotice ✔️');
				commit('setSystemNoticeList', systemNoticeList);
			} else {
				console.log('noticeStore - findSystemNotice ERROR !!!!!!!!!!!');
			}
		},

		// 스토어 공지사항 데이터 가져오기
		async findStoreNotice({ commit }) {
			let storeNoticeList = await noticeService.findStoreNotice();
			if (storeNoticeList !== null && storeNoticeList !== undefined) {
				console.log('noticeStore - findStoreNotice ✔️');
				commit('setStoreNoticeList', storeNoticeList);
			} else {
				console.log('noticeStore - findStoreNotice ERROR !!!!!!!!!!!');
			}
		},
	},
};

export default noticeStore;
