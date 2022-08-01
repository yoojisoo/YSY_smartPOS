
<!-- create by clubbboy@naver.com 2022 07 18
	** Y2sGrid props 구조 및 사용법 **
	< vuetify 기반 grid를 Customizing하여 사용>

	props -> gridDataList        : array(json data) - body data 정의
		  -> gridInfo            : 해당 모듈을 사용하는 페이지에서 "props"이름을 "gridInfo"로 넘겨줌.
	     
		  🔅 속성
	      -> gridInfo.headers             : array(json data) - header 컬럼 정의
		  -> gridInfo.page.sync           : int  - 페이지 네이션과의 싱크(나타나는 최초 페이지)
		  -> gridInfo.gridNm			  : String - 부모 컴포넌트 이름
		  -> gridInfo.rowCnt              : int  - 한 패이지당 row 표현 갯수
		  -> gridInfo.gridKey             : String - 해당 그리드의 고유 키값 (정렬 삽입 등에 사용?)
		  -> gridInfo.isBtnGrp            : bool  - 페이지네이션의과의 싱크(나타나는 최초 페이지)
		  -> gridInfo.isResize            : bool  - 컬럼 마우스 드래그로 리사이즈 (정렬과 동시사용 불가)
		  -> gridInfo.isNotSort           : bool  - 정렬 비활성화
		  -> gridInfo.isUseBody           : bool  - 엑셀 업로드 content
		  -> gridInfo.gridDense           : bool  - row 폭 좁게
		  -> gridInfo.isUseHeader         : bool  - 엑셀 업로드 변환 헤더
		  -> gridInfo.isCheckBox          : bool - 첫번째 column에 checkbox 사용유무
		  -> gridInfo.isFiltering         : boolean - 필터링 할 텍스트 필드 정보 생성 유무
		  -> gridInfo.isSingleSelect      : bool - true일시 전체선택 없음.
		  -> gridInfo.mobile-breakpoint   : String | int - 모바일 버전으로 언제 보여질지
		  -> gridInfo.hide_default_header : bool - 테이블의 디폴트 헤더 사용유무
		  -> gridInfo.hide_default_footer : bool  - 테이블의 디폴트 푸터 사용유무(현재 페이지 위치 및 전체 페이지 정보)

		  🔅 함수
		  -> gridInfo.rowClick            : function - 로우 클릭시 받을 함수 명(click된 row정보와 grid명 파라메터로 넘겨줌)
		  -> gridInfo.rowDBClick          : function - 로우 클릭시 받을 함수 명(click된 row정보와 grid명 파라메터로 넘겨줌)
		  -> colEditList : Array(json)  { isEdit:true , obj : textBox} ,{ isEdit:true , obj : Btn , action:rowBtnClick(row , gridNm)}
-->
<template>
	<v-container fluid pa-0 ma-0 style="background-color: lightcoral; height: 100%;">
		<v-card class="mb-3 pa-0" outlined tile height="100%">
			<v-card-title>

			</v-card-title>

			<v-data-table 
				:dense="gridInfo.dense== undefined? true:gridInfo.dense" 
				v-model="selected"
				:headers="gridInfo.headers" 
				:items="gridDataList"
				:items-per-page="gridInfo.rowCnt == undefined? 5: gridInfo.rowCnt"
				
				:search="filteringTxt"
				:loading="false"
    			loading-text="Loading... Please wait"
				@page-count="pageCount = $event"
				:show-select="gridInfo.isCheckBox"
				:single-select="gridInfo.isSingleSelect" 
				:hide-default-header="false"
				:hide-default-footer="gridInfo.hide_default_footer" 
				:item-key="gridKey"
				:disable-sort="gridInfo.isNotSort==undefined?true:gridInfo.isNotSort" 
				multi-sort
				@click:row="fn_rowClick"
				@dblclick:row="fn_rowDbClick"
				
				:disable-pagination="true"
				fixed-header
				height="50vh"
				
				mobile-breakpoint="xs"
				>

				<template v-slot:top>
					<v-toolbar flat dense class="ma-0 pa-0">
						<v-toolbar-title>{{ gridInfo.gridNm }}</v-toolbar-title>
						<v-divider class="mx-4" inset vertical />
						<v-spacer />
						<div style="width: 250;">
							<v-text-field
								v-if="gridInfo.isFiltering"
								v-model="filteringTxt"
								append-icon="mdi-magnify"
								label="Grid Filtering"
								single-line hide-details
							></v-text-field>
						</div>
						<span>
							<v-btn outlined class="ml-3 mr-1" v-if="gridInfo.searchBtnClick" @click="fn_searchBtnClick">
								{{gridInfo.searchBtnNm==undefined?"조회":gridInfo.searchBtnNm}}
							</v-btn>
							<v-btn outlined class="mr-1" v-if="gridInfo.addBtnClick" @click="fn_addBtnClick">
								{{gridInfo.addBtnNm==undefined?"추가":gridInfo.addBtnNm}}
							</v-btn>
							<v-btn outlined class="mr-1" v-if="gridInfo.delBtnClick" @click="fn_delBtnClick">
								{{gridInfo.delBtnNm==undefined?"삭제":gridInfo.delBtnNm}}
							</v-btn>
							<v-btn outlined class="mr-1" v-if="gridInfo.moreBtnClick" @click="fn_moreBtnClick">
								{{gridInfo.moreBtnNm==undefined?"전체보기":gridInfo.moreBtnNm}}
							</v-btn>
							<v-btn outlined v-if="gridInfo.excelDownClick" @click="fn_excelDownClick">
								{{gridInfo.excelDownNm==undefined?"Excel Down":gridInfo.excelDownNm}}
							</v-btn>
						</span>
						
					</v-toolbar>
				</template>

	<!-- v-if="gridInfo.isCustomHeader" -->
				<template v-if="gridInfo.isUseHeader"
					v-slot:header="{ props: { headers } }"
				>
					<thead>
					<tr id='tableheaders' >
						<template v-for="header in headers" >
						<th :key="header.text">
							{{ header.text }}
						</th>
						</template>
					</tr>
					</thead>
				</template>

				<template  v-if="gridInfo.isUseBody" v-slot:body="{ items }">
					<tr v-for="item in items" :key="item.name">
					<td v-for="header in gridInfo.headers" :key="header.text" >
						{{ item[header.value]}} 
					</td>
					</tr>
				</template>
				<!-- <template v-slot:items="{ item }">
					<td v-for="header in gridInfo.headers" :key="header.value" style="background-color: red;">
						{{ item[header.value] }}
					</td>
				</template> -->
			</v-data-table>
			<template>
				<v-file-input
					v-if="gridInfo.excelUploadClick"
					class="ma-o pa-0"
					small-chips show-size outlined dense hide-details
					prepend-icon="mdi-paperclip"
					:label="gridInfo.excelUploadNm==undefined?'Excel Upload':gridInfo.excelUploadNm"
					accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
					@change="fn_excelUploadClick"
				/>
			</template>
			<!-- 페이지 네이션-->
			<!-- <template v-if="gridInfo.page">
				<v-pagination v-model="gridInfo.page" :length="pageCount" :total-visible="5" circle />
			</template> -->
		</v-card>
	</v-container>
