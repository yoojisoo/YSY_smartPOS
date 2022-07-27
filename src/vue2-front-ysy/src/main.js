import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import vuetify from './plugins/vuetify';
import axios from 'axios';

//easy table 
import "vue-easytable/libs/theme-default/index.css";
import VueEasytable from "vue-easytable";

export const eventBus = new Vue();
Vue.prototype.$axios = axios;

Vue.use(VueEasytable);
// Vue.prototype.axios = axios
// Vue.config.globalProperties.axios = axios;
// axios.defaults.baseURL = 'http://localhost:8000';

Vue.config.productionTip = false;


var urlParams1 = location.search.split(/[?&]/).slice(1).map(function(paramPair) {

	return paramPair.split(/=(.+)?/).slice(0, 2);

}).reduce(function(obj, pairArray) {

	obj[pairArray[0]] = pairArray[1];

	return obj;

}, {});
console.log("urlParams1.code) ===================================================="+urlParams1.code);
console.log("urlParams1.oauth_path) ===================================================="+urlParams1.oauth_path);





new Vue({
	router,
	store,
	vuetify,
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
    
	// if(reqUrl.indexOf("kakao") > -1){
	// 	router.push({ path: '/kakaoLogin', query: { "code": code } })
	// }
    // else if(reqUrl.indexOf("naver") > -1){
	// 	router.push({ path: '/naverLogin', query: { "code": code } })
	// }
	if(oauthPath === "kakao"){
		router.push({ path: '/kakaoLogin', query: { "code": code } })
	}
    else if(oauthPath === "naver"){
		router.push({ path: '/naverLogin', query: { "code": code } })
	}
	// else{
	// 	router.push({ path: '/kakaoLogin', query: { "code": code } })
	// }
}
