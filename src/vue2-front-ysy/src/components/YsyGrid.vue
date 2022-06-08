<!-- 
          *****설명
          :page.sync="page"              -> 원하는 page를 선택했을때, 자동적으로 대상 page로 전환
          :items-per-page="itemsPerPage" -> 한 page에서 보여줄 list의 개 수
          hide-default-footer            -> 기본 페이지네이션 숨김
          @page-count                    -> 페이지 갯수 지정


          :show-select="gridInfo.isCheckBox"           : 체크박스 사용여부
          :single-select="gridInfo.isSingleSelect"     : 한개씩 선택할지 사용여부  -> true인경우 한개만 체크할수 있음.
       -->

<template>
  <v-container fluid>

    <v-data-table
                :headers="gridInfo.headers"
                :hide-default-header="true" 
                :items="gridInfo.dataList"
                :page.sync="page"
                :items-per-page="rowCnt"
                :show-select="gridInfo.isCheckBox"
                :single-select="gridInfo.isSingleSelect"
                hide-default-footer
                :item-key="gridKey"
                @page-count="pageCount = $event"
                @click:row="rowClick"
                :dense = "gridDense"
    >
    <template v-slot:header="{ props }">
        <thead class="v-data-table-header">
            <tr width="100%">
                <th rowspan="2" class="text-center parent-header" style="border:1px solid rgb(0, 0, 0);width:50%">
                    변경된 아이디
                </th>
                <th colspan="2"  class="text-center parent-header" style="border:1px solid rgb(0, 0, 0);width:50%">
                  병합
                </th>
            </tr>
            <tr>
                <th class="text-center child-header" style="border:1px solid rgb(0, 0, 0);width:50%">
                  전화번호
                </th>
                <th class="text-center child-header" style="border:1px solid rgb(0, 0, 0);width:50%">
                  날짜
                </th>
           </tr>
      </thead>
    </template>
        <template #item.regDt="{item}">
            <dateForm :dateVal="item.regDt" :gubun="gridInfo.dateGubun" />
        </template>
        

    </v-data-table>
    
        <template>
            <!-- 
                *****설명
                pagination              -> 페이지 넘김 아이콘
                :length                 -> 총 페이지의 사이즈
                v-model="page"          -> 활성화 된 목록(버튼)
                :total-visible          -> ppagination에 표시할 page의 개수를 지정. 초과시 ... 표시
                next-icon과 prev-icon   -> pagination 아이콘 지정
                v-text-fiel             -> 입력한 숫자대로 row 갯수 조절
circle Square
                :total-visible="totalVisible"
             -->
            <v-pagination
                        v-model="page"
                        :length="pageCount"
                        :total-visible="pageTotCnt"
                         
            >
            </v-pagination>
           
        </template>
  </v-container>
</template>

<script>
    import dateForm from '@/components/gridModule/DateForm.vue'
    export default {
        props: [  "gridInfo"],
        components: {
            dateForm,
        },
        data: () => ({
            page: 1,    // 최초 나타나는 페이지
            pageCount: 0, // 데이터 겟수에 따라 변경됨 ->  @page-count="pageCount = $event"
        }),
        methods: {
            /** grid click event */
		    rowClick(item, row) {
                if(this.gridInfo.rowClick){
                    this.gridInfo.rowClick(row , this.gridInfo.gridNm);
                }
		    },
        },
        computed : {
            rowCnt(){
                return this.gridInfo.rowCnt != undefined ? this.gridInfo.rowCnt :5 ;// 페이지당 로우 겟수
            },
            pageTotCnt(){
                return this.gridInfo.pageTotCnt != undefined ? this.gridInfo.pageTotCnt :5 ;
            },
            gridDense(){// 그리드 행 폭 작게 지정여부 
                return this.gridInfo.gridDense != undefined ? this.gridInfo.gridDense :false ;
            },
            gridKey(){
                let keys = "";
                this.gridInfo.headers.forEach(x => {
                    if(x.key != undefined) keys += x.value;
                });
                console.log("keys = " + keys);
                return keys ;
            },
        },
        mounted (){

            console.log("mounted grid =========================================");
            console.log(this.gridInfo);
        }
    }
</script>
<style>
/* .v-data-table
    td
      padding: 12px 8px
      font-size: $record-text-size
      border-color: rgba(0,0,0,0.12)
      border-style: solid
      border-left-width: 0px
      border-right-width: 1px
      border-top-width: 0px
      border-bottom-width: 1px
    th
      border-color: rgba(0,0,0,0.12)
      border-style: solid
      border-left-width: 0px
      border-right-width: 1px
      border-top-width: 0px
      border-bottom-width: 1px
  .v-data-table table
    border: solid 1px #a1a1a1
    border-radius: 5px
  .v-data-table table thead tr th
    font-weight: bold
    font-size: $record-text-size
    padding: 0px 8px

  .v-data-table table tbody tr td .v-btn
    margin-right: 0px !important

  .v-data-table .v-data-table-header__sort-badge
    font-size: 10px

  .v-data-table.theme--dark
    tr th
      color: #fff !important

  .theme--light
    .v-data-table table thead tr th
      color: black
    .v-data-table table thead tr td
      color: black */
</style>
