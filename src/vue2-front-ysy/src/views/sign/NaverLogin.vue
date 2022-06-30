<template></template>

<script>
export default {
	mounted() {
		this.naverLogin();
	},
	data() {
		return {
			params: {
				url: '/ysy/v1/oauth/naver/setCode',
				data: {
					code: '',
					path: 'naver',
				},
			},
		};
	},
	methods: {
		async naverLogin() {
			this.params.data.code = this.$route.query.code;
			let flag = await this.$store.dispatch('authStore/signIn', this.params);
			if (flag) this.$router.replace('/');
			else alert('아이디나 비밀번호가 틀렸습니다.');
		},
	},
};
</script>
