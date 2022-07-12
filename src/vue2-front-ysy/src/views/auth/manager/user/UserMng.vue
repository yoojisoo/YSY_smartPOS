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
							<!--<v-col cols="12">
								<pageHistory :pageNameKo="pageNameKo" />
							</v-col>-->
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
import mainSystemBar from '@/components/header/TheSystemBar.vue';
import mainHeader from '@/components/header/TheHeader.vue';
import mainFooter from '@/components/footer/TheFooter.vue';
import ysyGrid from '@/components/YsyGrid.vue';
import UserAddrEdit from '@/components/user/UserAddrEdit.vue';
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
};
</script>
