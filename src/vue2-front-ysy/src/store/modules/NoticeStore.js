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
			try {
				let systemNoticeList = await noticeService.findSystemNotice();
				if (systemNoticeList !== null && systemNoticeList !== undefined) {
					if (systemNoticeList.length > 0) {
						console.log('noticeStore - findSystemNotice ✔️');
						commit('setSystemNoticeList', systemNoticeList);
					} else {
						console.log('System Notice List Length 0');
					}
				} else {
					console.log('noticeStore - findSystemNotice ERROR !!!!!!!!!!!');
				}
			} catch (error) {
				console.log('NoticeStore findSystemNotice error => ' + error);
			}
		},

		// 스토어 공지사항 데이터 가져오기
		async findStoreNotice({ commit }) {
			try {
				let storeNoticeList = await noticeService.findStoreNotice();
				if (storeNoticeList !== null && storeNoticeList !== undefined) {
					if (storeNoticeList.length > 0) {
						console.log('noticeStore - findStoreNotice ✔️');
						commit('setStoreNoticeList', storeNoticeList);
					} else {
						console.log('Store Notice List Length 0');
					}
				} else {
					console.log('noticeStore - findStoreNotice ERROR !!!!!!!!!!!');
				}
			} catch (error) {
				console.log('NoticeStore findStoreNotice error => ' + error);
			}
		},
	},
};

export default noticeStore;
