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
			code: '',
		};
	},
	methods: {
		async kakaoLogin() {
			console.log("this.code ==========================> "+this.$route.query.code);
			this.code = this.$route.query.code;
			let params = {
				code : this.code,
				path : "kakao",
			}
			await this.$axios.post('/ysy/v1/auth/kakao/setCode' , params)
				.then(res=>{
					if(res.data !== "ok"){
						alert(res.data);
						this.$router.replace('/signIn');
						return;
					}
					// this.$axios.defaults.headers.common['access_token'] = res.headers.access_token;
					let flag = authService.setLoginData(res.headers);
					console.log(res.data);
					if(flag) {
						this.$router.replace('/');
					}
				})
				.catch(error=>{
					this.$router.replace('/403');
				});
		},
	},
};
</script>
