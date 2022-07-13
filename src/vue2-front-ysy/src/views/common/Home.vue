<!-- main 화면 -->
<template>
	<v-app>
		<!-- Header Start -->
		<TheSystemBar />
		<TheHeader :pageName="pageName" />
		<!-- Header End -->

		<!-- Main Start -->
		<v-main>
			<v-container class="main_layout_container">
				<!-- style="background-color: white" -->
				<v-row class="main_layout_row">
					<!-- 슬라이드 배너 가로 폭 : 전체 -->
					<v-col class="main_layout_col" cols="12">
						<carousel />
					</v-col>
					<v-col class="main_layout_col" cols="12" md="8" lg="8" xl="8">
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
								<v-row align="start" justify="center" no-gutters>
									<v-col cols="12">
										<ysyGrid :gridInfo="systemNoticeInfo" />
									</v-col>
									<v-col cols="12" md="6" lg="6" xl="6">
										<ysyGrid :gridInfo="storeNoticeInfo" />
									</v-col>
									<v-col cols="12" md="6" lg="6" xl="6">
										<ysyGrid :gridInfo="systemNoticeInfo" />
									</v-col>
								</v-row>
							</v-col>
						</v-row>
					</v-col>
				</v-row>
				<template v-if="isPopup">
					<div>
						<noticeDialog :boardObj="currentRow" :callback="dialogCallback" />
					</div>
				</template>
			</v-container>
		</v-main>
		<!-- Main End -->

		<!-- Footer Start -->
		<v-footer class="ma-0 pa-0" fixed app>
			<TheFooter />
		</v-footer>
		<!-- Footer End -->
	</v-app>
</template>

<script>
import { TheFooter, TheHeader, TheSystemBar } from '@/assets/util/importFile.js';
import ysyGrid from '@/components/FGrid.vue';
import carousel from '@/components/FCarousel.vue';
import noticeDialog from '@/components/FDialog.vue';
import { mapGetters } from 'vuex';

export default {
	components: {
		TheFooter,
		TheHeader,
		TheSystemBar,
		ysyGrid,
		carousel,
		noticeDialog,
	},

	data() {
		return {
			isPopup: false,
			filterCols: 0,
			mainCols: 12,
			filterShow: false,
			pageName: 'home',
			pageNameKo: '메인페이지',
			currentRow: {},
			config: {
				headers: {
					access_token: this.$store.state.authStore.loginData.userToken,
				},
			},
			systemNoticeInfo: {
				dataList: [],
				headers: [
					{ text: '번호', value: 'no', width: '20%', key: true },
					{ text: '제목', value: 'title', width: '40%' },
					{ text: '작성자', value: 'writer', width: '40%' },
				],
				dateGubun: '/',
				gridNm: '시스템 공지사항',
				path: '/noticeSystem',
				isCheckBox: true,
				isSingleSelect: false,
				rowCnt: 7,
				gridDense: true,
				useBtn: true,
				isDetail: false,
				rowClick: (row, gridNm) => {
					this.rowClick(row, gridNm);
				}, //로우 클릭 이벤트 콜백
				rowDbClick: (row, gridNm) => {
					this.rowDbClick(row, gridNm);
				}, //로우 더블클릭 이벤트 콜백
			},
			storeNoticeInfo: {
				dataList: [],
				headers: [
					{ text: '번호', value: 'no', width: '20%', key: true },
					{ text: '제목', value: 'title', width: '40%' },
					{ text: '작성자', value: 'writer', width: '40%' },
				],
				dateGubun: '/',
				gridNm: '스토어 공지사항',
				path: '/noticeStore',
				isCheckBox: true,
				isSingleSelect: false,
				rowCnt: 7,
				gridDense: true,
				useBtn: true,
				isDetail: false,
				rowClick: (row, gridNm) => {
					this.rowClick(row, gridNm);
				}, //로우 클릭 이벤트 콜백
				rowDbClick: (row, gridNm) => {
					this.rowDbClick(row, gridNm);
				}, //로우 더블클릭 이벤트 콜백
			},
		};
	},

	mounted() {
		// 시스템 공지사항 셋팅
		this.findSystemNoticeList();

		// 스토어 공지사항 셋팅
		this.findStoreNoticeList();
	},

	methods: {
		colsChange() {
			// 필터와 메인 그리드 cols 변경 (아이콘 눌렀을때)
			this.filterShow = !this.filterShow;

			if (this.filterCols < 2) this.filterCols = 2;
			else this.filterCols = 2;

			if (this.mainCols < 12) this.mainCols = 12;
			else this.mainCols = 10;
		},
		rowClick(row, gridNm) {
			console.log('rowClick= ' + gridNm);
			console.log(row);
			this.currentRow = row.item;
			console.log('this.currentRow 확인중 ~~~');
			console.log(this.currentRow);

			if (gridNm !== '사용자 관리') {
				this.isPopup = true;
			}
		},
		rowDbClick(row, gridNm) {
			console.log(' 더블클릭 row ===> ');
			console.log(row);
		},
		dialogCallback(dialogObj) {
			console.log('dialogObj.msg = ' + dialogObj.msg);
			this.isPopup = false;
		},

		//시스템 공지사항 셋팅
		async findSystemNoticeList() {
			await this.$store.dispatch('noticeStore/findSystemNotice').catch(error => {
				console.log('===============> noticeStore/findSystemNotice error');
				console.log(error);
			});

			if (this.getSystemNoticeList) {
				this.systemNoticeInfo.dataList = this.getSystemNoticeList;
			} else {
				console.log('Home --- findSystemNoticeList() 실패 !!');
			}
		},

		// 스토어 공지사항 셋팅
		async findStoreNoticeList() {
			await this.$store.dispatch('noticeStore/findStoreNotice').catch(error => {
				console.log('===============> noticeStore/findStoreNotice error');
				console.log(error);
			});

			if (this.getStoreNoticeList) {
				this.storeNoticeInfo.dataList = this.getStoreNoticeList;
			} else {
				console.log('this.getStoreNoticeList 실패 !!');
			}
		},
	},
	computed: {
		...mapGetters({ getUser: 'authStore/getUser' }),
		...mapGetters({ getSystemNoticeList: 'noticeStore/getSystemNoticeList' }),
		...mapGetters({ getStoreNoticeList: 'noticeStore/getStoreNoticeList' }),
	},
};
</script>
