<!-- main 화면 -->
<template>
	<v-app>
		<!-- Header Start -->
		<mainSystemBar />
		<mainHeader :pageName="pageName" />
		<!-- Header End -->

		<!-- Main Start -->
		<v-main>
			<v-container fluid pa-0 ma-0 style="height: 100%">
				<!-- style="background-color: white" -->
				<v-row justify="center" no-gutters style="height: 100%">
					<!-- 슬라이드 배너 가로 폭 : 전체 -->
					<v-col cols="12">
						<carousel />
					</v-col>
					<v-col cols="12" md="8" lg="8" xl="8">
						<!-- style="background-color: #6667ab" -->
						<v-row justify="center" no-gutters style="height: inherit">
							<!-- 슬라이드 배너 가로 폭 : 맞춤 -->
							<!--<v-col cols="12">
								<carousel />
							</v-col>-->
							<v-col :cols="filterCols" class="hidden-xs-only">
								<v-card
									v-if="filterShow"
									height="100%"
									tile
									color="indigo"
									flat
								></v-card>
							</v-col>
							<v-col :cols="mainCols">
								<!--<v-row justify="center" no-gutters>
									<v-col align-self="center" class="hidden-xs-only">
										<v-btn
										class="ma-2 hidden-xs-only"
										@click="colsChange"
										>
										web filter
										</v-btn>
									</v-col>
									<v-col align-self="center" class="hidden-sm-and-up">
										<v-btn
										class="ma-2"
										@click="colsChange"
										>
										mobile filter 
										</v-btn>
									</v-col>
								</v-row>-->
								<!--<v-row justify="center" no-gutters>
									<v-col clos="12">
										<pageHistory :pageNameKo="pageNameKo" />
									</v-col>
								</v-row>-->
								<v-row align="start" justify="center" no-gutters>
									<v-col cols="12">
										<ysyGrid :gridInfo="sysNoticeInfo" />
									</v-col>
									<v-col cols="12" md="6" lg="6" xl="6">
										<ysyGrid :gridInfo="storeNoticeInfo" />
									</v-col>
									<v-col cols="12" md="6" lg="6" xl="6">
										<ysyGrid :gridInfo="sysNoticeInfo" />
									</v-col>
								</v-row>
							</v-col>
						</v-row>
					</v-col>
				</v-row>
				<noticeDialog :content="curRow"/>
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
import mainFooter from '@/components/TheFooter.vue';
import ysyGrid from '@/components/YsyGrid.vue';
import pageHistory from '@/components/PageHistory.vue';
import carousel from '@/components/Carousel.vue';
import { mapGetters, mapActions } from 'vuex';
 import noticeDialog from '@/components/noticeDialog.vue'

export default {
	components: {
		mainSystemBar,
		mainHeader,
		mainFooter,
		ysyGrid,
		pageHistory,
		carousel,
		noticeDialog,
	},
	data() {
		return {
			filterCols: 0,
			mainCols: 12,
			filterShow: false,
			pageName: 'home',
			pageNameKo: '메인페이지',
			currentRow: {},
			sysNoticeInfo: {
				dataList: [],
				headers: [
					{ text: '아이디', value: 'username', width: '40%', key: true },
					{ text: '이름', value: 'name', width: '40%' },
					{ text: '날짜', value: 'regDt', width: '20%' },
				],
				dateGubun: '/',
				gridNm: '시스템 공지사항',
				path: '/systemNotice',
				isCheckBox: true,
				isSingleSelect: false,
				rowCnt: 7,
				gridDense: true,
				isDetail: false,
				rowClick: (row, gridNm) => {
					this.rowClick(row, gridNm);
				}, //로우 클릭 이벤트 콜백
				rowDbClick : (row, gridNm) =>  { this.rowDbClick(row, gridNm); }, //로우 더블클릭 이벤트 콜백
			},
			storeNoticeInfo: {
				dataList: [{ username: 'd', phone: '444', regDt1: '44' }],
				headers: [
					{ text: '아이디', value: 'username', width: '40%', key: true },
					{
						text: '전화번호',
						value: 'phone',
						width: '40%',
						//    divider: true,
						//    children: [{ text: "phone" , value: 'userPhone'}, { text: "날짜" , value: 'regDt1'}]
					},
				],
				dateGubun: '-',
				gridNm: '스토어 공지사항',
				path: '/storeNotice',
				isCheckBox: true,
				isSingleSelect: false,
				rowCnt: 7,
				gridDense: true,
				//pageTotCnt: 7,
				isDetail: false,
				rowClick: (row, gridNm) => {
					this.rowClick(row, gridNm);
				}, //로우 클릭 이벤트 콜백
				rowDbClick : (row, gridNm) =>  { this.rowDbClick(row, gridNm); }, //로우 더블클릭 이벤트 콜백
			},
			config: {
				headers: {
					access_token: this.$store.state.authStore.loginData.userToken,
				},
			},
		};
	},
	methods: {
		login() {
			this.$router.replace('/signIn');
		},
		logout() {
			this.$store.commit('clearUserInfo');
			this.$router.replace('/signIn');
		},
		colsChange() {
			// 필터와 메인 그리드 cols 변경 (아이콘 눌렀을때)
			this.filterShow = !this.filterShow;

			if (this.filterCols < 2) this.filterCols = 2;
			else this.filterCols = 2;

			if (this.mainCols < 12) this.mainCols = 12;
			else this.mainCols = 10;
		},

		async setSystemNoticeList() {
			await this.$store.dispatch('findNoticeInfo');
			if (this.systemNoticeList) {
				this.sysNoticeInfo.dataList = this.systemNoticeList;
			}
		},
		setStoreNoticeList() {
			// await this.$store.dispatch('setUserList');
			for (let index = 1; index < 100; index++) {
				let json = {};
				json.username = 'aa' + index;
				json.phone = '' + index;
				json.regDt1 = '222';
				this.storeNoticeInfo.dataList.push(json);
			}

			// this.storeNoticeInfo.dataList = this.storeNoticeList;
		},

		rowClick(row, gridNm) {
			console.log('rowClick= ' + gridNm);
			console.log(row);
			if (gridNm === '시스템 공지사항') {
				// if(this.sysNoticeInfo.dataList.includes(row.item)){
				// 	row.item.username = "aaa";
				// }
			}
		},
		rowDbClick( row, gridNm ) {
			this.currentRow = row;
			console.log(" 더블클릭 row ===> ");
			console.log(row);
		},
	},
	computed: {
		// ...mapActions(['findNoticeInfo']),
		isLogin() {
			return this.$store.getters.isLogin;
		},
		userId() {
			return this.$store.state.authStore.loginData.userId;
		},
		systemNoticeList() {
			return this.$store.state.gridStore.userList;
		},
		storeNoticeList() {
			return this.storeNoticeInfo.dataList;
		},
		curRow() {
			return this.currentRow;
		}
	},
	mounted() {
		this.setSystemNoticeList();
		this.setStoreNoticeList();		
	},
	created() {},
};
</script>
