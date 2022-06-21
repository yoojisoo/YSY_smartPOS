<template>
	<v-app>
		<v-main>
			<v-container class="signUp_container">
				<v-row class="signUp_row">
					<v-col class="signUp_col" cols="12">
						<v-row class="signUp_row">
							<v-col class="col_logo" cols="12">
								<v-btn class="btn_remove_hover" text x-large to="/">
									<v-img
										contain
										height="50"
										src="@/assets/img/svg/logoWhite.svg"
									/>
								</v-btn>
							</v-col>
							<v-col class="signUp_col" cols="md-4 xs-12">
								<v-card class="elevation-0">
									<v-card-text>
										<v-form ref="form" @submit.prevent="signUp">
											<v-row justify="space-between" no-gutters>
												<!--<v-col cols="12" class="mb-6">
													<v-progress-linear
														v-model="valueDeterminate"
														class="progress_linear"
													></v-progress-linear>
												</v-col>-->
												<v-col cols="12" class="mb-6">
													<h2>
														POS 설치를 위해 회원정보를 입력해주세요.
													</h2>
												</v-col>
												<!-- 회원가입 정보 Start -->
												<v-col cols="12" class="mb-6">
													<!-- 회사코드 -->
													<v-text-field
														v-model="signUpInfo.bizCd"
														class="signUp_txt_field"
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
														v-model="signUpInfo.name"
														class="signUp_txt_field"
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
														v-model="signUpInfo.username"
														class="signUp_txt_field"
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
												<!-- <v-col cols="12" v-if="mailSended"> -->
												<!-- 이메일 인증 버튼을 눌렀을때,
                                                         메일 전송이 완료되면 나타나는
                                                         인증번호 입력 필드 -->
												<!-- <v-text-field
                                                        v-model="key"
                                                        class="signUp_txt_field"
                                                        type="text"
                                                        placeholder="인증번호 숫자 6자리"
                                                        filled
                                                        required
                                                    ></v-text-field>
                                                    <v-btn
                                                        color="primary"
                                                        outlined
                                                        large
                                                        block
                                                        dark
                                                        @click="confirmSignUpKey"
                                                    >
                                                        이메일 인증 확인
                                                    </v-btn>
                                                </v-col> -->
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
													<!-- 이메일 인증(인증 전 : 이메일 인증, 인증 후 : 이메일 인증 완료) -->
													<!-- <v-btn
                                                        color="primary"
                                                        outlined
                                                        large
                                                        block
                                                        dark
                                                        @click="mailSend"
                                                    >
                                                        <span v-if="!isConfirmKey"> 이메일 인증 </span>
                                                        <span v-else> 이메일 인증 완료 </span>
                                                    </v-btn> -->
												</v-col>
												<v-col cols="12" class="mb-6">
													<!-- 비밀번호(아이콘을 누르면 비밀번호 표시 형식이 바뀜) -->
													<v-text-field
														v-model="signUpInfo.password"
														class="signUp_txt_field"
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
														class="signUp_txt_field"
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
														class="signUp_btn"
														block
														large
														@click="signUp"
														@keyup.enter="signUp"
													>
														<span class="signUp_btn_txt">회원가입</span>
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
// import router from '@/router'
export default {
	name: 'App',
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
				// 회원가입용 사용자 정보
				bizCd: '',
				username: '',
				password: '',
				name: '',
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
					v => v === this.signUpInfo.password || '비밀번호가 일치하지 않습니다.',
				],
			},
		};
	},
	methods: {
		/** 이메일 중복 확인 btn ***********************
		 * DB에 같은 아이디(이메일)가 있는지 체크한다. */
		confirmEmail() {
			const { mailInfo } = this;
			mailInfo.email = this.signUpInfo.username;

			this.$axios.post('ysy/v1/mail/mailConfirm', mailInfo).then(res => {
				if (!res.data) {
					// 이메일 중복 x : 가입 가능
					alert('가입 가능한 아이디입니다.');
					this.isConfirmEmail = true;
				} else {
					// 이메일 중복 o : 가입 불가능
					alert('중복된 아이디입니다. \n다른 아이디로 다시 진행해주세요.');
					this.isConfirmEmail = false;
				}
			});
		},
		/** 이메일 인증 btn ****************************************
		 * 사용자가 입력한 이메일로 인증 메일을 보내고 *************
		 * 메일이 성공적으로 보내지면 인증키값을 store에 저장한다. */
		// mailSend() {
		//     const { mailInfo } = this;
		//     mailInfo.email = this.signUpInfo.username;

		//     this.$axios.post("/ysy/v1/mail/mailSend", mailInfo)
		//         .then((res) => {
		//             console.log(res.data);
		//             if(res.data !== "fail") { // 성공하면 store에 저장하기 위해 인증key가 리턴되기때문에 "fail이 아닐때"로 체크함
		//                 alert("메일 보내기 성공 => " + res.data);
		//                 this.mailSended = true; // 메일 보내기 성공 (인증번호 입력란 나타남)
		//                 // let key = res.data;
		//                 // this.$store.dispatch('setSignUpKey', key); // 받아온 이메일 인증키 store에 저장
		//             } else {
		//                 alert(res.data);
		//             }
		//         })
		// },
		/** 이메일 인증 확인 btn *************
		 * store에 저장된 인증key값과 ********
		 * 사용자가 입력한 인증번호를 바교함 */
		// confirmSignUpKey() {
		//     const confirmKey = this.$store.getters.getSignUpKey;

		//     if(this.key == confirmKey) {
		//         alert("인증 성공");
		//         this.isConfirmKey = true;
		//         this.mailSended = false;
		//     } else {
		//         alert("인증 번호가 일치하지 않습니다.");
		//     }
		// },
		/** 회원가입 btn */
		signUp() {
			const { signUpInfo } = this;
			const validate = this.$refs.form.validate();
			this.mailInfo.email = this.signUpInfo.username;

			// if(this.isConfirmEmail && this.isConfirmKey) {
			if (this.isConfirmEmail) {
				if (this.signUpInfo.password === this.confirmPassword) {
					if (validate) {
						this.$axios.post('/ysy/v1/auth/signUp', signUpInfo).then(res1 => {
							console.log(res1.data);
							if (res1.data === 'ok') {
								this.$axios
									.post('/ysy/v1/mail/mailSend', this.mailInfo)
									.then(res2 => {
										if (res2.data) {
											alert('메일을 확인해주세요.');
											this.$router.replace({ name: 'signIn' });
										} else alert('메일 보내기 실패');
									});
							} else {
								alert(res1.data);
							}
						});
					} else alert('양식에 맞게 작성해주세요.');
				} else alert('비밀번호가 일치하지 않습니다.');
			} else alert('이메일 중복확인은 필수 항목입니다.');
			// } else alert("이메일 중복확인과 인증은 필수 항목입니다.");
		},
		signIn() {
			this.$router.replace({ name: 'signIn' });
		},
	},
};
</script>
