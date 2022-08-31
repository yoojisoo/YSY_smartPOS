/**
 * create by clubbboy@naver.com
 *
 */

const adminFreeBoardStore = {
	namespaced: true,
	state: {
		boardId: -1,
		title: '',
		orgTitle: '',
		subTitle: '',
		orgSubTitle: '',
		content: '',
		orgContent: '',
		writer: '',
	},

	mutations: {
		setNoteInfo: (state, payload) => {
			console.log('❤️ boardStore -> setNoteInfo');

			state.boardId = payload.boardId;
			state.orgTitle = payload.orgTitle;
			state.orgSubTitle = payload.orgSubTitle;
			state.orgContent = payload.orgContent;
			state.writer = payload.writer;
		},

		setTitle: (state, title) => {
			state.title = title;
		},
		setOrgTitle: (state, orgTitle) => {
			state.orgTitle = orgTitle;
		},

		setSubTitle: (state, subTitle) => {
			state.subTitle = subTitle;
		},
		setOrgSubTitle: (state, orgSubTitle) => {
			state.orgSubTitle = orgSubTitle;
		},

		setContent: (state, content) => {
			state.content = content;
		},
		setOrgContent: (state, orgContent) => {
			state.orgContent = orgContent;
		},
	},
	actions: {
		setNoteInfo({ commit }, payload) {
			commit('setNoteInfo', payload);
		},

		setTitle({ commit }, title) {
			commit('setTitle', title);
		},
		setOrgTitle({ commit }, orgTitle) {
			commit('setOrgTitle', orgTitle);
		},

		setSubTitle({ commit }, subTitle) {
			commit('setSubTitle', subTitle);
		},
		setOrgSubTitle({ commit }, orgSubTitle) {
			commit('setOrgSubTitle', orgSubTitle);
		},

		setContent({ commit }, content) {
			commit('setContent', content);
		},
		setOrgContent({ commit }, orgContent) {
			commit('setOrgContent', orgContent);
		},
	},
};

export default adminFreeBoardStore;
