/**
 * @author clubbboy@naver.com
 * @create date 2022-07-17
 * @desc excel down upload 
 */

//  import menuService from '@/service/MenuService.js';
import * as XLSX from 'xlsx';

 const excelStore = {
    namespaced: true,
 
    state: {
        sheetName : "",
        fileName : "",
        excelToJson: [],
    },
 
    getters: {
        getFileName: state => state.fileName,
        getSheetName: state => state.sheetName,
        getExcelToJson: state => state.excelToJson,
        
    },
 
    mutations: {
        setFileName(state, payload) {
            state.fileName = payload;
        },
        setSheetName(state, payload) {
            state.sheetName = payload;
        },
        setExcelToJson(state, payload) {
            state.excelToJson = payload;
        },
        addData(state, payload){
            state.excelToJson.splice(payload.index , 0 ,payload.data );
        }
    },
 
 
    actions: {
        //데이터 로우 추가
        //payload { json , index}
        addData({ commit },payload){
            console.log("addData payload");
            console.log(payload);
            commit('addData', payload);
        },

        downloadExcelFile({ commit },payload ) {
            console.log("ExcelUtil downloadExcelFile = " );
            const workBook = XLSX.utils.book_new();
          
            const workSheet = XLSX.utils.json_to_sheet(payload.body);
            XLSX.utils.sheet_add_aoa(workSheet, payload.header); // header set
            XLSX.utils.book_append_sheet(workBook, workSheet, payload.sheetName);
            XLSX.writeFile(workBook, payload.fileName);
        },
        //EXCEL UPLOAD시 JSON DATA로 변경
        uploadExcelDefault({ commit }, payload) {
            console.log("ExcelUtil uploadExcelFile ===> Excel upload start ");
            console.log(payload.file);
            commit('setFileName', payload.file.name);   
            // const file = event.target.files[0];
            const file       = payload.file;
            const userHeader = payload.customHeader;
            let reader = new FileReader();
            reader.readAsBinaryString(file);
            
            
            reader.onload = (e) => {
                console.log("reader.onload start"); 
                let data = reader.result;
                let workbook = XLSX.read(data, {type: 'binary'});

                workbook.SheetNames.forEach(sheetName => {
                    //시트명 입력
                    commit('setSheetName', sheetName);    
                    //컬럼명 새롭게 지정 - 한글이었을경우 DB 컬럼에 맟주어서 진행
                    console.log("sheetName = "+sheetName);
                    console.log("customHeader = "+userHeader);
                    const EXCEL_HEADER = ["A1","B1","C1","D1","E1","F1","G1","H1","I1","J1","K1","L1","M1","N1","O1","P1","Q1","R1","S1","T1"];
                    //column header 변경
                    if(userHeader != undefined){
                        userHeader.forEach((element, index) => {
                            workbook.Sheets[sheetName][EXCEL_HEADER[index]].w = element;
                        });
                    }
                    // workbook.Sheets[sheetName].A1.w = "test2";
    
                    // console.log("workbook.Sheets[sheetName].A1");
                    // console.log(workbook.Sheets[sheetName]);
                    // console.log(workbook.Sheets[sheetName].A1);
                    const roa = XLSX.utils.sheet_to_json(workbook.Sheets[sheetName]);
                    commit('setExcelToJson', roa);
                });
                console.log("reader.onload end");
            }
        },
        
    },
 };
 
 export default excelStore;
 