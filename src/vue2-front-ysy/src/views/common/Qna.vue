<!-- main 화면 -->
<template>
	<v-container class="main_layout_container">
		<v-row class="main_layout_row">
			<v-col class="main_layout_col" cols="12" md="10" lg="10" xl="10">
				<v-row justify="center" no-gutters style="height: inherit">
					<v-col cols="12">
						<v-breadcrumbs :items="breadCrumbsInfo">
							<template v-slot:divider>
								<v-icon>mdi-chevron-right</v-icon>
							</template>
						</v-breadcrumbs>
					</v-col>
					<v-col cols="12">
						<!-- <ysyGrid :gridInfo="qnaInfo" /> -->
						<v-card elevation="0">
							<v-card-title>
								Q&A
								<v-spacer />
								<v-btn depressed color="primary" @click="openCreate('qna')">글쓰기</v-btn>
							</v-card-title>
							<v-data-table :headers="qnaInfo.headers" hide-default-footer></v-data-table>
						</v-card>
					</v-col>
				</v-row>
			</v-col>
		</v-row>
		<!--<template v-if="isPopup">
			<boardDialog :boardObj="currentRow" :callback="dialogCallback" />
		</template>-->
	</v-container>
</template>

<script>
// import ysyGrid from '@/components/FGrid.vue';
import boardDialog from '@/components/FDialog.vue';

export default {
	components: {
		// ysyGrid,
		boardDialog,
	},
	props: ['parentPageName'],
	data() {
		return {
			pageName: 'qna',
			pageNameKo: 'QnA',
			qnaInfo: {
				dataList: [],
				headers: [
					{ text: '번호', value: 'no', width: '15%', key: true },
					{ text: '제목', value: 'title', width: '40%' },
					{ text: '작성자', value: 'writer', width: '30%' },
					{ text: 'Actions', value: 'actions', width: '15%', sortable: false },
				],
				dateGubun: '/',
				gridNm: 'QnA',
				path: '/qna',
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
			breadCrumbsInfo: [{ text: this.parentPageName }, { text: 'QnA' }],
		};
	},
	mounted() {
		this.findQnAList();
	},
	computed: {
		getQnAList() {
			return this.$store.state.communityStore.qnaList;
		},
	},
	methods: {
		async findQnAList() {
			await this.$store.dispatch('communityStore/findQnA').catch(error => {
				console.log('===============> communityStore/findQnA error');
				console.log(error);
			});

			if (this.getQnAList.length > 0) {
				this.qnaInfo.dataList = this.getQnAList;
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
		openCreate(type) {
			if (type == 'qna') {
				this.$router.push({ path: '/newArticles' });
			}
		},
	},
};
</script>
