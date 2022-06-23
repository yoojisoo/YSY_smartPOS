import axios from 'axios';

const noticeStore = {
	namespaced: true,
	state: {
		noticeSystemList: [],
		noticeStoreList: [],
		// QnaList: [],
	},
	getters: {
		getNoticeSystemList: state => {
			return state.noticeSystemList;
		},
	},
	mutations: {
		setNoticeSystemList(state, noticeSystemList) {
			state.noticeSystemList = noticeSystemList;
		},
		setNoticeStoreList(state, noticeStoreList) {
			state.noticeStoreList = noticeStoreList;
		},
	},
	actions: {
		async findNoticeSystemInfo({ commit }) {
			console.log('===============> noticeStore / findSystemNoticeInfo');
			let url = 'ysy/v1/getSystemNotice';
			//let res = axios.get(url);
			//if (res) {
			//	commit('setSystemNoticeList', res.data);
			//} else {
			//	console.log('findSystemNoticeInfo error XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX');
			//}
			await axios.get(url).then(res => {
				commit('setNoticeSystemList', res.data);
			})
			.catch(error=>{
				console.log("===============> noticeStore / findNoticeSystemInfo error");
				console.log(error);
			});
		},
		async findNoticeStoreInfo({ commit }) {
			console.log('===============> noticeStore / findNoticeStoreInfo');
			let url = 'ysy/v1/getSystemNotice';
			let res = await axios.get(url);
			if (res) {
				commit('setNoticeStoreList', res.data);
			} else {
				console.log('findNoticeStoreInfo error XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX');
			}
		},
	},
};

export default noticeStore;
