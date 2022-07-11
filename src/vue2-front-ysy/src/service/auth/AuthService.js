import axios from 'axios';

class AuthService {
	async signIn(params) {
		await axios.post(params.url, params.data)
		.then(res=>{

			console.log("res=");
			console.log(res.data);//res.data.status == 상태값
			console.log(res.headers);
		})
		.then(error=>{
			console.log("error=");
			console.log(error);
		});
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
