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
			<v-footer v-if="!isSignView" class="ma-0 pa-0"> <!-- app fixed -->
				<TheFooter />
			</v-footer>
		</keep-alive>
	</v-app>
</template>

<script>
import { TheSystemBar, TheHeader, TheFooter, YsyUtil } from '@/assets/import/index.js';
export default {
	components: { TheSystemBar, TheHeader, TheFooter },
	data() {
		return {
			parentPage: '',
		};
	},
	created() {
		console.log('app.vue created');
		//window.addEventListener('beforeunload', this.handler);
		/** 페이지 새로고침시access 유실되는 문제 발생으로 로그인 데이터를 다시 참조하여 accessToken을 넣어줌 */
		if (localStorage.getItem('vuex')) {
			const access_token = JSON.parse(localStorage.getItem('vuex')).authStore.loginData.access_token;
			YsyUtil.setAccessToken(this.$axios, access_token);
		}
	},
	mounted() {
		window.addEventListener('beforeunload', this.onUnload);
	},
	computed: {
		isSignView() {
			return this.$route.name === 'signIn' || this.$route.name === 'signUp';
		},
	},
	methods: {
		refreshAll() {
			// 새로고침
			console.log('새로고침');
		},
		beforePageDestroyed(event) {
			console.log('beforePageDestroyed');
		},
		menuClick(parentPageName) {
			this.parentPage = parentPageName;
		},
		onUnload(event) {
			window.localStorage.removeItem('loginData');
		},
	},
	beforeDestroy() {
		window.removeEventListener('beforeunload', this.onUnload);
		localStorage.clear();
	},
};
</script>

<style scoped>
.container {
	max-width: 100vw;
	padding: 0px;
}
</style>
