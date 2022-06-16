import axios from 'axios';

const noticeStore = {
	namespaced: true,
	state: {
		noticeList: [],
	},
	getters: {
		getNoticeList: state => {
			return state.noticeList;
		},
	},
	mutations: {
		setSystemNoticeList(state, noticeList) {
			state.noticeList = noticeList;
		},
		setStoreNoticeList(state, noticeList) {
			state.noticeList = noticeList;
		},
	},
	actions: {
		async findSystemNoticeInfo({ commit }) {
			console.log('===============> noticeStore / findSystemNoticeInfo');
			let url = 'ysy/v1/getSystemNotice';
			//let res = axios.get(url);
			//if (res) {
			//	commit('setSystemNoticeList', res.data);
			//} else {
			//	console.log('findSystemNoticeInfo error XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX');
			//}
			await axios.get(url).then(res => {
				commit('setSystemNoticeList', res.data);
			});
		},
		async findStoreNoticeInfo({ commit }) {
			console.log('===============> noticeStore / findStoreNoticeInfo');
			let url = 'ysy/v1/getSystemNotice';
			let res = await axios.get(url);
			if (res) {
				commit('setStoreNoticeList', res.data);
			} else {
				console.log('findStoreNoticeInfo error XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX');
			}
		},
	},
};

export default noticeStore;
