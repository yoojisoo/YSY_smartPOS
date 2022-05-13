const authStore = {
  state: {
    loginSession: {
      header: "",
      token: "",
    },
    loginData: {
      userId: "",
      userToken: "",
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
    setUserInfo: (state, _userId, _userToken) => {
      state.loginData.userId = _userId;
      state.loginData.userToken = _userToken;
    },
    clearUserInfo: (state) => {
      state.loginData.userId = "";
    },
  },
  actions: {
    setUserInfo: ({ commit }, _userId, _userToken) => {
      commit("setUserInfo", _userId, _userToken);
    },
  },
};

export default authStore;
