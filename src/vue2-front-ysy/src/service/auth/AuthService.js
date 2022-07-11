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
			console.log("authService signIn => " + error);
			return error;
		}
		

		//.then(res=>{

		//	console.log("res=");
		//	console.log(res.data);//res.data.status == 상태값
		//	console.log(res.headers);
		//})
		//.then(error=>{
		//	console.log("error=");
		//	console.log(error);
		//});
		// if (res.access_token !== null || res.access_token !== '') {
		// 	axios.defaults.headers.common['access_token'] = res.access_token;
		// 	return res;
		// } else {
		// 	console.log('AuthService setLoginData ==> error !!!!!!!!!!!');
		// 	return null;
		// }
		// let res = (await axios.post(params.url, params.data)).headers;
		// if (res.access_token !== null || res.access_token !== '') {
		// 	axios.defaults.headers.common['access_token'] = res.access_token;
		// 	return res;
		// } else {
		// 	console.log('AuthService setLoginData ==> error !!!!!!!!!!!');
		// 	return null;
		// }
	}
}

export default new AuthService();
