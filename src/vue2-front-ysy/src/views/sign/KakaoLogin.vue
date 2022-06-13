<template>
	
</template>

<script>
// import axios from 'axios';
import authService from "@/service/auth/AuthService.js"
export default {
	created() {
		this.kakaoLogin();
	},
	data() {
		return {
			codes: '',
			form: {
				password: '',
				email: '',
				uname: '',
			},
			show: true,
		};
	},
	methods: {
		async kakaoLogin() {
			console.log("this.code ==========================> "+this.$route.query.code);
			this.codes = this.$route.query.code;
			let params = {
				code : this.codes,
				path : "kakao",
			}
			await this.$axios.post('/ysy/v1/auth/kakao/setCode' , params)
				.then(res=>{
					this.$axios.defaults.headers.common['access_token'] = res.headers.access_token;
					authService.setLoginData(res.headers);
					console.log(res.data);
				})
				.catch(error=>{

				});
			// this.getToken();
		},
		login() {
			axios.post('http://localhost:8080/login', this.form).then(res => {
				if (res.data != null) {
					document.cookie = `accessToken=${res.data}`;
					axios.defaults.headers.common['x-access-token'] = res.data;
					this.$router.push('/');
				}
			});
		},
		getToken() {
			axios.get('http://localhost:8080/klogin?authorize_code=' + this.codes).then(res => {
				this.form.email = res.data.email;
				this.form.password = res.data.id;
				if (this.form.password == undefined) {
					alert('올바르지 못한 접근입니다.');
					this.$router.push('/');
				} else {
					this.login();
				}
			});
		},
		onSubmit(event) {
			event.preventDefault();
			// alert(JSON.stringify(this.form));
			axios.post('http://localhost:8080/join', this.form).then(res => {
				console.log(res.status);
				this.login();
			});
		},
	},
};
</script>
