import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import vuetify from './plugins/vuetify';
import axios from 'axios';

import "vue-easytable/libs/theme-default/index.css";//easy table 
import VueEasytable from "vue-easytable";



export const eventBus = new Vue();
Vue.prototype.$axios = axios;


// Vue.prototype.axios = axios
// Vue.config.globalProperties.axios = axios;
// axios.defaults.baseURL = 'http://localhost:8000';

Vue.config.productionTip = false;
// Vue.use(VueEasytable);
new Vue({
	router,
	store,
	vuetify,
	VueEasytable,
	render: h => h(App),
}).$mount('#app');


/**
 * ** oauth login path 설정
 * sns에서 로그인시 code callback받고 redirect할 페이지 지정.
 */
 const re11 = document.referrer;
console.log("re11 ",re11);

const reqUrl = window.location.toString();
const urlParams = new URLSearchParams(window.location.search);
console.log("window.location.search", window.location.search)
console.log("urlParams", urlParams)
urlParams.forEach((v, k) => {
    console.log(`key: ${k} value: ${v}`)
})


const code      = urlParams.get("code");
const oauthPath = urlParams.get("oauth_path");

console.log("reqUrl ", reqUrl);
console.log('code =  ' , code);
console.log('oauthPath =  ' , oauthPath);

if (code) {
    
	if(oauthPath === "kakao"){
		router.push({ path: '/kakaoLogin', query: { "code": code } })
	}
    else if(oauthPath === "naver"){
		router.push({ path: '/naverLogin', query: { "code": code } })
	}
	else{
		router.push({ path: '/', query: { "oauthMsg": "oauth login fail!" } })
	}
}
