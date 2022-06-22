<template>
	<v-app>
		<v-main>
			<v-container fill-height fluid>
				<v-row justify="center" no-gutters>
					<v-col cols="auto">
						<v-btn class="btn_remove_hover" text x-large to="/">
							<v-img contain height="50" src="@/assets/img/svg/logoWhite.svg" />
						</v-btn>
					</v-col>
					<v-col cols="12">
						<v-row justify="center" no-gutters>
							<v-col cols="md-4 xs-12">
								<v-card elevation="0">
									<v-card-text>
										<v-row justify="space-between" no-gutters>
											<v-col cols="12" class="mb-6">
												<v-text-field
													v-model="signInInfo.username"
													name="user_id"
													type="text"
													prepend-inner-icon="mdi-human-greeting-variant"
													placeholder="  Hello, Donkey! What's your ID?"
													required
													filled
													hide-details
												></v-text-field>
											</v-col>
											<v-col cols="12" class="mb-6">
												<v-text-field
													v-model="signInInfo.password"
													name="user_pw"
													type="password"
													prepend-inner-icon="mdi-donkey"
													placeholder="  Hmm.. My Password is..."
													required
													filled
													hide-details
												></v-text-field>
											</v-col>
											<v-col
												cols="auto"
												class="mb-1"
												v-for="(item, idx) in toggleMessage"
												:key="idx"
											>
												<span>{{ item.title }}</span>
											</v-col>
											<v-col cols="12">
												<v-btn
													block
													class="my-2 deep-purple lighten-1"
													dark
													value="sign in"
													@click="signIn"
													>로그인</v-btn
												>
											</v-col>
											<v-col cols="12">
												<v-btn
													block
													outlined
													class="my-2"
													color="#7E57C2"
													value="sign up"
													@click="signUp"
													>회원가입</v-btn
												>
											</v-col>
										</v-row>
										<v-row justify="center">
											<v-col cols="4">
												<!-- <v-btn
													block
													large
													color="#ffe812"
													href="https://kauth.kakao.com/oauth/authorize?client_id=4c9e081b17404f289741f6792bd4c6e7&redirect_uri=http://localhost:8000/ysy/v1/auth/kakao/redirect&response_type=code"
												> -->
												<!-- javascript 방식 -->
												<v-btn
													block
													large
													color="#ffe812"
													href="https://kauth.kakao.com/oauth/authorize?client_id=f21217ecb3112aa4791cbdc7d7e8b4ed&redirect_uri=http://192.168.0.77:8080/kakaoLogin&response_type=code"
												>
													<!-- <v-btn
													block
													large
													color="#ffe812"
													@click="kakaoLogin"
												> -->
													<v-img
														contain
														max-height="40"
														max-width="40"
														src="@/assets/img/png/kakao_logo.png"
													></v-img>
													<h4 style="color: black">카카오 로그인</h4>
												</v-btn>
											</v-col>
											<v-col cols="4">
												<v-btn
													block
													large
													color="#03c75a"
													@click="naverLogin"
												>
													<v-img
														contain
														max-height="40"
														max-width="40"
														src="@/assets/img/png/naver_logo.png"
													></v-img>
													<h4 style="color: white">네이버 로그인</h4>
												</v-btn>
											</v-col>
											<v-col cols="4">
												<v-btn block large color="white" href="">
													<v-img
														contain
														max-height="40"
														max-width="40"
														src="@/assets/img/png/google_logo.png"
													></v-img>
													<h4 style="color: black">구글 로그인</h4>
												</v-btn>
											</v-col>
										</v-row>
									</v-card-text>
								</v-card>
							</v-col>
						</v-row>
					</v-col>
				</v-row>
			</v-container>
		</v-main>
	</v-app>
</template>

<script>
import authService from '@/service/auth/AuthService.js';

export default {
	name: 'App',
	data() {
		return {
			toggleMessage: [{ title: 'REMEMBER' }, { title: 'FORGET ID/PW?' }],
			signInInfo: {
				username: '',
				password: '',
			},
		};
	},
	methods: {
		async signIn() {
			await this.$axios.post('/login', this.signInInfo).then(res => {
				let flag = authService.setLoginData(res.headers);
				if (flag) {
					this.$router.replace('/');
				} else {
					alert('아이디나 비밀번호가 틀렸습니다.');
				}
			});
		},
		signUp() {
			this.$router.replace({ name: 'signUp' });
		},

		naverLogin() {
			var client_id = '75NEjj6MeqfW6we4eFlJ'; //naver client key
			var callbackUrl = 'http://localhost:8080/naverLogin'; //서버 주소
			var url =
				'https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=' +
				client_id +
				'&redirect_uri=' +
				callbackUrl +
				'';
			window.location.replace(url);
		},
	},
};
</script>
