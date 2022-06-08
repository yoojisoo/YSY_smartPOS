import axios from 'axios'

const gridStore = {
    
/** 
 * vuex에 정의된 상태를 가져올 때는 computed에서 return 받는다.
 * getters는 computed에서 사용
 * actions는 dispatch로 호출
 * mutations와 actions는 methods에서 사용
 *  */

    state: {
        userList: [],
        gridHeaders: [
            { text: '아이디'     , value: 'username'},
            { text: '전화번호'   , value: 'userPhone'},
            { text: '날짜'       , value: 'regDt'},
        ],
    },
    getters: {
        getUserList: state =>  state.userList ,
    },
    mutations: {
        setUserList(state, userList) { state.userList =  userList; },
    },
    actions: {
        async setUserList( {commit} ) {
            let url = 'ysy/v1/auth/test/getUserList?id=s_plus7@naver.com';
            let res = await axios.get(url);
            if(res){
                let list = res.data.map( x => {
                    if(x.regDt != undefined && x.regDt !== "") x.regDt = new Date(x.regDt);
                    if(x.modDt != undefined && x.modDt !== "") x.modDt = new Date(x.modDt);

                    return x;
                });
                console.log("gridStore setUserList ========================================start");
                console.log(list);
                console.log("gridStore setUserList ========================================end");
                commit("setUserList", res.data);
            }
            else{
                console.log("gridStore setUserList error XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            }
        },
    },
  };

  export default gridStore;