</template>

<script>


var tables = document.getElementsByTagName('table');

function setNewPos(parentNode, oldIndex, oldNode, newIndex, newNode) {
  setTimeout(() => {
    if (oldIndex < newIndex) {
      parentNode.insertBefore(oldNode, newNode.nextSibling);
    } else {
      parentNode.insertBefore(oldNode, newNode);
    }
  }, 150);
}

// Add back the sortHandle class if it gets stripped away by external code
function watchClass(targetNode, classToWatch) {
  let lastClassState = targetNode.classList.contains(classToWatch);
  const observer = new MutationObserver((mutationsList) => {
    for (let i = 0; i < mutationsList.length; i++) {
      const mutation = mutationsList[i];
      if (mutation.type === 'attributes' && mutation.attributeName === 'class') {
        const currentClassState = mutation.target.classList.contains(classToWatch);
        if (lastClassState !== currentClassState) {
          lastClassState = currentClassState;
          if (!currentClassState) {
            mutation.target.classList.add('sortHandle');
          }
        }
      }
    }
  });
  observer.observe(targetNode, { attributes: true });
}

function sortTableRows(el) {
  const options = {
    handle: '.sortHandle',
    animation: 150,
    onStart: (evt) => {
      // Flag all cells in the column being dragged by adding a class to them
      el.querySelectorAll('tr').forEach((row) => {
        const draggedTd = row.querySelectorAll('td')[evt.oldIndex];
        if (draggedTd) {
          draggedTd.classList.add('sorting');
        }
      });
    },
    onEnd: (evt) => {
      // Take the dragged cells and move them over to the new column location
      el.querySelectorAll('tr').forEach((row) => {
        if (!row.querySelector('th')) {
          const oldNode = row.querySelector('.sorting');
          const newNode = row.querySelectorAll('td')[evt.newIndex];
          setNewPos(row, evt.oldIndex, oldNode, evt.newIndex, newNode);
          oldNode.classList.remove('sorting');
        }
      });
    },
  };
  const initEl = el.getElementsByTagName('thead')[0].getElementsByTagName('tr')[0];
  return Sortable.create(initEl, options);
}




