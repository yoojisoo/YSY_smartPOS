<template>
	<v-system-bar class="hidden-sm-and-down" app height="30">
		<v-container class="ma-0 pa-0" fluid>
			<v-row justify="center" no-gutters>
				<v-col cols="10">
					<v-row justify="end" no-gutters>
						<v-btn-toggle tile group v-if="isLogin && isAdmin">
							<v-btn v-if="getMyRole && getMyRole == 'ADMIN'" plain @click="fn_freeBoard"> admin 자유게시판 </v-btn>
							<v-btn plain @click="myInfo">관리자페이지</v-btn>
							<v-btn plain @click="myInfo">내프로필</v-btn>
							<v-btn plain @click="logout">로그아웃</v-btn>
							<BaseButtonThemeChange />
						</v-btn-toggle>

						<v-btn-toggle tile group v-else-if="isLogin && !isAdmin">
							<v-btn plain @click="myInfo">내프로필</v-btn>
							<v-btn plain @click="logout">로그아웃</v-btn>
							<BaseButtonThemeChange />
						</v-btn-toggle>

						<v-btn-toggle tile group v-else>
							<v-btn plain @click="signIn">로그인</v-btn>
							<v-btn plain @click="signUp">회원가입</v-btn>
							<BaseButtonThemeChange />
						</v-btn-toggle>
					</v-row>
				</v-col>
			</v-row>
		</v-container>
	</v-system-bar>
</template>

<script>
import { BaseButtonThemeChange } from '@/assets/util/importFile.js';
import store from '@/store';
import { mapGetters } from 'vuex';

export default {
	name: 'main-system-bar',
	components: { BaseButtonThemeChange },
	methods: {
		signIn() {
			this.$router.replace({ name: 'signIn' }).catch(() => {});
		},
		signUp() {
			this.$router.replace({ name: 'signUp' }).catch(() => {});
		},
		myInfo() {
			this.$router.push({ name: 'myInfo' }).catch(() => {});
		},
		logout() {
			this.$store.dispatch('authStore/clearUserInfo');
			// [ '/' 오류 뜨는 이유 ]
			// 현재 '/' 경로에 있기 때문에 뜨는거라서 catch로 오류를 잡아줌
			this.$router.replace({ name: 'home' }).catch(() => {});
			location.reload();
		},
		//admin만 사용하는 자유 게시판임.
		fn_freeBoard() {
			this.$router.push({ name: 'adminFreeBoard' }).catch(() => {});
		},
	},
	computed: {
		...mapGetters({ isLogin: 'authStore/isLogin' }),
		...mapGetters({ isAdmin: 'authStore/isAdmin' }),
		...mapGetters({ getUserId: 'authStore/getUserId' }),

		getMyRole() {
			return this.$store.state.authStore.myRole;
		},
	},
};
</script>
