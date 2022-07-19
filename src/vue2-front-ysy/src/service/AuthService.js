import axios from 'axios';

class AuthService {
	async signUp(signUpInfo) {
		try {
			let res = await axios.post('/ysy/v1/auth/signUp', signUpInfo.userInfo);

			if (res.status === 200) {
				try {
					// mailSend return value = true || false
					let mailSend = await this.mailSend(signUpInfo.mailInfo);
					return mailSend;
				} catch (error) {
					return error;
				}
			} else {
				return false;
			}
		} catch (error) {
			console.log('AuthService signUp error => ' + error);
			return error;
		}
	}

	async mailSend(mailInfo) {
		try {
			let res = await axios.post('/ysy/v1/mail/mailSend', mailInfo);
			console.log('✅ AuthService mailSend');
			console.log(res);
			if (res.status === 200 && res.data) {
				return true;
			}
			return false;
		} catch (error) {
			console.log('AuthService signUp error => ' + error);
			return error;
		}
	}

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
			console.log('authService signIn error => ' + error);
			return error;
		}
	}

	async isAdmin(userId) {
		try {
			console.log('isAdminisAdminisAdminisAdminisAdminisAdminisAdminisAdminisAdminisAdmin');
			console.log(userId);
			let res = await axios.get('/ysy/v1/auth/isAdmin?username=' + userId);
			console.log(res);
			return res.data;
		} catch (error) {
			console.log('AuthService isAdmin error => ' + error);
		}
	}
}

export default new AuthService();
