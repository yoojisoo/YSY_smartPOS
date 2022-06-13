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
								<ysyGrid :gridInfo="sysNoticeInfo" />
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
			pageName: 'systemNotice',
			pageNameKo: '시스템 공지사항',
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
				gridDense: false,
				isDetail: true,
				rowClick: (row, gridNm) => {
					this.rowClick(row, gridNm);
				}, //로우 클릭 이벤트 콜백
			},
		};
	},
	methods: {
		async setSystemNoticeList() {
			await this.$store.dispatch('setUserList');
			if (this.systemNoticeList) {
				this.sysNoticeInfo.dataList = this.systemNoticeList;
			}
		},
	},
	computed: {
		systemNoticeList() {
			return this.$store.state.userStore.userList;
		},
	},
	mounted() {
		this.setSystemNoticeList();
	},
	created() {},
};
</script>
