import store from '@/store/index';
import jwt_decode from 'jwt-decode';
import axios from 'axios';
import router from '@/router';

class AuthService {
	setLoginData(headers) {
		axios.defaults.headers.common['access_token'] = headers.access_token;
		var decodedHeader_access = jwt_decode(headers.access_token, { payload: true });
		var decodedHeader_refresh = jwt_decode(headers.refresh_token, { payload: true });
		if (headers.access_token === null || headers.access_token === '') {
			return false;
		}
		let payload = {
			user_id: decodedHeader_access.username,
			user_name: decodedHeader_access.name,
			access_token: headers.access_token,
			access_token_exp: decodedHeader_access.exp,
			refresh_token: headers.refresh_token,
			refresh_token_exp: decodedHeader_refresh.exp,
		};
		console.log('AuthService -> setLoginData -> payload');
		console.log(payload);
		store.dispatch('authStore/setUserInfo', payload);
		return true;
	}

	setLoginClear(path) {
		store.dispatch('authStore/clearUserInfo');
		if (path !== undefined && path !== '') {
			router.replace(path);
		} else {
			// [ '/' 오류 뜨는 이유 ]
			// 현재 '/' 경로에 있기 때문에 뜨는거라서 catch로 오류를 잡아줌 !
			router.replace('/').catch(() => {});
		}
	}
}

export default new AuthService();
