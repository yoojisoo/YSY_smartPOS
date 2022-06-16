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
import { mapActions, mapGetters } from 'vuex';

//const noticeStore = 'noticeStore';

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
			pageName: 'systemNotice',
			pageNameKo: '시스템 공지사항',
			systemNoticeInfo: {
				dataList: [],
				headers: [
					{ text: '번호', value: 'boardId', width: '20%', key: true },
					{ text: '제목', value: 'title', width: '40%' },
					{ text: '작성자', value: 'ysyUserMst.username', width: '40%' },
				],
				dateGubun: '/',
				gridNm: '시스템 공지사항',
				path: '/systemNotice',
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
		this.findSystemNoticeList();
	},
	methods: {
		//...mapActions(noticeStore, ['findSystemNoticeInfo']),
		async findSystemNoticeList() {
			await this.$store.dispatch('findSystemNoticeInfo');
			if (this.getNoticeList) {
				this.systemNoticeInfo.dataList = this.getNoticeList;
			}
		},
	},
	computed: {
		...mapGetters({ getNoticeList: 'noticeStore/getNoticeList' }),
	},
};
</script>
