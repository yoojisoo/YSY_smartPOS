<template></template>

<script>
export default {
	mounted() {
		this.kakaoLogin();
	},
	data() {
		return {
			params: {
				url: '/ysy/v1/oauth/kakao/setCode',
				data: {
					code: '',
					path: 'kakao',
				},
			},
		};
	},
	methods: {
		async kakaoLogin() {
			try {

				this.params.data.code = this.$route.query.code;
				let res = await this.$store.dispatch('authStore/signIn', this.params);
				console.log("kakao login =>");
				console.log(res);
				if (res.status != undefined && res.status == 200)
				{
					this.$router.replace('/');
				}
				else {
					console.log("res ===========>"+res);
					alert(res);
					this.$router.replace('/signIn');
				}
			} catch (error) {
				
			}
			
		},
	},
};
</script>
