<template>
	<v-app>
		<v-container fluid class="grey lighten-5">
			<router-view />
		</v-container>
	</v-app>
</template>

<script>
export default {
	components: {},
	data: () => ({}),
	mounted() {
		console.log('app.vue mounted ');
		const access_token = JSON.parse(sessionStorage.getItem('loginData')).authStore.loginData
			.access_token;
		this.$axios.defaults.headers.common['access_token'] = access_token; //local store
	},
	computed: {},
	methods: {
		refreshAll() {
			// 새로고침
			console.log('새로고침');
		},
	},
	beforeCreate() {
		console.log('app.vue beforeCreate ');
		//localStorage.clear();
	},
	created() {},
};
</script>

<style scoped>
.container {
	max-width: 100vw;
	padding: 0px;
}
</style>
