<template></template>

<script>
// import axios from 'axios';
import authService from '@/service/auth/authService.js';
export default {
	created() {},
	mounted() {
		this.naverLogin();
		console.log('this.code naverLogin ==========================> ' + this.$route.query.code);
	},
	data() {
		return {
			code: '',
		};
	},
	methods: {
		async naverLogin() {
			console.log('this.code naver ==========================> ' + this.$route.query.code);
			this.code = this.$route.query.code;
			let params = {
				code: this.code,
				path: 'naver',
			};
			await this.$axios
				.post('/ysy/v1/oauth/naver/setCode', params)
				.then(res => {
					if (res.data !== 'ok') {
						this.$router.replace('/signIn');
						return;
					}
					// this.$axios.defaults.headers.common['access_token'] = res.headers.access_token;
					let flag = authService.setLoginData(res.headers);
					console.log(res.data);
					if (flag) {
						this.$router.replace('/');
					}
				})
				.catch(error => {
					this.$router.replace('/403');
				});
		},
	},
};
</script>
