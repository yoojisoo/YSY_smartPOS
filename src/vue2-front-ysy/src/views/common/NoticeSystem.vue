<!-- main 화면 -->
<template>
	<v-container class="main_layout_container">
		<v-row class="main_layout_row">
			<v-col cols="12" md="8" lg="8" xl="8">
				<v-row justify="center" no-gutters style="height: inherit">
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
</template>

<script>
import { TheSystemBar, TheHeader, TheFooter } from '@/assets/util/importFile.js';
import ysyGrid from '@/components/FGrid.vue';
import { mapGetters } from 'vuex';
import boardDialog from '@/components/FDialog.vue';

export default {
	components: {
		TheSystemBar,
		TheHeader,
		TheFooter,
		ysyGrid,
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
				useBtn: true,
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

			if (this.getSystemNoticeList.length > 0) {
				this.noticeSystemInfo.dataList = this.getSystemNoticeList;
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
