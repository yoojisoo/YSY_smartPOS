import jwt_decode from "jwt-decode";

const authStore = {
  state: {
    loginSession: {
      header: "",
      token: "",
    },
    loginData: {
      user_id: "",
      user_token: "",
      user_name: "",
      phone: "",
      email: "",
    },
    adminPage: {
      chartDataSet: [],
    },
  },
  getters: {
    isLogin: (state) => {
      return state.loginData.user_id == "" ? false : true;
    },
  },
  mutations: {
    setUserInfo: (state, userInfo) => {
      // console.log(jwt_decode(userInfo.access_token.string, { payload: true }));
      state.loginData.user_id = userInfo.user_id;
      state.loginData.user_token = userInfo.access_token;
      state.loginData.user_name = userInfo.user_name;
      state.loginData.phone = userInfo.phone;
      state.loginData.email = userInfo.email;
    },
    clearUserInfo: (state) => {
      state.loginData.user_id = "";
    },
  },
  actions: {
    setUserInfo: ({ commit }, userInfo) => {
      commit("setUserInfo", userInfo);
    },
  },
};

export default authStore;
