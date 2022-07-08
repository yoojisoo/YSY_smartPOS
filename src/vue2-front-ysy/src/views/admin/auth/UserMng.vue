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
							<v-col cols="6">
								<v-card
									flat
									outlined
								>
									<v-card-title>유저상세정보</v-card-title>
									<v-card-text>
										<div>아이디 :
											{{ userUserDetailInfo }}
										</div>

										<div>이름 :
											{{ userUserDetailInfo.userNm }}
										</div>

										<div>생성일 :
											{{ userUserDetailInfo.regDt }}
										</div>

										<div>가입경로 :
											{{ userUserDetailInfo.oauthPath }} 
										</div>

										<div>데이터 확인 : {{userUserDetailInfo}}</div>
										<!-- <div>주소 :
											<p v-for=" addr in userUserDetailAddrInfo"
												:key="addr.addrZipCode"
											>
										</div>

										<!-- {{ userUserDetailInfo }} -->
									</v-card-text>
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
import { mapGetters, mapActions } from 'vuex';

export default {
	components: {
		mainSystemBar,
		mainHeader,
		mainFooter,
		ysyGrid,
	},
	data() {
		return {
			pageName: 'User Manager',
			// currentRow: {},
			userUserDetailInfo: {},
			userInfo: {
				dataList: [],
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
					this.rowClick(row, gridNm);
				}, //로우 클릭 이벤트 콜백
				rowDbClick: (row, gridNm) => {
					this.rowDbClick(row, gridNm);
				}, //로우 더블클릭 이벤트 콜백
			},
		};
	},
	computed: {
		...mapGetters({ getUserList: 'userStore/getUserList' }),
		...mapGetters({ getUserDetail: 'userStore/getUserDetail' }),
	},

	mounted() {
		this.fn_getUserList();
	},

	methods: {
		rowClick(row, gridNm) {
			this.fn_getUserDetail(row.item.userId);
		},
		rowDbClick(row, gridNm) {
			
		},

		//모든 유저 목록 조회
		async fn_getUserList() {
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

		// 유저 상세정보 조회 : 1명의 아이디로 어드레스 조회
		async fn_getUserDetail(userId) {
			await this.$store.dispatch('userStore/fn_getUserDetail', userId);

			if (this.getUserDetail) {
				console.log('🟢 userMng fn_getUserDetail');
				this.userUserDetailInfo = this.getUserDetail;
				console.log(this.userUserDetailInfo);
				console.log('🔴 userMng fn_getUserDetail');
			} else {
				console.log('❌ userMng fn_getUserDetail ❌');
			}
		},
	},
};
</script>

<style>
@import '../../../assets/css/global_layout.css';
</style>
