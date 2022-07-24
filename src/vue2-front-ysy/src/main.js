import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import vuetify from './plugins/vuetify';
import axios from 'axios';
//import importFile from '@/assets/util/importFile.js';
import YsyUtil from './mixin/YsyUtil';

//easy table 
// import "vue-easytable/libs/theme-default/index.css";
// import VueEasytable from "vue-easytable";

export const eventBus = new Vue();
Vue.prototype.$axios = axios;

Vue.use(YsyUtil);

// Vue.use(VueEasytable);
Vue.prototype.$util = YsyUtil;
//Vue.prototype.$import = importFile;
// Vue.prototype.axios = axios
// Vue.config.globalProperties.axios = axios;
// axios.defaults.baseURL = 'http://localhost:8000';

Vue.config.productionTip = false;

new Vue({
	router,
	store,
	vuetify,
	render: h => h(App),
}).$mount('#app');
