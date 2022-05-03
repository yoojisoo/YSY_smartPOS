import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    title: null // state 값
  },
  getters: {
    getTitle: (state) => {
      return state.title // state.title 값을 읽어올 getTitle
  }
  },
  mutations: {
    setTitle: (state, payload) => {
      state.title = payload // state.title 값을 변경해줄 setTitle
  }
  },
  actions: {
    setTitle: (context, payload) => { // setTitle 액션
      context.commit('setTitle', payload)
  }
  },
  modules: {
  }
})
