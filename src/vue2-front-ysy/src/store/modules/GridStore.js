import axios from 'axios'

const gridStore = {
    
/** 
 * vuex에 정의된 상태를 가져올 때는 computed에서 return 받는다.
 * getters는 computed에서 사용
 * actions는 dispatch로 호출
 * mutations와 actions는 methods에서 사용
 *  */

    state: {
        userInfo: [],
        gridHeaders: [
            { text: '아이디'     , value: 'username'},
            { text: '전화번호'   , value: 'userPhone'},
            { text: '날짜'       , value: 'regDt'},
        ],
    },
    getters: {
        getUserData: state => {
			return state.userInfo;
		},
    },
    mutations: {
        SET_USER(state, userInfo) {
            console.log("mutations - SET_USER ~~~~~~~~~")
            state.userInfo =  userInfo;
            console.log(userInfo); 
        },
        // SET_HEADERS(state, headers) {
        //      state.headers = headers;
        // },
    },
    actions: {
        async getUserData( {commit} ) {
            console.log('getUserData start------------>');
            // var url = 'http://localhost:8000/ysy/v1/auth/test/userList?id=s_plus7@naver.com';
            var url = 'ysy/v1/auth/test/userList?id=s_plus7@naver.com';
            await axios.get(url)
							.then( res => {
                                let list = res.data.map( x => {
                                    x.regDt = new Date(x.regDt);
                                    return x;
                                });
                                console.log("list ========================================");
                                console.log(list);
								commit("SET_USER", res.data);
							})
							.catch(function (error) {
								console.log(error);
							})
            console.log("getUserData end~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        	},
    },
  };

  export default gridStore;