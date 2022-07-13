import axios from 'axios';

class AuthService {
	async signIn(params) {
		try {
			let res = await axios.post(params.url, params.data);

			if (res.status === 200 && res.headers !== null) {
				console.log('🟢 AuthService signIn');
				console.log(res.headers);
				console.log('🔴 AuthService signIn');
				return res;
			} else {
				console.log('❌ AuthService signIn ❌');
				return res;
			}
		} catch (error) {
			console.log('authService signIn => ' + error);
			return error;
		}
	}
}

export default new AuthService();
