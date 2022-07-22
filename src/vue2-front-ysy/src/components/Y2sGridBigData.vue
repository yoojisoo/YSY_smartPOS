
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



disable-sort 
v-scroll:#virtual-scroll-table="onScroll"
id="virtual-scroll-table"

fixed-header 
class="flex-table" 
id="virtual-scroll-table"
v-scroll:#virtual-scroll-table="onScroll"


<div id="app">
		<v-app class="app">
			<section class="view">

				<v-card class="table-container">
					<v-data-table 
						
						:headers="headers" 
						:items="dataListLimited" 
						
						hide-default-footer
						disable-pagination 
						fixed-header 
						dense 
						item-key="col1"
						max-height= "400px"
                        overflow= "auto"
id="virtual-scroll-table"
v-scroll:#virtual-scroll-table="onScroll"
						


						 />

					<template
						v-if="start > 0"
						v-slot:body.prepend
					>
						<tr>
						<td
							:colspan="headers.length"
							:style="'padding-top:'+startHeight+'px'"
						>
						</td>
						</tr>
					</template>
					<template
						v-if="start + perPage < this.dataList.length"
						v-slot:body.append
					>
						<tr>
						<td
							:colspan="headers.length"
							:style="'padding-top:'+endHeight+'px'"
						>
						</td>
						</tr>
					</template>


				</v-card>
			</section>
		</v-app>

-->
<template>
	<div id="app">
		<v-app >
			<v-data-table id="virtual-scroll-table" 
				v-scroll:#virtual-scroll-table="onScroll" 
				dense 
				:headers="headers"
				:items="dataListLimited" 
				item-key="col1" 
				disable-pagination 
				hide-default-footer 
				fixed-header
				>

				<template v-if="start > 0" v-slot:body.prepend="{headers}">
					<tr>
						<td :colspan="headers.length" :style="'padding-top:'+startHeight+'px'">
						</td>
					</tr>
				</template>
				<template v-if="start + perPage < this.dataList.length" v-slot:body.append="{headers}">
					<tr>
						<td :colspan="headers.length" :style="'padding-top:'+endHeight+'px'">
						</td>
					</tr>
				</template>
			</v-data-table>
		</v-app>
	</div>


</template>

<script>



export default {
	props: ['headers', "dataList"],
	data: () => ({
		start: 0,
		timeout: null,
		rowHeight: 24,
		perPage: 25,
	}),

	mounted() {
    	// document.addEventListener('wheel', this.onScroll);
  	},
	methods: {
		onScroll(e) {
			// debounce if scrolling fast
			this.timeout && clearTimeout(this.timeout);
			this.timeout = setTimeout(() => {
				 const { scrollTop } = e.target;
				
				const rows = Math.ceil(scrollTop / this.rowHeight);
				
				this.start = rows + this.perPage > this.dataList.length ? this.dataList.length - this.perPage : rows;

				this.$nextTick(() => {
					e.target.scrollTop = scrollTop;
				});
			}, 20);
		}
	},

	computed: {
		dataListLimited() {
			return this.dataList.slice(this.start, this.perPage + this.start);
		},
		startHeight() {
			return this.start * this.rowHeight - 32;
		},
		endHeight() {
			return this.rowHeight * (this.dataList.length - this.start);
		},
	},
	

	
};





</script>

<style>
.v-data-table /deep/ .sticky-header {
  position: sticky;
  top: 56;
}
.v-data-table /deep/ .v-data-table__wrapper {
  overflow: unset;
}
#virtual-scroll-table {
  max-height: 400px;
  overflow: auto;
}



</style>
<!-- 
.app {
  background: lightgrey !important;
  height: 100vh;
  overflow: hidden;
}

.view {
  display: flex;
  flex-direction: column;
  height: 100%;
  padding: 20px;
}

.card-top {
  padding: 20px;
}

.table-container {
  display: flex;
  margin-top: 20px;
  flex-grow: 1;
  overflow: hidden;
}

.flex-table {
  display: flex;
  flex-grow: 1;
  width: 100%;
}

.flex-table > div {
  width: 100%;
}

 -->