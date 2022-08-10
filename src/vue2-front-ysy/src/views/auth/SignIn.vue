<template>
	<v-container class="sign_container">
		<v-row class="sign_row">
			<v-col class="sign_logo" cols="12">
				<BaseButtonLogoBig />
			</v-col>
			<v-col class="sign_col" cols="12">
				<v-row class="sign_row">
					<v-col cols="md-4 xs-12">
						<v-card elevation="0">
							<v-card-text>
								<v-row justify="space-between" no-gutters>
									<v-col cols="12" class="mb-6">
										<v-text-field
											v-model="params.data.username"
											name="user_id"
											type="text"
											prepend-inner-icon="mdi-human-greeting-variant"
											placeholder="  Hello, Donkey! What's your ID?"
											required
											filled
											hide-details
											@keyup.enter="signIn"
										></v-text-field>
									</v-col>
									<v-col cols="12" class="mb-6">
										<v-text-field
											v-model="params.data.password"
											name="user_pw"
											type="password"
											prepend-inner-icon="mdi-donkey"
											placeholder="  Hmm.. My Password is..."
											required
											filled
											hide-details
											@keyup.enter="signIn"
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
										<v-btn block large color="#ffe812" @click="kakaoLogin">
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
										<v-btn block large color="#03c75a" @click="naverLogin">
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
</template>

<script>
import { BaseButtonLogoBig, CommonService } from '@/assets/util/importFile.js';
//import { YsyUtil } from '@/mixin/MixinGlobal.js';
import YsyUtil from '@/mixin/YsyUtil.js';

export default {
	name: 'App',
	components: { BaseButtonLogoBig },
	//mixins: [YsyUtil],
	data() {
		return {
			toggleMessage: [{ title: 'REMEMBER' }, { title: 'FORGET ID/PW?' }],
			params: {
				url: '/login',
				data: {
					username: '',
					password: '',
				},
			},
		};
	},
	methods: {
		async signIn() {
			if (
				YsyUtil.isNullAndEmpty(this.params.data.username) ||
				YsyUtil.isNullAndEmpty(this.params.data.password)
			) {
				alert('ID와 비밀번호를 입력해주세요.');
			} else {
				try {
					let res = await this.$store.dispatch('authStore/signIn', this.params);
					if (res.status != undefined && res.status == 200) {
						let routeName = sessionStorage.getItem('routeName');
						this.$router.replace({ name: routeName });
					} else {
						console.log('res ===========>', res);
						alert(res);
					}
				} catch (error) {
					console.log('signIn => ' + error.response.status);
				}
			}
		},

		signUp() {
			this.$router.push({ name: 'signUp' });
		},

		async kakaoLogin() {
			try {
				let res = await CommonService.fn_getDataList('/ysy/v1/oauth/getKakaoRedirectUrl');
				YsyUtil.log('kakaoLogin callback url  ', res.data.obj);

				var client_id = 'f21217ecb3112aa4791cbdc7d7e8b4ed'; // kako client key
				// var callbackUrl = 'http://localhost:8000/ysy/v1/oauth/kakao/getCode&response_type=code'; // 서버 주소
				//var callbackUrl = 'http://localhost:8000/kakaoLogin/getCode&response_type=code'; // 서버 주소
				//var callbackUrl = 'http://ysy899.cafe24.com/kakaoLogin/getCode&response_type=code'; // 서버 주소
				// var callbackUrl = 'http://tboom.shop/kakaoLogin/getCode&response_type=code'; // 서버 주소
				var callbackUrl = res.data.obj + '&response_type=code'; // 서버 주소

				YsyUtil.log('callbackUrl ==  ', callbackUrl);
				var url =
					'https://kauth.kakao.com/oauth/authorize?client_id=' +
					client_id +
					'&redirect_uri=' +
					callbackUrl;
				window.location.replace(url);
			} catch (error) {
				YsyUtil.log('kakaoLogin error ', error);
			}
		},
		async naverLogin() {
			try {
				let res = await CommonService.fn_getDataList('/ysy/v1/oauth/getNaverRedirectUrl');
				YsyUtil.log('naverLogin callback url  ', res.data.obj);

				var client_id = '75NEjj6MeqfW6we4eFlJ'; // naver client key
				// var callbackUrl = 'http://localhost:8080/naverLogin'; // 서버 주소
				// var callbackUrl = 'http://localhost:8000/naverLogin/getCode'; // 서버 주소
				var callbackUrl = res.data.obj; // 서버 주소
				YsyUtil.log('callbackUrl ==  ', callbackUrl);
				var url =
					'https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=' +
					client_id +
					'&redirect_uri=' +
					callbackUrl;
				window.location.replace(url);
			} catch (error) {
				YsyUtil.log('naverLogin error ', error);
			}
		},
	},
};
</script>
