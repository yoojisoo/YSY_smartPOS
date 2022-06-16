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
								<ysyGrid :gridInfo="storeNoticeInfo" />
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
import mainFooter from '@/components/TheFooter.vue';
import ysyGrid from '@/components/YsyGrid.vue';
import pageHistory from '@/components/PageHistory.vue';

export default {
	components: {
		mainSystemBar,
		mainHeader,
		mainFooter,
		ysyGrid,
		pageHistory,
	},
	data() {
		return {
			pageName: 'storeNotice',
			pageNameKo: '스토어 공지사항',
			storeNoticeInfo: {
				dataList: [],
				headers: [
					{ text: '번호', value: 'boardId', width: '20%', key: true },
					{ text: '제목', value: 'title', width: '40%' },
					{ text: '작성자', value: 'ysyUserMst.username', width: '40%' },
				],
				dateGubun: '/',
				gridNm: '스토어 공지사항',
				path: '/storeNotice',
				isCheckBox: true,
				isSingleSelect: false,
				gridDense: false,
				isDetail: true,
				rowClick: (row, gridNm) => {
					this.rowClick(row, gridNm);
				}, //로우 클릭 이벤트 콜백
			},
		};
	},
	mounted() {
		this.setStoreNoticeList();
	},
	methods: {
		async setStoreNoticeList() {
			await this.$store.dispatch('findStoreNoticeInfo');
			if (this.storeNoticeList) {
				this.storeNoticeInfo.dataList = this.storeNoticeList;
			}
		},
	},
	computed: {
		storeNoticeList() {
			return this.$store.state.noticeStore.noticeList;
		},
	},
};
</script>
