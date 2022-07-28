import communityService from '@/service/CommunityService.js';

const communityStore = {
	namespaced: true,
	state: {
		systemNoticeList: [],
		storeNoticeList: [],
		qnaList: [],
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

		// QnA getter
		getQnAList: state => {
			return state.qnaList;
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

		// QnA DB값 store에 set
		setQnAList(state, qnaList) {
			state.qnaList = qnaList;
		},
	},
	actions: {
		// 시스템 공지사항 데이터 가져오기
		async findSystemNotice({ commit }) {
			try {
				let systemNoticeList = await communityService.findSystemNotice();
				if (systemNoticeList !== null && systemNoticeList !== undefined) {
					if (systemNoticeList.length > 0) {
						console.log('communityStore - findSystemNotice ✔️');
						commit('setSystemNoticeList', systemNoticeList);
					} else {
						console.log('System Notice List Length 0');
					}
				} else {
					console.log('communityStore - findSystemNotice ERROR !!!!!!!!!!!');
				}
			} catch (error) {
				console.log('communityStore findSystemNotice error => ' + error);
			}
		},

		// 스토어 공지사항 데이터 가져오기
		async findStoreNotice({ commit }) {
			try {
				let storeNoticeList = await communityService.findStoreNotice();
				if (storeNoticeList !== null && storeNoticeList !== undefined) {
					if (storeNoticeList.length > 0) {
						console.log('communityStore - findStoreNotice ✔️');
						commit('setStoreNoticeList', storeNoticeList);
					} else {
						console.log('Store Notice List Length 0');
					}
				} else {
					console.log('communityStore - findStoreNotice ERROR !!!!!!!!!!!');
				}
			} catch (error) {
				console.log('communityStore findStoreNotice error => ' + error);
			}
		},

		// QnA 데이터 가져오기
		async findQnA({ commit }) {
			try {
				let qnaList = await communityService.findQnA();
				if (qnaList !== null && qnaList !== undefined) {
					if (qnaList.length > 0) {
						console.log('communityStore - findQnA ✔️');
						commit('setQnAList', qnaList);
					} else {
						console.log('Store QnA List Length 0');
					}
				} else {
					console.log('communityStore - findQnA ERROR !!!!!!!!!!!');
				}
			} catch (error) {
				console.log('communityStore findQnA error => ' + error);
			}
		},
	},
};

export default communityStore;
