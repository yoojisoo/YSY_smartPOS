<template>
	<v-system-bar class="hidden-sm-and-down" app height="30">
		<v-container class="ma-0 pa-0" fluid>
			<v-row justify="center" no-gutters>
				<v-col cols="12" xs="12" sm="12" md="8" lg="8" xl="8">
					<v-row justify="end" no-gutters>
						<v-btn-toggle tile group v-if="isLogin && isAdmin">
							<v-btn plain to="/myInfo">관리자페이지</v-btn>
							<v-btn plain to="/myInfo">내프로필</v-btn>
							<v-btn plain @click="logout">로그아웃</v-btn>
							<FThemeChgBtn />
						</v-btn-toggle>

						<v-btn-toggle tile group v-else-if="isLogin && !isAdmin">
							<v-btn plain to="/myInfo">내프로필</v-btn>
							<v-btn plain @click="logout">로그아웃</v-btn>
							<FThemeChgBtn />
						</v-btn-toggle>

						<v-btn-toggle tile group v-else>
							<v-btn plain to="/signIn">로그인</v-btn>
							<v-btn plain to="/signUp">회원가입</v-btn>
							<FThemeChgBtn />
						</v-btn-toggle>
					</v-row>
				</v-col>
			</v-row>
		</v-container>
	</v-system-bar>
</template>

<script>
import { FThemeChgBtn } from '@/assets/util/importFile.js';
import { mapGetters } from 'vuex';

export default {
	name: 'main-system-bar',
	components: { FThemeChgBtn },
	data() {
		return {};
	},
	methods: {
		logout() {
			this.$store.dispatch('authStore/clearUserInfo');
			console.log('##### 로그아웃');
			// [ '/' 오류 뜨는 이유 ]
			// 현재 '/' 경로에 있기 때문에 뜨는거라서 catch로 오류를 잡아줌
			this.$router.replace('/').catch(() => {});
			location.reload();
		},
	},
	computed: {
		...mapGetters({ isLogin: 'authStore/isLogin' }),
		...mapGetters({ isAdmin: 'authStore/isAdmin' }),
		...mapGetters({ getUserId: 'authStore/getUserId' }),
	},
};
</script>
