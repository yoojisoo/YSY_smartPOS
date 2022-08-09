<!--
    xs=600이하 
    sm=600 - 960
    md=960이상

    outlined rounded text	동그란 라인
                            plain 배경없앰.
-->
<template>
	<v-app>
		<v-btn @click="update">update</v-btn>
		<v-container fluid class="main_layout_container">
			<!-- custom card grid -->
			<v-row no_gutters class="mx-1 pa-0 mt-1">
				<v-col
					v-for="(data, idx) in cardList"
					:key="idx"
					:cols="grid_layout.default"
					:sm="grid_layout.tablet"
					:md="grid_layout.pc"
					class="mt-0 pa-0"
					:class="info && info.bottomMargin ? info.bottomMargin : bottomMargin"
				>
					<BaseCardImg :info="data" />
				</v-col>
			</v-row>
			
			<!-- 페이징 정보 -->
			<v-row no_gutters>
				<v-col cols="12" style="text-align: center">
					<BasePagination :info="paginationInfo" />
				</v-col>
			</v-row>
		</v-container>
		
	</v-app>
</template>

<script>
import { BaseCardImg, BasePagination , CommonService , YsyUtil } from '@/assets/util/importFile.js';

// import CommonService from '@/service/CommonService';

export default {
	props: ['info'],
	components: {
		BaseCardImg,
		BasePagination,
	},
	data() {
		return {
			cardCntInPage: 6, // 한 페이지에 표시 될 카드 수
			cardCntInAll: 0, // 전체 카드 수
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
		};
	},
	mounted() {
		this.getDataList();
		
		// this.dataInit();
		// this.initPage(); // 초기 데이터를 불러온 후 첫페이지 셋팅
		// this.paginationInfo.pageCnt = this.pages();
	},
	methods: {
		async getDataList() { 
			const params = {};
			let {data} = await CommonService.fn_getDataList('/ysy/v1/admin/getYsyBoardList', params);
			this.dataList = data.objList;
			console.log('admin board free veiw getDataList ', this.dataList);
		},
		async update(){
			let updateData = this.dataList.filter(x=>x.boardId == 1)[0];
			updateData.title = "변경된 title 입니다.";
			console.log("this.dataList",this.dataList);
			console.log("updateData",updateData);
			let formData = YsyUtil.createFormData(updateData );
			let res = await CommonService.fn_save('/ysy/v1/admin/modifyYsyBoard', formData);
			console.log('admin board free veiw update ', res);
			
		},
		dataInit() {
			for (var i = 0; i < 100; i++) {
				var json = {
					title: 'title' + i,
					content: 'content' + i,
					writer: 'writer' + i,
					count: ' 20',
					commentCnt: '3',
					contentList: [
						{ title: '상품명', text: '상품' + i },
						{ title: '재고 수량', text: i },
						{ title: '판매 수량', text: i + 1 },
					],
					// isAction : false,
					isAction: true,
					viewCnt: 1,
				};
				if (i == 0) {
					json.isOwner = true;
					json.content = '1234567890123456789012345678901234567890';
				}
				this.dataList.push(json);
			}
		},
		pages() {
			if (this.dataList.length === 0) return 0;
			else return Math.ceil(this.dataList.length / this.cardCntInPage);
		},
		initPage() {
			// 처음에 dataInit에서 조회해온 데이터들을 사이즈에 맞게 잘라서 보관
			this.cardCntInAll = this.dataList.length;
			if (this.cardCntInAll < this.cardCntInPage) {
				// 한 페이지에 보여줄 카드 수가 전체 카드 수 초과 (그대로 씀)
				this.cardList = this.dataList;
			} else {
				// 한 페이지에 보여줄 카드 수가 전체 카드 수 이하 (사이즈만큼 잘라서 씀)
				this.cardList = this.dataList.slice(0, this.cardCntInPage);
			}
		},
		updatePage(page) {
			// Pagination을 눌렀을때 해당 페이지의 데이터를 보관
			let start = (page - 1) * this.cardCntInPage;
			let end = page * this.cardCntInPage;
			this.cardList = this.dataList.slice(start, end);
		},
		cardClick() {
			console.log('click');
		},
		test() {
			console.log('test click');
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
