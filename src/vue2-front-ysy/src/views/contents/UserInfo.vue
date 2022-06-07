<!-- 회원정보 수정 page-->
<template>
	<v-app>
		<!-- Header Start -->
		<mainSystemBar />
		<mainHeader :pageName="pageName" />
		<!-- Header End -->

		<v-main>
			<v-container fluid pa-0 ma-0 style="background-color: lightgray; height: 100%">
				<v-row justify="center" no-gutters style="height: 100%">
					<v-col cols="12" md="4" lg="4" xl="4" style="background-color: lightslategray">
						<pageHistory :pageNameKo="pageNameKo" />
						<v-card tile flat height="94%" class="d-flex flex-column">
							<v-card-title>프로필 수정</v-card-title>
							<v-card-subtitle
								>S-POS 가입 정보를 수정 하실 수 있습니다.</v-card-subtitle
							>
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
												type="password"
												filled
												hide-details
												dense
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
												type="password"
												filled
												hide-details
												dense
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
												filled
												hide-details
												dense
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
		</v-main>

		<!-- Footer Start -->
		<v-footer class="ma-0 pa-0" fixed app>
			<mainFooter />
		</v-footer>
		<!-- Footer End -->
	</v-app>
</template>

<script>
import mainSystemBar from '@/components/header/TheSystemBar.vue';
import mainHeader from '@/components/header/TheHeader.vue';
import mainFooter from '@/components/TheFooter.vue';
import { mapGetters } from 'vuex';
import pageHistory from '@/components/PageHistory.vue';

export default {
	components: {
		mainSystemBar,
		mainHeader,
		mainFooter,
		pageHistory,
	},
	data() {
		return {
			pageName: 'userInfo',
			pageNameKo: '내프로필',
			formDisabled: true,
			id: 'ggg',
		};
	},
	computed: {
		...mapGetters(['getUser']),
	},
};
</script>

<style></style>
