<template>
	<v-app>
		<v-main>
			<v-container class="sign_container">
				<v-row class="sign_row">
					<v-col class="sign_col" cols="12">
						<v-row class="sign_row">
							<v-col class="sign_logo" cols="12">
								<FLogoBtnB />
							</v-col>
							<v-col class="sign_col" cols="md-4 xs-12">
								<v-card class="elevation-0">
									<v-card-text>
										<v-form ref="form" @submit.prevent="signUp">
											<v-row justify="space-between" no-gutters>
												<v-col cols="12" class="mb-6">
													<h2>
														POS 설치를 위해 회원정보를 입력해주세요.
													</h2>
												</v-col>
												<!-- 회원가입 정보 Start -->
												<v-col cols="12" class="mb-6">
													<!-- 회사코드 -->
													<v-text-field
														v-model="signUpInfo.userInfo.bizCd"
														class="sign_txt_field"
														type="text"
														placeholder="회사코드"
														filled
														required
														:rules="rules.bizCd"
													></v-text-field>
													<!-- required : HTML5에 새로 도입 된 속성.
                                                                : 양식을 제출하기 전에 입력 필드를 채워야 함을 지정한다. -->
												</v-col>
												<v-col cols="12" class="mb-6">
													<!-- 사용자 이름 -->
													<v-text-field
														v-model="signUpInfo.userInfo.name"
														class="sign_txt_field"
														type="text"
														placeholder="이름"
														filled
														required
														:rules="rules.name"
													></v-text-field>
												</v-col>
												<v-col cols="12">
													<!-- 사용자 아이디(이메일) -->
													<v-text-field
														v-model="signUpInfo.userInfo.username"
														class="sign_txt_field"
														name="user_email"
														type="text"
														placeholder="이메일"
														filled
														required
														:rules="rules.email"
														:append-icon="
															isConfirmKey ? 'mdi-check' : ''
														"
														:readonly="isConfirmEmail"
													></v-text-field>
												</v-col>
												<v-col cols="12" class="mb-6">
													<!-- 이메일 중복확인(확인 전 : 이메일 중복 확인, 확인 후 가입 가능할때 : 이메일 중복 확인 완료) -->
													<v-btn
														color="primary"
														outlined
														large
														block
														@click="confirmEmail"
														:disabled="isConfirmEmail"
													>
														<span v-if="!isConfirmEmail">
															이메일 중복 확인
														</span>
														<span v-else> 이메일 중복 확인 완료 </span>
													</v-btn>
												</v-col>
												<v-col cols="12" class="mb-6">
													<!-- 비밀번호(아이콘을 누르면 비밀번호 표시 형식이 바뀜) -->
													<v-text-field
														v-model="signUpInfo.userInfo.password"
														class="sign_txt_field"
														:append-icon="
															passwordShow ? 'mdi-eye' : 'mdi-eye-off'
														"
														:type="passwordShow ? 'text' : 'password'"
														@click:append="passwordShow = !passwordShow"
														placeholder="비밀번호"
														filled
														required
														:rules="rules.password"
													></v-text-field>
												</v-col>
												<v-col cols="12" class="mb-6">
													<!-- 비밀번호 확인(아이콘을 누르면 비밀번호 표시 형식이 바뀜) -->
													<v-text-field
														v-model="confirmPassword"
														class="sign_txt_field"
														:append-icon="
															confirmPasswordShow
																? 'mdi-eye'
																: 'mdi-eye-off'
														"
														:type="
															confirmPasswordShow
																? 'text'
																: 'password'
														"
														@click:append="
															confirmPasswordShow =
																!confirmPasswordShow
														"
														placeholder="비밀번호 확인"
														filled
														required
														:rules="rules.confirmPassword"
													></v-text-field>
												</v-col>
												<v-col cols="12">
													<!-- 회원가입 버튼 -->
													<v-btn
														class="sign_btn"
														block
														large
														@click="signUp"
														@keyup.enter="signUp"
													>
														<span class="sign_btn_txt">회원가입</span>
													</v-btn>
												</v-col>
												<v-col cols="12">
													<div
														class="grey--text mt-4"
														v-on:click="signIn"
													>
														{{ toggleMessage }}
													</div>
												</v-col>
											</v-row>
										</v-form>
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
import { FLogoBtnB } from '@/assets/util/importFile.js';

