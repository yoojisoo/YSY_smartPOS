<!--
    xs=600이하 
    sm=600 - 960
    md=960이상

    outlined rounded text	동그란 라인
                            plain 배경없앰.
-->
<template>
	<!--<v-btn @click="update">update</v-btn>-->
	<v-container class="main_layout_container">
		<v-row class="main_layout_row">
			<v-col class="main_layout_col" cols="12" md="10" lg="10" xl="10">
				<v-row justify="start" no-gutters style="height: inherit">
					<v-col cols="12">
						<v-breadcrumbs :items="breadCrumbsInfo">
							<template v-slot:divider>
								<v-icon>mdi-chevron-right</v-icon>
							</template>
						</v-breadcrumbs>
					</v-col>
					<v-col
						v-for="(card, idx) in cardList"
						:key="idx"
						:cols="grid_layout.default"
						:sm="grid_layout.tablet"
						:md="grid_layout.pc"
						class="mt-0 pa-0"
						:class="info && info.bottomMargin ? info.bottomMargin : bottomMargin"
						style="text-align: -webkit-center"
					>
						<BaseCardImg :info="card" />
					</v-col>
				</v-row>
				<v-row no-gutters style="height: inherit">
					<v-col class="main_layout_col" cols="12">
						<BasePagination :info="paginationInfo" />
					</v-col>
				</v-row>
			</v-col>
		</v-row>

		<!-- 페이징 정보 -->
	</v-container>
</template>

<script>
import { BaseCardImg, BasePagination, CommonService, YsyUtil } from '@/assets/import/index.js';

export default {
	props: ['info', 'parentPageName'],
	components: {
		BaseCardImg,
		BasePagination,
	},
	data() {
		return {
			dataCntInPage: 3, // 한 페이지에 표시 될 데이터 수
			paginationInfo: {
				pageCnt: 0, // 총 페이지 수 ⭐
				//totalVisible: 5, // 페이지 버튼 표시 사이즈
				//useCircle: true, // 페이지 버튼 circle로
				updatePage: page => {
					// 페이지 넘어갈때 실행될 함수
					this.updatePage(page);
				},
			},
			grid_layout: {
				default: 12,
				tablet: 6,
				pc: 4,
			},
			default_img: 'https://cdn.vuetifyjs.com/images/cards/cooking.png',
			selection: 1,
			bottomMargin: 'mb-3',
			dataList: [], // 초기에 조회된 모든 카드 데이터 리스트
			cardList: [], // 페이징 처리 된 카드 데이터 리스트
			breadCrumbsInfo: [{ text: this.parentPageName }, { text: 'Admin 자유게시판' }],
		};
	},
	mounted() {
		this.getDataList();
	},
	methods: {
		async getDataList() {
			const params = {};
			let { data } = await CommonService.fn_getDataList('/ysy/v1/admin/getYsyBoardList', params);
			this.dataList = data.objList;
			console.log('admin board free veiw getDataList ', this.dataList);

			this.paging(); // 데이터 로딩 후 페이징 처리 !
			this.initPage(); // 초기 데이터를 불러온 후 첫페이지 셋팅
		},

		async update() {
			let updateData = this.dataList.filter(x => x.boardId == 1)[0];
			updateData.title = '변경된 title 입니다.';
			console.log('this.dataList', this.dataList);
			console.log('updateData', updateData);
			let formData = YsyUtil.createFormData(updateData);
			let res = await CommonService.fn_save('/ysy/v1/admin/modifyYsyBoard', formData);
			console.log('admin board free veiw update ', res);
		},

		/** Card Data 로딩이 완료되고, 페이징 처리하는 함수 */
		paging() {
			let res;

			if (this.dataList.length === 0) res = 0;
			else res = Math.ceil(this.dataList.length / this.dataCntInPage);

			this.paginationInfo.pageCnt = res;
		},

		initPage() {
			if (this.dataList.length < this.dataCntInPage) {
				// 한 페이지에 보여줄 카드 수가 전체 카드 수 초과 (그대로 씀)
				this.cardList = this.dataList;
			} else {
				// 한 페이지에 보여줄 카드 수가 전체 카드 수 이하 (사이즈만큼 잘라서 씀)
				this.cardList = this.dataList.slice(0, this.dataCntInPage);
			}
		},

		updatePage(page) {
			// Pagination을 눌렀을때 해당 페이지의 데이터를 보관
			let start = (page - 1) * this.dataCntInPage;
			let end = page * this.dataCntInPage;
			this.cardList = this.dataList.slice(start, end);
		},

		cardClick() {
			console.log('click');
		},

		detailMove(type) {
			console.log('detailMove click = ' + type);
		},

		onScroll() {
			this.scrollInvoked++;
			console.log(this.scrollInvoked);
		},
	},
};
</script>

<style>
.cursor_finger {
	cursor: pointer;
}
</style>
