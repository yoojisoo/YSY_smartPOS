<template>
	<v-app>
		<div v-if="!isSignView">
			<keep-alive>
				<TheSystemBar />
			</keep-alive>
			<keep-alive>
				<TheHeader :parentPage="parentPage" @menuClick="menuClick" />
			</keep-alive>
		</div>
		<v-main>
			<router-view :parentPageName="parentPage" />
		</v-main>
		<keep-alive>
			<v-footer v-if="!isSignView" class="ma-0 pa-0" app fixed>
				<TheFooter />
			</v-footer>
		</keep-alive>
	</v-app>
</template>

<script>
import { TheSystemBar, TheHeader, TheFooter, YsyUtil } from '@/assets/util/importFile.js';
export default {
	components: { TheSystemBar, TheHeader, TheFooter },
	data() {
		return {
			parentPage: '',
		};
	},
	mixins: [],
	created() {
		console.log('app.vue created');
		if (sessionStorage.getItem('loginData')) {
			const access_token = JSON.parse(sessionStorage.getItem('loginData')).authStore.loginData
				.access_token;
			YsyUtil.setAccessToken(this.$axios, access_token);
		}
	},
	mounted() {
		//console.log('app.vue mounted ');
		///** 페이지 새로고침시access 유실되는 문제 발생으로 로그인 데이터를 다시 참조하여 accessToken을 넣어줌 */
		//if (sessionStorage.getItem('loginData')) {
		//	const access_token = JSON.parse(sessionStorage.getItem('loginData')).authStore.loginData
		//		.access_token;
		//	YsyUtil.setAccessToken(this.$axios, access_token);
		//}
	},
	methods: {
		refreshAll() {
			// 새로고침
			console.log('새로고침');
		},
		menuClick(parentPageName) {
			this.parentPage = parentPageName;
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
