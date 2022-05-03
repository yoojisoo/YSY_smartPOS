import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify'
import axios from 'axios'


Vue.prototype.$http = axios
// Vue.prototype.axios = axios
// app.config.globalProperties.axios = axios;
axios.defaults.baseURL = 'http://localhost:8000';

Vue.config.productionTip = false

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
