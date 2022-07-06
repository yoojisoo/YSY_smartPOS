<!-- main 화면 -->
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
							<v-col cols="12">
								<ysyGrid :gridInfo="systemNoticeInfo" />
							</v-col>
						</v-row>
					</v-col>
				</v-row>
				<template v-if="isPopup">
					<boardDialog :boardObj="currentRow" :callback="dialogCallback" />
				</template>
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
import pageHistory from '@/components/PageHistory.vue';
import { mapGetters } from 'vuex';
import boardDialog from '@/components/Dialog.vue';

export default {
	components: {
		mainSystemBar,
		mainHeader,
		mainFooter,
		ysyGrid,
		pageHistory,
		boardDialog,
	},
	data() {
		return {
			pageName: 'noticeSystem',
			pageNameKo: '시스템 공지사항',
			systemNoticeInfo: {
				dataList: [],
				headers: [
					{ text: '번호', value: 'no', width: '15%', key: true },
					{ text: '제목', value: 'title', width: '40%' },
					{ text: '작성자', value: 'writer', width: '30%' },
					{ text: 'Actions', value: 'actions', width: '15%', sortable: false },
				],
				dateGubun: '/',
				gridNm: '시스템 공지사항',
				path: '/noticeSystem',
				isCheckBox: true,
				isSingleSelect: false,
				gridDense: false,
				isDetail: true,
				rowClick: (row, gridNm) => {
					this.rowClick(row, gridNm);
				}, //로우 클릭 이벤트 콜백
			},
			isPopup: false,
			currentRow: {},
		};
	},
	mounted() {
		this.findSystemNoticeList();
	},
	computed: {
		...mapGetters({ getSystemNoticeList: 'noticeStore/getSystemNoticeList' }),
	},
	methods: {
		async findSystemNoticeList() {
			await this.$store.dispatch('noticeStore/findSystemNotice').catch(error => {
				console.log('===============> noticeStore/findSystemNotice error');
				console.log(error);
			});

			if (this.getSystemNoticeList) {
				this.noticeSystemInfo.dataList = this.getSystemNoticeList;
			} else {
				console.log('this.getSystemNoticeList 실패 !!');
			}
		},
		rowClick(row, gridNm) {
			console.log('this.currentRow -----> ');
			this.currentRow = row.item;
			this.currentRow.gridNm = gridNm;
			console.log(this.currentRow);
			this.isPopup = true;
		},
		dialogCallback(dialogObj) {
			console.log('dialogObj.msg = ' + dialogObj.msg);
			this.isPopup = false;
		},
	},
};
</script>
