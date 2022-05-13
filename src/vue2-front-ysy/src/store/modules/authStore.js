const authStore = {
  state: {
    loginSession: {
      header: "",
      token: "",
    },
    loginData: {
      userId: "",
      userName : "",
      phone : "",
      email : "",
    },
    adminPage: {
      chartDataSet: [],
    },
  },
  getters: {
    isLogin: (state) => {
      return state.loginData.userId == "" ? false : true;
    },
  },
  mutations: {
    setUserInfo: (state, userInfo) => {
      state.loginData.userId = userInfo.userId;
      state.loginData.userName = userInfo.userName;
      state.loginData.phone = userInfo.phone;
      state.loginData.email = userInfo.email;
    },
    clearUserInfo: (state) => {
      state.loginData.userId = "";
    },
  },
  actions: {
    setUserInfo: ({ commit }, userInfo) => {
      commit("setUserInfo",   userInfo);
    },
  },
};

export default authStore;
