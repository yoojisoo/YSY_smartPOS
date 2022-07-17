<template>
    <v-app>
        test excel
        <v-card>
            <v-btn @click="excelDown"> Excel Down </v-btn>
        </v-card>
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
        filename = [{{getFileName}}]
        <br/>
        sheetname = [{{getSheetName}}]
        <br/>
        data cnt : {{getExcelToJson.length}}
        <br/>
        column cnt : {{getExcelToJson.length >0 ?Object.keys(getExcelToJson[0]).length : 0}}
        <br/>
        data = {{getExcelToJson}} 
      
      
    </v-app>
</template>

<script>

import { mapGetters } from 'vuex';

export default {
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
                const customHeader = ["col1","col2","col3","col4"];
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