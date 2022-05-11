import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

import authStore from "./modules/authStore.js";

const store = new Vuex.Store({
  modules: {
    authStore,
  },
  plugins: [
    createPersistedState({
      paths: ["authStore.loginData"],
      storage: sessionStorage,
      key: "loginData",
    }),
  ],
});

export default store;