export default {
	props: ['gridInfo', "gridDataList"],
	data: () => ({
		pageCount: 0,
		selected: [],
		currentIndex : -1,
		filteringTxt:"", 
	}),
	methods: {
		/** grid click event */
		//row 클릭
		fn_rowClick(event, info) {
			this.currentIndex = info.index;
			if(this.gridInfo.rowClick)
				this.gridInfo.rowClick(event, info , this.gridInfo.gridNm);
		},
		//row 더블클릭
		fn_rowDbClick(event, info) {
			if(this.gridInfo.rowDbClick)
				this.gridInfo.rowDbClick(event, info, this.gridInfo.gridNm);
		},
		//조회버튼 클릭
		fn_searchBtnClick(){
			if(this.gridInfo.searchBtnClick)
				this.gridInfo.searchBtnClick(this.gridInfo.gridNm);
		},
		//추가 버튼 클릭 이벤트
		fn_addBtnClick(){
			if(this.gridInfo.addBtnClick)
				this.gridInfo.addBtnClick(this.gridInfo.gridNm , this.currentIndex);
		},
		//삭제 버튼 클릭 이벤트
		fn_delBtnClick(){
			if(this.gridInfo.delBtnClick)
				this.gridInfo.delBtnClick(this.gridInfo.gridNm , this.currentIndex);
		},
		//더보기 버튼 클릭
		fn_moreBtnClick (){
			if(this.gridInfo.moreBtnClick)
				this.gridInfo.moreBtnClick(this.gridInfo.gridNm);
		},
		
		//excel down 버튼 클릭
		fn_excelDownClick (){
			if(this.gridInfo.excelDownClick)
				this.gridInfo.excelDownClick(this.gridInfo.gridNm);
		},
		fn_excelUploadClick (file){
			if(this.gridInfo.excelUploadClick)
				this.gridInfo.excelUploadClick(this.gridInfo.gridNm , file);
		},

		//컬럼 마우스 드레그 리사이즈 함수
		resizableGrid(table){
			console.log("1.resizableGrid start=================================");
			var row = table.getElementsByTagName('tr')[0],
				cols = row ? row.children : undefined;
			if (!cols) return;

			console.log("2.resizableGrid start=================================");

			table.style.overflow = 'hidden';

			var tableHeight = table.offsetHeight;

			for (var i = 0; i < cols.length; i++) {
				var div = createDiv(tableHeight);
				cols[i].appendChild(div);
				cols[i].style.position = 'relative';
				setListeners(div);
			}

			function setListeners(div) {

				console.log("1.setListeners start=================================");
				var pageX, curCol, nxtCol, curColWidth, nxtColWidth;

				div.addEventListener('mousedown', function (e) {
					curCol = e.target.parentElement;
					nxtCol = curCol.nextElementSibling;
					pageX = e.pageX;

					var padding = paddingDiff(curCol);

					curColWidth = curCol.offsetWidth - padding;
					if (nxtCol)
						nxtColWidth = nxtCol.offsetWidth - padding;
				});

				div.addEventListener('mouseover', function (e) {
					e.target.style.borderRight = '';//2px solid #0000ff
				})

				div.addEventListener('mouseout', function (e) { 
					e.target.style.borderRight = '';
				})

				document.addEventListener('mousemove', function (e) {
					if (curCol) {
						var diffX = e.pageX - pageX;

						if (nxtCol)
							nxtCol.style.width = (nxtColWidth - (diffX)) + 'px';

						curCol.style.width = (curColWidth + diffX) + 'px';
					}
				});

				document.addEventListener('mouseup', function (e) {
					curCol = undefined;
					nxtCol = undefined;
					pageX = undefined;
					nxtColWidth = undefined;
					curColWidth = undefined
				});
			}

			function createDiv(height) {
				var div = document.createElement('div');
				div.style.top = 0;
				div.style.right = 0;
				div.style.width = '5px';
				div.style.position = 'absolute';
				div.style.cursor = 'col-resize';
				div.style.userSelect = 'none';
				div.style.height = height + 'px';
				return div;
			}

			function paddingDiff(col) {

				if (getStyleVal(col, 'box-sizing') == 'border-box') {
					return 0;
				}

				var padLeft = getStyleVal(col, 'padding-left');
				var padRight = getStyleVal(col, 'padding-right');
				return (parseInt(padLeft) + parseInt(padRight));

			}

			function getStyleVal(elm, css) {
				return (window.getComputedStyle(elm, null).getPropertyValue(css))
			}
		},


		

	},

	directives: {
		'sortable-table': {
			componentUpdated: (el) => {
				sortTableRows(el);
			},
			bind: (el) => {
				el.querySelectorAll('th').forEach((draggableEl) => {
					// Need a class watcher because sorting v-data-table rows asc/desc removes the sortHandle class
					watchClass(draggableEl, 'sortHandle');
					draggableEl.classList.add('sortHandle');
				});
				sortTableRows(el);
			},
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

		filteredHeaders: {
            get: function() { return this.gridInfo.headers; },
            set: function( headers ){ return this.gridInfo.headers;}
        },

		//스크롤
		items () {
        	return Array.from({ length: this.length }, (k, v) => v + 1)
		},
		length () {
			return 7000
		},
	},
	
	created(){
		// test();
		
	},
	mounted() {
		//컬럼 마우스 드레그 리사이즈 등록
		//정렬기능과 같이 사용하면 안됨.
		if(this.gridInfo.isResize){
			for (var i=0; i<tables.length;i++){
				this.resizableGrid(tables[i]);
			}
		}
		

	},
};





</script>

<style>
th, td {
  border-right: 1px solid grey;
}
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

.v-btn:not(.v-btn--round).v-size--default:not(:last-child){
	height: 36px;
    min-width: 64px;
    padding: 0 0px;
}


</style>
