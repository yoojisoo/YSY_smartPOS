import axios from 'axios';

class AuthService {
	async signIn(params) {
		let res = (await axios.post(params.url, params.data)).headers;
		if (res.access_token !== null || res.access_token !== '') {
			axios.defaults.headers.common['access_token'] = res.access_token;
			return res;
		} else {
			console.log('AuthService setLoginData ==> error !!!!!!!!!!!');
			return null;
		}
	}
}

export default new AuthService();
