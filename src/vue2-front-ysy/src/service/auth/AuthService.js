import jwt_decode from 'jwt-decode';
import axios from 'axios';

class AuthService {
	async setLoginData(userInfo) {
		let url = '/login';
		let res = (await axios.post(url, userInfo)).headers;
		if (res.access_token !== null || res.access_token !== '') {
			axios.defaults.headers.common['access_token'] = res.access_token;

			var decodedHeader_access = jwt_decode(res.access_token, { payload: true });
			var decodedHeader_refresh = jwt_decode(res.refresh_token, { payload: true });

			let payload = {
				user_id: decodedHeader_access.username,
				user_name: decodedHeader_access.name,
				access_token: res.access_token,
				access_token_exp: decodedHeader_access.exp,
				refresh_token: res.refresh_token,
				refresh_token_exp: decodedHeader_refresh.exp,
			};

			return payload;
		} else {
			console.log('AuthService setLoginData ==> error !!!!!!!!!!!');
			return null;
		}
	}
}

export default new AuthService();
