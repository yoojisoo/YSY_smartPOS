<template>
	<v-app>
		<div v-if="!isSignView">
			<keep-alive>
				<TheSystemBar />
			</keep-alive>
			<keep-alive>
				<TheHeader />
			</keep-alive>
		</div>
		<v-main>
			<router-view />
		</v-main>
		<keep-alive>
			<v-footer v-if="!isSignView" class="ma-0 pa-0" app fixed>
				<TheFooter />
			</v-footer>
		</keep-alive>
	</v-app>
</template>

<script>
import { TheSystemBar, TheHeader, TheFooter } from '@/assets/util/importFile.js';

export default {
	components: { TheSystemBar, TheHeader, TheFooter },
	mounted() {
		console.log('app.vue mounted ');
		if (sessionStorage.getItem('loginData')) {
			const access_token = JSON.parse(sessionStorage.getItem('loginData')).authStore.loginData
				.access_token;
			this.$axios.defaults.headers.common['access_token'] = access_token;
		}
	},
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
	computed: {
		isSignView() {
			return this.$route.name === 'signIn' || this.$route.name === 'signUp';
		},
	},
};
</script>

<style scoped>
.container {
	max-width: 100vw;
	padding: 0px;
}
</style>
