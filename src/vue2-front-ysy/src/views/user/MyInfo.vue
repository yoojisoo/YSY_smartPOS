<!-- 회원정보 수정 page-->
<template>
	<v-container fluid pa-0 ma-0 style="background-color: lightgray; height: 100%">
		<v-row justify="center" no-gutters style="height: 100%">
			<v-col cols="12" md="4" lg="4" xl="4" style="background-color: lightslategray">
				<v-card tile flat height="94%" class="d-flex flex-column">
					<v-card-title>프로필 수정</v-card-title>
					<v-card-subtitle>S-POS 가입 정보를 수정 하실 수 있습니다.</v-card-subtitle>
					<v-divider />
					<v-card-text class="flex-grow-1">
						<v-form :disabled="formDisabled" class="pt-5 pb-5">
							<v-row justify="center" class="mr-0">
								<!-- smk : 이메일을 아이디로 사용하는 곳(구글)은
												아이디 변경이 불가능하게 해놨길래
												여기도 아이디는 변경 불가능하게 해둠 -->
								<v-col cols="5" md="3" lg="3" xl="3" align-self="center">
									<v-subheader style="justify-content: center">
										아이디
									</v-subheader>
								</v-col>
								<v-col cols="7" md="4" lg="4" xl="4" align-self="center">
									<h4>{{ getUser.user_id }}</h4>
								</v-col>
							</v-row>
							<v-row justify="center" class="mr-0">
								<v-col cols="5" md="3" lg="3" xl="3" align-self="center">
									<v-subheader style="justify-content: center">
										비밀번호
									</v-subheader>
								</v-col>
								<v-col cols="7" md="4" lg="4" xl="4" align-self="center">
									<h4 v-if="formDisabled">********</h4>
									<v-text-field
										v-else
										v-model="userInfoChange.password"
										:append-icon="passwordShow ? 'mdi-eye' : 'mdi-eye-off'"
										:type="passwordShow ? 'text' : 'password'"
										@click:append="passwordShow = !passwordShow"
										filled
										dense
										required
										:rules="rules.password"
									></v-text-field>
								</v-col>
							</v-row>
							<v-row justify="center" class="mr-0" v-if="!formDisabled">
								<v-col cols="5" md="3" lg="3" xl="3" align-self="center">
									<v-subheader style="justify-content: center">
										비밀번호 확인
									</v-subheader>
								</v-col>
								<v-col cols="7" md="4" lg="4" xl="4" align-self="center">
									<v-text-field
										v-model="confirmPassword"
										:append-icon="
											confirmPasswordShow ? 'mdi-eye' : 'mdi-eye-off'
										"
										:type="confirmPasswordShow ? 'text' : 'password'"
										@click:append="confirmPasswordShow = !confirmPasswordShow"
										filled
										dense
										required
										:rules="rules.confirmPassword"
									></v-text-field>
								</v-col>
							</v-row>
							<v-row justify="center" class="mr-0">
								<v-col cols="5" md="3" lg="3" xl="3" align-self="center">
									<v-subheader style="justify-content: center">
										이름
									</v-subheader>
								</v-col>
								<v-col cols="7" md="4" lg="4" xl="4" align-self="center">
									<h4 v-if="formDisabled">{{ getUser.user_name }}</h4>
									<v-text-field
										v-else
										v-model="userInfoChange.name"
										filled
										dense
										required
										:rules="rules.name"
									></v-text-field>
								</v-col>
							</v-row>
						</v-form>
					</v-card-text>
					<v-divider />
					<v-card-actions class="pt-5 pb-5 mr-5">
						<v-spacer />
						<v-btn
							v-if="formDisabled"
							color="purple lighten-2"
							dark
							depressed
							large
							@click="formDisabled = !formDisabled"
							>수정</v-btn
						>
						<v-btn
							v-if="!formDisabled"
							class="mr-3"
							color="purple lighten-2"
							dark
							depressed
							large
							@click="userInfoSave"
							>저장</v-btn
						>
						<v-btn
							v-if="!formDisabled"
							color="purple lighten-2"
							dark
							depressed
							large
							@click="formDisabled = !formDisabled"
						>
							취소
						</v-btn>
					</v-card-actions>
				</v-card>
			</v-col>
		</v-row>
	</v-container>
</template>

<script>
import { TheSystemBar, TheHeader, TheFooter } from '@/assets/util/importFile.js';
import { mapGetters } from 'vuex';

const authStore = 'authStore';

export default {
	components: {
		TheSystemBar,
		TheHeader,
		TheFooter,
	},
	data() {
		return {
			pageName: 'userInfo',
			pageNameKo: '내프로필',
			formDisabled: true,
			id: 'ggg',
			userInfoChange: {
				username: '',
				name: '',
				password: '',
			},
			confirmPassword: '',
			passwordShow: false, // 비밀번호 text type
			confirmPasswordShow: false, // 비밀번호 확인 text type
			rules: {
				name: [
					// 사용자 이름 규칙 (필수값, 특수문자 체크)
					v => !!v || '이름은 필수 입력사항입니다.',
					v =>
						!/[~!@#$%^&*()_+|<>?:{}]/.test(v) ||
						'이름에는 특수문자를 사용할 수 없습니다.',
				],
				password: [
					// 사용자 비밀번호 규칙 (필수값 체크)
					v => !!v || '비밀번호는 필수 입력사항입니다.',
				],
				confirmPassword: [
					// 비밀번호 확인 규칙 (필수값, 비밀번호와 일치 여부 체크)
					v => !!v || '비밀번호는 필수 입력사항입니다.',
					v => v === this.userInfoChange.password || '비밀번호가 일치하지 않습니다.',
				],
			},
		};
	},
	computed: {
		...mapGetters(authStore, ['getUser']),
	},
	mounted() {
		if (this.getUser) {
			console.log('1.myinfo this.getUser.access_token = ' + this.getUser.access_token);
			console.log('1.myinfo this.getUser.refresh_token = ' + this.getUser.refresh_token);
			this.userInfoChange.username = this.getUser.user_id;
		}
	},
	methods: {
		userInfoSave() {
			//const validate = this.$refs.form.validate();

			if (this.userInfoChange.password === this.confirmPassword) {
				//if (validate) {
				this.$axios
					.post('/ysy/v1/user/modUserInfo', this.userInfoChange)
					.then(res => {
						if (res.data) {
							let payload = {
								user_id: this.getUser.user_id,
								user_name: this.userInfoChange.name,
								access_token: this.getUser.access_token,
								access_token_exp: this.getUser.access_token_exp,
								refresh_token: this.getUser.refresh_token,
								refresh_token_exp: this.getUser.refresh_token_exp,
							};
							this.$store.dispatch('setUserInfo', payload);
							alert('프로필이 수정되었습니다.');
							this.formDisabled = true;
						} else {
							alert('프로필 수정 실패');
						}
					})
					.catch(error => {
						console.log('myinfo error');
						console.log(error);
					});
				//} else alert('양식에 맞게 작성해주세요.');
			} else {
				alert('비밀번호가 일치하지 않습니다.');
			}
		},
	},
};
</script>

<style></style>
