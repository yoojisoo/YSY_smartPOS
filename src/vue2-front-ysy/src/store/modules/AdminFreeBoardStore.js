
/**
 * create by clubbboy@naver.com
 * 
 */


const adminFreeBoardStore = {
	namespaced: true,
	state: {
		boardId : -1,
		title   : "",
		orgTitle   : "",
		content : "",
		orgContent : "",
		writer  : "",
	},
	
	mutations: {
		setNoteInfo: (state, payload) => {
		
			console.log('❤️ boardStore -> setNoteInfo');
			
			state.boardId = payload.boardId;
			state.orgTitle = payload.orgTitle;
			state.orgContent = payload.orgContent;
			state.writer = payload.writer;
		},


		setTitle : (state , title) =>{
			state.title = title;
		},
		setOrgTitle : (state , orgTitle) =>{
			state.orgTitle = orgTitle;
		},

		setContent : (state , content) =>{
			state.content = content;
		},
		setOrgContent : (state , orgContent) =>{
			state.orgContent = orgContent;
		},
		
	},
	actions: {
		setNoteInfo({ commit }, payload){
			commit('setNoteInfo', payload);
		},
		

		setTitle({ commit }, title) {
			commit('setTitle', title);
		},
		setOrgCTitle({ commit }, orgTitle) {
			commit('setOrgTitle', orgTitle);
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
