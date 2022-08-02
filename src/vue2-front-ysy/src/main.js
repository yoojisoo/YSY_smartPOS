import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import vuetify from './plugins/vuetify';
import axios from 'axios';

import 'vue-easytable/libs/theme-default/index.css'; //easy table
import VueEasytable from 'vue-easytable';

export const eventBus = new Vue();
Vue.prototype.$axios = axios;

Vue.prototype.$accessTokenKey = 'authorization';

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
console.log('re11 ', re11);

const reqUrl = window.location.toString();
const urlParams = new URLSearchParams(window.location.search);
console.log('window.location.search', window.location.search);
console.log('urlParams', urlParams);
urlParams.forEach((v, k) => {
	console.log(`key: ${k} value: ${v}`);
});

const __code   = urlParams.get('code');
const __gubun  = urlParams.get('gubun');
const __status = urlParams.get('status');//이메일 인증시 완료 여부
const __msg    = urlParams.get('msg');

console.log('reqUrl ', reqUrl);
console.log('__code =    ', __code);
console.log('__gubun =   ', __gubun);
console.log('__status =  ', __status);
console.log('__msg =     ', __msg);

if (code) {
	if (__gubun === 'kakao') {
		router.push({ path: '/kakaoLogin', query: { code: __code } });
	} else if (__gubun === 'naver') {
		router.push({ path: '/naverLogin', query: { code: __code } });
	} else if(__gubun === 'email') {
		router.push({ path: '/signIn', query: { status: __status , msg : __msg} });
	}
}
/**
 *
 * Style Guide *****
 *
 * [ .vue ]
 * Base***.vue		: Base Component			(부품 단위)
 * Block****.vue	: Base Components의 집합	(반제품 단위)
 * The***.vue		: Single Instance Component (한 페이지당 한 번만 사용되는 컴포넌트)
 * View*****.vue	: 다양한 Components의 집합	(완제품 단위 - App.vue에서 router-view에 들어가는 화면)
 *
 * [ script - 필요한것만 사용 ]
 * name > props > components > mixins > data
 * (before)created > (before)mounted
 * computed > watch > methods
 * (before)updated > (before)destroyed
 *
 * [ function ]
 * fn_ + [ get, set, save, modify, delete ] + [ 가져올 데이터 정보 ]
 * ex) fn_getDataList , fn_modifyData , fn_setDataList ...
 *
 */
