const authStore = {
  state: {
    loginSession: {
      header: "",
      token: "",
    },
    loginData: {
      userId: "",
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
    setUserInfo: (state, _userId) => {
      state.loginData.userId = _userId;
    },
    clearUserInfo: (state) => {
      state.loginData.userId = "";
    },
  },
  actions: {
    setUserInfo: ({ commit }, _userId) => {
      commit("setUserInfo", _userId);
    },
  },
};

export default authStore;
