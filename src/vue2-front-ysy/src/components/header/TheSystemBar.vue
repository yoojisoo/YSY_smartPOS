<template>
	<v-system-bar class="hidden-sm-and-down" color="white" app>
		<v-container class="ma-0 pa-0" fluid>
			<v-row justify="center" no-gutters>
				<v-col cols="12" xs="12" sm="12" md="8" lg="8" xl="8">
					<v-row justify="end" no-gutters>
						<v-btn-toggle tile group v-if="isLogin">
							<v-btn value="left" plain to="/userInfo">내프로필</v-btn>
							<v-divider vertical></v-divider>
							<v-btn value="right" plain @click="logout">로그아웃</v-btn>
						</v-btn-toggle>

						<v-btn-toggle tile group v-else>
							<v-btn value="left" plain to="/signIn">로그인</v-btn>
							<v-divider vertical></v-divider>
							<v-btn value="right" plain to="/signUp">회원가입</v-btn>
						</v-btn-toggle>
					</v-row>
				</v-col>
			</v-row>
		</v-container>
	</v-system-bar>
</template>

<script>
import { mapGetters } from 'vuex';
import authService from '@/service/auth/authService.js';

export default {
	name: 'main-system-bar',
	data() {
		return {};
	},
	mounted() {},
	methods: {
		logout() {
			authService.setLoginClear();
		},
	},
	computed: {
		...mapGetters({ isLogin: 'authStore/isLogin' }),
	},
};
</script>

<style scoped>
.v-system-bar {
	color: #bababa;
}
.v-btn {
	color: black;
}
</style>
