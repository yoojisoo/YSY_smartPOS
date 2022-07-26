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
			try {
				this.params.data.code = this.$route.query.code;
				let res = await this.$store.dispatch('authStore/signIn', this.params);
				console.log('naver login =>');
				console.log(res);
				if (res.status != undefined && res.status == 200) {
					this.$router.replace('/');
				} else {
					console.log('res ===========>' + res);
					alert(res);
					this.$router.replace('/signIn');
				}
			} catch (error) {}
		},
	},
};
</script>
