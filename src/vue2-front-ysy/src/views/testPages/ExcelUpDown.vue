<template>
    <v-app>
        test excel
        <!-- <v-card>
            <v-btn @click="excelDown"> Excel Down </v-btn>
        </v-card> -->
        <!-- <input type="file" @change="excelUpload" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"> -->
        <!-- v-model="files" -->
        <v-card>
            <v-file-input 
                label="Excel Upload"
                accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                small-chips
                show-size
                outlined
                dense
                prepend-icon="mdi-paperclip"
                @change="excelUpload"
                />
        </v-card>
        <Y2sGrid :gridInfo="gridInfo" :gridDataList="getExcelToJson" />
        filename = [{{getFileName}}]
        <br/>
        sheetname = [{{getSheetName}}]
        <br/>
        data cnt : {{getExcelToJson.length}}
        <br/>
        column cnt : {{getExcelToJson.length >0 ?Object.keys(getExcelToJson[0]).length : 0}}
        <br/>
        
      
      
    </v-app>
</template>

<script>

import { mapGetters } from 'vuex';
import Y2sGrid from "@/components/Y2sGrid.vue"

export default {
    components :{
        Y2sGrid,
    },
    data(){
        return {
            files:[],
            excelData : {
                header: [['이름', '칼로리', '지방', '탄수화물', '단백질', '철분']],
                body: [
                    {
                        name: '바나나',
                        calories: 159,
                        fat: 6.0,
                        carbs: 24,
                        protein: 4.0,
                        iron: '1%',
                    },
                    {
                        name: '사과',
                        calories: 237,
                        fat: 9.0,
                        carbs: 37,
                        protein: 4.3,
                        iron: '1%',
                    },
                ],
                sheetName : "sampleSheet",
                fileName : "sampleFile.xlsx",
            },
            excelJsonData : {},


            //grid 관련 정보 dataList는 따로 관리
            gridInfo : {
				headers: [
					{ text: '이름', value: 'col1',width:"30%",  key: true},
					{ text: '칼로리', value: 'col2',           },
					{ text: '지방', value: 'col3',             },
					{ text: '탄수화물', value: 'col4',         },
					{ text: '단백질', value: 'col5',           },
					{ text: '철분', value: 'col6',             },
				],
				// dateGubun: '/',
				gridNm: 'Excel Upload Data',
				
				// isCheckBox: false,
				// isSingleSelect: false,
				rowCnt: 10,
				gridDense: true,
				useBtn: true,
                hide_default_footer:false,
                moreBtnNm : "더보기...",
                addBtnNm : "Add",
                isBtnGrp:false,
				// rowClick: (event, dataInfo , gridNm) => {
				// 	console.log("rowClick =>" + gridNm);
				// }, //로우 클릭 이벤트 콜백
				rowDbClick: (event, dataInfo , gridNm) => {
					console.log("rowDbClick=>" + gridNm);
					console.log(event);
					console.log(dataInfo);
                    dataInfo.item.col2 = 12345;

				}, //로우 더블클릭 이벤트 콜백
                // moreBtnClick: ( gridNm) => {
                //     console.log("moreBtnClick =>" + gridNm);
				// },
                // addBtnClick: ( gridNm, curIndex ) => {
                //     console.log("addItemBtn =>" + curIndex , gridNm);
				// },
                excelDownClick: ( gridNm) => {
                    this.excelData.body = this.getExcelToJson;
                    this.$store
                    .dispatch('excelStore/downloadExcelFile',this.excelData)
                    .catch(error => {
                        console.log('===============> excelStore/downloadExcelFile error');
                        console.log(error);
                    });
				},
                // excelUploadClick: ( gridNm , file) => {
                //     if(file != undefined || file){
                //         const customHeader = ["col1","col2","col3","col4","col5","col6"];
                //         const data = {
                //             file,
                //             customHeader
                //         };
                //         this.$store
                //             .dispatch('excelStore/uploadExcelDefault',data)
                //             .catch(error => {
                //                 console.log('===============> excelStore/uploadExcelDefault error');
                //                 console.log(error);
                //             });
                //     }
				// },
			},
        }
    },

    computed:{
        
        ...mapGetters({ getExcelToJson: 'excelStore/getExcelToJson' }),
        ...mapGetters({ getSheetName: 'excelStore/getSheetName' }),
        ...mapGetters({ getFileName: 'excelStore/getFileName' }),

    },
    methods: {
        
        // 엑셀 다운로드
        excelDown(){

            this.$store
                .dispatch('excelStore/downloadExcelFile',this.excelData)
                .catch(error => {
                    console.log('===============> excelStore/downloadExcelFile error');
                    console.log(error);
                });
            
        },
        // 엑셀 업로드
        excelUpload(file) {
            console.log(this.files);
            if(file != undefined || file){
                const customHeader = ["col1","col2","col3","col4","col5","col6"];
                const data = {
                    file,
                    customHeader
                };
                this.$store
                    .dispatch('excelStore/uploadExcelDefault',data)
                    .catch(error => {
                        console.log('===============> excelStore/uploadExcelDefault error');
                        console.log(error);
			        });
            }
            

        },
        //
        uploadCancel(){
            console.log("uploadCancel");
        },
    },
    mounted(){
       
        
    },
}
</script>

<style>

</style>