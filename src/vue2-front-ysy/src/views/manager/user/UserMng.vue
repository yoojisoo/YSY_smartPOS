<template>
	<v-app>
		<!-- Header Start -->
		<mainSystemBar />
		<mainHeader :pageName="pageName" />
		<!-- Header End -->

		<!-- Main Start -->
		<v-main>
			<v-container fluid pa-0 ma-0>
				<v-row justify="center" no-gutters>
					<v-col cols="12" md="8" lg="8" xl="8">
						<v-row justify="center" no-gutters style="height: inherit">
							<v-col cols="6">
								<ysyGrid :gridInfo="userInfo" />
							</v-col>
							<v-col cols="6" v-if="isAddr">
								<v-card>
									<v-card-title>유저상세정보</v-card-title>
									<v-card-text>
										<div>아이디 : {{ userDetailInfo.userId }}</div>
										<div>이름 : {{ userDetailInfo.userNm }}</div>
										<div>생성일 : {{ userDetailInfo.regDt }}</div>
										<div>가입경로 : {{ userDetailInfo.oauthPath }}</div>
										<div>비즈네임 : {{ userDetailInfo.bizNm }}</div>
									</v-card-text>
									<div
										outlined
										v-for="addr in userDetailInfo.addrList"
										:key="addr.id"
									>
										<userAddrEdit :addr="addr" />
									</div>
								</v-card>
							</v-col>
						</v-row>
					</v-col>
				</v-row>
			</v-container>
		</v-main>
		<!-- Main End -->

		<!-- Footer Start -->
		<v-footer class="ma-0 pa-0" fixed app>
			<mainFooter />
		</v-footer>
		<!-- Footer End -->
	</v-app>
</template>

<script>
import mainSystemBar from '@/components/1_templates/header/TheSystemBar.vue';
import mainHeader from '@/components/1_templates/header/TheHeader.vue';
import mainFooter from '@/components/1_templates/footer/TheFooter.vue';
import ysyGrid from '@/components/FGrid.vue';
import UserAddrEdit from '@/components/1_templates/TheAddrEdit.vue';
import MixinGlobal from '@/mixin/MixinGlobal';

export default {
	components: {
		mainSystemBar,
		mainHeader,
		mainFooter,
		ysyGrid,
		UserAddrEdit,
	},
	mixins: [MixinGlobal],
	data() {
		return {
			pageName: 'User Manager',
			isAddr: true, // user 상세 정보 view 변수
			userInfo: {
				dataList: [{}],
				headers: [
					{ text: '아이디', value: 'userId', key: true },
					{ text: '이름', value: 'userNm' },
					{ text: '날짜', value: 'regDt' },
				],
				dateGubun: '/',
				gridNm: '유저관리',
				path: '/userMng',
				isCheckBox: false,
				isSingleSelect: false,
				rowCnt: 10,
				gridDense: true,
				isDetail: true,
				rowClick: (row, gridNm) => {
					this.fn_userClick(row, gridNm);
				}, //로우 클릭 이벤트 콜백
				rowDbClick: (row, gridNm) => {
					this.fn_userDbClick(row, gridNm);
				}, //로우 더블클릭 이벤트 콜백
			},
		};
	},
	mounted() {
		this.fn_getUserList();
	},
	methods: {
		async fn_getUserList() {
			console.log('UserMng fn_getUserList !!');
			await this.$store.dispatch('userStore/fn_getUserList');

			if (this.getUserList) {
				console.log('🟢 userMng fn_getUserList');
				this.userInfo.dataList = this.getUserList;
				console.log(this.userInfo.dataList);
				console.log('🔴 userMng fn_getUserList');
			} else {
				console.log('❌ userMng fn_getUserList ❌');
			}
		},
	},
};
</script>
