const authStore = {
  state: {
    loginSession: {
      header: "",
      token: "",
    },
    title: null,
    loginData: {
      userId: "",
    },
    adminPage: {
      chartDataSet: [],
    },
  },
  getters: {
    getTitle: (state) => {
      return state.title; // state.title 값을 읽어올 getTitle
    },
    isLogin: (state) => {
      return state.loginData.userId == "" ? false : true;
    },
  },
  mutations: {
    setTitle: (state, payload) => {
      state.title = payload; // state.title 값을 변경해줄 setTitle
    },
    setUserInfo: (state, _userId) => {
      state.loginData.userId = _userId;
    },
    clearUserInfo: (state) => {
      state.loginData.userId = "";
    },
  },
  actions: {
    setTitle: (context, payload) => {
      // setTitle 액션
      context.commit("setTitle", payload);
    },
    setUserInfo: ({ commit }, _userId) => {
      commit("setUserInfo", _userId);
    },
  },
};

export default authStore;