export default {
	name: 'App',
	components: { FLogoBtnB },
	data() {
		return {
			isConfirmEmail: false, // 이메일 중복확인 (true:가입 가능, false: 가입 불가능)
			key: '', // 이메일 인증용 key
			isConfirmKey: false, // 이메일 인증 여부
			mailSended: false, // 인증용 이메일 전송 여부
			passwordShow: false, // 비밀번호 text type
			confirmPasswordShow: false, // 비밀번호 확인 text type
			toggleMessage: 'Already have an Account? Sign In', // 회원가입 페이지에서 로그인 페이지로 이동 버튼 메세지
			signUpInfo: {
				userInfo: {
					// 회원가입용 사용자 정보
					bizCd: '',
					username: '',
					password: '',
					name: '',
				},
				mailInfo: {
					// 이메일 중복확인, 이메일 인증용 사용자 이메일 정보
					email: '',
				},
			},
			confirmPassword: '', // 비밀번호 확인
			mailInfo: {
				// 이메일 중복확인, 이메일 인증용 사용자 이메일 정보
				email: '',
			},
			valueDeterminate: 50,
			rules: {
				// 회원가입 기입 정보 규칙
				bizCd: [
					// 회사코드 규칙 (필수값 체크)
					v => !!v || '회사코드는 필수 입력사항입니다.',
				],
				name: [
					// 사용자 이름 규칙 (필수값, 특수문자 체크)
					v => !!v || '이름은 필수 입력사항입니다.',
					v =>
						!/[~!@#$%^&*()_+|<>?:{}]/.test(v) ||
						'이름에는 특수문자를 사용할 수 없습니다.',
				],
				email: [
					// 사용자 이메일(아이디) 규칙 (필수값, 이메일 형식 체크)
					v => !!v || '이메일은 필수 입력사항입니다.',
					v => {
						const replaceV = v.replace(/(\s*)/g, '');
						const pattern =
							/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/;
						return pattern.test(replaceV) || '이메일 형식으로 입력해주세요';
					},
				],
				password: [
					// 사용자 비밀번호 규칙 (필수값 체크)
					v => !!v || '비밀번호는 필수 입력사항입니다.',
				],
				confirmPassword: [
					// 비밀번호 확인 규칙 (필수값, 비밀번호와 일치 여부 체크)
					v => !!v || '비밀번호는 필수 입력사항입니다.',
					v => v === this.signUpInfo.userInfo.password || '비밀번호가 일치하지 않습니다.',
				],
			},
		};
	},
	methods: {
		/** 이메일 중복 확인 btn ***********************
		 * DB에 같은 아이디(이메일)가 있는지 체크한다. */
		confirmEmail() {
			this.signUpInfo.mailInfo.email = this.signUpInfo.userInfo.username;
			console.log('✅✅✅✅✅');
			console.log(this.signUpInfo.mailInfo);
			this.$axios.post('ysy/v1/mail/mailConfirm', this.signUpInfo.mailInfo).then(res => {
				if (res.data == true) {
					//아이디 있는경우
					alert('중복된 아이디입니다. \n다른 아이디로 다시 진행해주세요.');
					this.isConfirmEmail = false;
				} else {
					alert('가입 가능한 아이디입니다.');
					this.isConfirmEmail = true;
				}
			});
		},
		/** 회원가입 btn */
		async signUp() {
			//const validate = this.$refs.form.validate();
			if (this.isConfirmEmail) {
				if (this.signUpInfo.userInfo.password === this.confirmPassword) {
					try {
						let res = await this.$store.dispatch('authStore/signUp', this.signUpInfo);
						if (res) {
							alert(
								'회원가입이 완료되었습니다.\n메일을 확인하여 인증을 완료해주세요.',
							);
							this.$router.push({ name: 'signIn' });
						} else {
							alert('회원가입 실패\n관리자에게 문의바랍니다.');
							this.$router.replace('/signUp').catch(() => {});
							location.reload();
						}
					} catch (error) {
						console.log('SignUp.vue error => ' + error);
					}
				} else alert('비밀번호가 일치하지 않습니다.');
			} else alert('이메일 중복확인은 필수 항목입니다.');
		},
		signIn() {
			this.$router.push({ name: 'signIn' });
		},
	},
};
</script>
