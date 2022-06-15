import axios from 'axios'

const noticeStore = {
    state: {
        noticeList: [],
    },
    getters: {
        getNoticeList: state =>  state.noticeList ,
    },
    mutations: {
        setNoticeList(state, noticeList) { state.noticeList =  noticeList; },
    },
    actions: {
        async findNoticeInfo( {commit} ) {
			console.log('noticeStore / findNoticeInfo');
            let url = 'ysy/v1/getSystemNotice';
            let res = await axios.get(url);
            if(res){
                console.log("findtNoticeInfo ========================================start");
                console.log(res.data);
                commit("setNoticeList", res.data);
            }
            else{
                console.log("findtNoticeInfo error XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            }
        },
       
    },
  };

  export default noticeStore;