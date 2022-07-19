
<!-- create by clubbboy@naver.com 2022 07 18
	** Y2sGrid props 구조 및 사용법 **
	< vuetify 기반 grid를 Customizing하여 사용>

	props -> gridDataList        : array(json data) - body data 정의
		  -> gridInfo            : 해당 모듈을 사용하는 페이지에서 "props"이름을 "gridInfo"로 넘겨줌.
	     

	      -> gridInfo.headers             : array(json data) - header 컬럼 정의
		  -> gridInfo.isFiltering         : boolean - 필터링 할 텍스트 필드 정보 생성 유무
		  -> gridInfo.rowCnt              : int  - 한 패이지당 row 표현 갯수
		  -> gridInfo.page                : int  - loading시 나타날 페이지 넘버
		  -> gridInfo.isCheckBox          : bool - 첫번째 column에 checkbox 사용유무
		  -> gridInfo.isSingleSelect      : bool - true일시 전체선택 없음.
		  -> gridInfo.hide-default-header : bool - 헤더 사용유무
		  -> gridInfo.ide-default-footer  : bool  - footer 사용유무(현재 페이지 위치 및 전체 페이지 정보)
		  -> gridInfo.gridKey             : String - 해당 그리드의 고유 키값 (정렬 삽입 등에 사용?)
		  -> gridInfo.rowClick            : function - 로우 클릭시 받을 함수 명(click된 row정보와 grid명 파라메터로 넘겨줌)
		  -> gridInfo.rowDBClick          : function - 로우 클릭시 받을 함수 명(click된 row정보와 grid명 파라메터로 넘겨줌)

		  -> colEditList : Array(json)  { isEdit:true , obj : textBox} ,{ isEdit:true , obj : Btn , action:rowBtnClick(row , gridNm)}

-->
<template>
	<v-container fluid pa-0 ma-0 mt-3>
		<v-card class="mb-3 pa-0" outlined tile>
			<v-card-title>
      
    </v-card-title>


			<v-data-table
				:dense = "gridInfo.dense== undefined? true:gridInfo.dense"
				v-model="selected"
				:headers="gridInfo.headers"
				:items="gridDataList"
				:items-per-page="gridInfo.rowCnt == undefined? 5: gridInfo.rowCnt"
				:show-select="gridInfo.isCheckBox"
				:single-select="gridInfo.isSingleSelect"
				:hide-default-header="false"
				:hide-default-footer = "gridInfo.hide_default_footer"
				:item-key="gridKey"
				@page-count="pageCount = $event"
				@click:row="rowClick"
				@dblclick:row="rowDbClick"
				:search="filteringTxt"
			>
				<template v-slot:top>
					<v-toolbar flat dense>
						<v-toolbar-title>{{ gridInfo.gridNm }}</v-toolbar-title>
						<v-divider class="mx-4" inset vertical />
						<v-spacer />

							
							<v-btn v-if="gridInfo.searchBtnClick" @click="searchBtnClick">
								{{gridInfo.searchBtnNm==undefined?"조회":gridInfo.searchBtnNm}}
							</v-btn>
							<v-btn v-if="gridInfo.addBtnClick" @click="addBtnClick">
								{{gridInfo.addBtnNm==undefined?"추가":gridInfo.addBtnNm}}
							</v-btn>
							<v-btn v-if="gridInfo.delBtnClick" @click="delBtnClick">
								{{gridInfo.delBtnNm==undefined?"삭제":gridInfo.delBtnNm}}
							</v-btn>
							<v-btn v-if="gridInfo.moreBtnClick" @click="moreBtnClick">
								{{gridInfo.moreBtnNm==undefined?"전체보기":gridInfo.moreBtnNm}}
							</v-btn>
							<v-btn v-if="gridInfo.excelDownClick" @click="excelDownClick">
								{{gridInfo.excelDownNm==undefined?"Excel Down":gridInfo.excelDownNm}}
							</v-btn>

							<v-card v-if="gridInfo.excelUploadClick">
								<v-file-input
									:label="gridInfo.excelUploadNm==undefined?'Excel Upload':gridInfo.excelUploadNm"
									accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
									small-chips
									show-size
									outlined
									dense
									prepend-icon="mdi-paperclip"
									@change="excelUploadClick"
									/>
							</v-card>

							<v-text-field v-if="gridInfo.isFiltering"
								v-model="filteringTxt"
								append-icon="mdi-magnify"
								label="Grid Filtering"
								single-line
								hide-details
							></v-text-field>

					</v-toolbar>
				</template>
			</v-data-table>
		</v-card>
	</v-container>
</template>

<script>
import GridResize from '@/assets/util/GridResize.js'

var tables = document.getElementsByTagName('table');
export default {
	props: ['gridInfo', "gridDataList"],
	data: () => ({
		selected: [],
		currentIndex : -1,
		filteringTxt:"", 
	}),
	methods: {
		/** grid click event */
		rowClick(event, info) {
			
			this.currentIndex =info.index;

			if(this.gridInfo.rowClick)
				this.gridInfo.rowClick(event, info , this.gridInfo.gridNm);
		},
		rowDbClick(event, info) {
			if(this.gridInfo.rowDbClick)
				this.gridInfo.rowDbClick(event, info, this.gridInfo.gridNm);
		},
		//조회버튼 클릭
		searchBtnClick(){
			if(this.gridInfo.searchBtnClick)
				this.gridInfo.searchBtnClick(this.gridInfo.gridNm);
		},

		//추가 버튼 클릭 이벤트
		addBtnClick(){
			if(this.gridInfo.addBtnClick)
				this.gridInfo.addBtnClick(this.gridInfo.gridNm , this.currentIndex);
		},
		//삭제 버튼 클릭 이벤트
		delBtnClick(){
			if(this.gridInfo.delBtnClick)
				this.gridInfo.delBtnClick(this.gridInfo.gridNm , this.currentIndex);
		},
		//더보기 버튼 클릭
		moreBtnClick (){
			if(this.gridInfo.moreBtnClick)
				this.gridInfo.moreBtnClick(this.gridInfo.gridNm);
		},
		
		//excel down 버튼 클릭
		excelDownClick (){
			if(this.gridInfo.excelDownClick)
				this.gridInfo.excelDownClick(this.gridInfo.gridNm);
		},
		excelUploadClick (file){
			if(this.gridInfo.excelUploadClick)
				this.gridInfo.excelUploadClick(this.gridInfo.gridNm , file);
		},

	},
	computed: {
		gridKey() {
			let key = '';
			this.gridInfo.headers.forEach(x => {
				if (x.key != undefined && x.key !== '') {
					key = x.value;
				}
			});
			console.log('key = ' + key);
			return key;
		},
	},
	created(){
		console.log("tables=================");
		console.log(tables);
		// GridResize.resizableGrid(tables);
	}
};
</script>
