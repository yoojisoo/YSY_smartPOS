<template>
	<v-system-bar class="hidden-sm-and-down" app height="35">
		<v-container class="ma-0 pa-0" fluid>
			<v-row justify="center" no-gutters>
				<v-col cols="10">
					<v-row justify="end" no-gutters>
						<v-btn-toggle tile group v-if="isLogin && isAdmin">
							<v-btn v-if="getMyRole && getMyRole == 'ADMIN'" plain @click="fn_freeBoard"> admin 자유게시판 </v-btn>
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
import { BaseButtonThemeChange } from '@/assets/import/index.js';
//import Swal from 'sweetalert2';

export default {
	name: 'main-system-bar',
	components: { BaseButtonThemeChange },
	methods: {
		signIn() {
			// 로그인 후 기존 페이지로 이동하기 위해서 Storage에 name 저장
			// ⭐ sessionStorage라서 브라우저를 닫으면 사라지지만
			// ⭐ 페이지 이동 후 Storage를 비워줘야하는지 ...?
			// ⭐ 왜냐면 굳이 안쓰는 데이터를 브라우저 종료 시점까지 가지고 가야하니까 ?
			sessionStorage.setItem('routeName', this.$route.name);
			this.$router.push({ name: 'signIn' }).catch(() => {});
		},
		signUp() {
			// 회원가입 후 로그인 페이지로 이동해서 로그인 후
			// 기존 페이지로 이동하기 위해서 Storage에 name 저장
			sessionStorage.setItem('routeName', this.$route.name);
			this.$router.push({ name: 'signUp' }).catch(() => {});
		},
		myInfo() {
			this.$router.push({ name: 'myInfo' }).catch(() => {});
		},
		logout() {
			//Swal.fire('Hey user!', 'You are the rockstar!', 'info');
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
		isLogin() {
			return this.$store.state.authStore.loginData.isLogin;
		},
		isAdmin() {
			return this.$store.state.authStore.loginData.isAdmin;
		},
		getUserId() {
			return this.$store.state.authStore.loginData.user_id;
		},
		getMyRole() {
			return this.$store.state.authStore.myRole;
		},
	},
};
</script>
