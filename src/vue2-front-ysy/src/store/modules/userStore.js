import axios from 'axios'

const userStore = {
    
/** 
 * vuex에 정의된 상태를 가져올 때는 computed에서 return 받는다.
 * getters는 computed에서 사용
 * actions는 dispatch로 호출
 * mutations와 actions는 methods에서 사용
 *  */

    state: {
        userList: [],
    },
    getters: {
        getUserList: state =>  state.userList ,
    },
    mutations: {
        setUserList(state, userList) { state.userList =  userList; },
    },
    actions: {
        async findUserList( {commit} ) {
            let url = 'ysy/v1/auth/getUserList';
            let res = await axios.get(url);
            if(res){
                let list = res.data.map( x => {
                    if(x.regDt != undefined && x.regDt !== "") x.regDt = new Date(x.regDt);
                    if(x.modDt != undefined && x.modDt !== "") x.modDt = new Date(x.modDt);

                    return x;
                });
                console.log("userStore setUserList ========================================start");
                console.log(list);
                console.log("userStore setUserList ========================================end");
                commit("setUserList", res.data);
            }
            else{
                console.log("userStore setUserList error XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            }
        },
    },
  };

  export default userStore;