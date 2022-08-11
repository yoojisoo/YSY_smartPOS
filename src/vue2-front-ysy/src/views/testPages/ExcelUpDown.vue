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

		<Y2sGrid :gridInfo="gridInfo" :gridDataList="getExcelToJson" />
		<!-- <Y2sGridBigData :headers="gridInfo.headers" :dataList="getExcelToJson" /> -->
		<!-- <EasyTable :dataList="getExcelToJson" :headers="gridInfo.easyHeaders" :colHiddenOption="gridInfo.easyColumnHiddenOption" /> -->

		filename = [{{ getFileName }}]
		<br />
		sheetname = [{{ getSheetName }}]
		<br />
		data cnt : {{ getExcelToJson.length }}
		<br />
		column cnt : {{ getExcelToJson.length > 0 ? Object.keys(getExcelToJson[0]).length : 0 }}
		<br />
	</v-app>
</template>

<script>
import Y2sGrid from '@/components/Y2sGrid.vue';
import Y2sGridBigData from '@/components/Y2sGridBigData.vue';
import EasyTable from '@/components/EasyTable.vue';

export default {
	components: {
		Y2sGrid,
		Y2sGridBigData,
		EasyTable,
	},
	data() {
		return {
			files: [],
			excelData: {
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
				sheetName: 'sampleSheet',
				fileName: 'sampleFile.xlsx',
			},
			excelJsonData: {},

			//grid 관련 정보 dataList는 따로 관리
			gridInfo: {
				headers: [
					{ width: '30%', class: 'sticky-header  grey lighten-3', align: 'center', text: '이름', value: 'col1', key: true },
					{ width: '10%', class: 'sticky-header  grey lighten-3', align: 'center', text: '칼로리', value: 'col2' },
					{ width: '10%', class: 'sticky-header  grey lighten-3', align: 'center', text: '지방', value: 'col3' },
					{ width: '10%', class: 'sticky-header  grey lighten-3', align: 'center', text: '탄수화물', value: 'col4' },
					{ width: '10%', class: 'sticky-header  grey lighten-3', align: 'center', text: '단백질', value: 'col5' },
					{ width: '30%', class: 'sticky-header  grey lighten-3', align: 'center', text: '철분', value: 'col6' },
				],
				easyHeaders: [
					{ field: 'col1', key: 'a', width: '30%', align: 'center', title: '이름', sortBy: '', fixed: 'left' },
					{ field: 'col2', key: 'b', width: '10%', align: 'center', title: '칼로리', sortBy: '' },
					{ field: 'col3', key: 'c', width: '10%', align: 'center', title: '지방', sortBy: '' },
					{ field: 'col4', key: 'd', width: '10%', align: 'center', title: '탄수화물' },
					{ field: 'col5', key: 'e', width: '10%', align: 'center', title: '단백질' },
					{ field: 'col6', key: 'f', width: '30%', align: 'center', title: '철분' },
				],
				easyColumnHiddenOption: {
					// default hidden column keys
					defaultHiddenColumnKeys: ['1'],
				},
				// dateGubun: '/',
				gridNm: 'Excel Upload Data',

				// isCheckBox: false,
				// isSingleSelect: false,
				rowCnt: 10,
				gridDense: true,
				useBtn: true,
				hide_default_footer: true,
				moreBtnNm: '더보기...',
				addBtnNm: 'Add',
				isBtnGrp: false,
				isResize: true,
				isNotSort: true,
				isUseHeader: false,
				isUseBody: false,
				isFiltering: true,
				// rowClick: (event, dataInfo , gridNm) => {
				// 	console.log("rowClick =>" + gridNm);
				// }, //로우 클릭 이벤트 콜백
				rowDbClick: (event, dataInfo, gridNm) => {
					console.log('rowDbClick=>' + gridNm);
					console.log(event);
					console.log(dataInfo);
					dataInfo.item.col2 = 12345;
				}, //로우 더블클릭 이벤트 콜백
				moreBtnClick: gridNm => {
					console.log('moreBtnClick =>' + gridNm);
				},
				addBtnClick: (gridNm, curIndex) => {
					console.log('addItemBtn =>' + curIndex, gridNm);
					let json = {
						col1: '111',
						col2: '112',
						col3: '113',
						col4: '1114',
						col5: '115',
						col6: '1116',
					};
					let pay = {
						index: curIndex + 1,
						data: json,
					};
					this.$store.dispatch('excelStore/addData', pay);
				},
				excelDownClick: gridNm => {
					this.excelData.body = this.getExcelToJson;
					this.$store.dispatch('excelStore/downloadExcelFile', this.excelData).catch(error => {
						console.log('===============> excelStore/downloadExcelFile error');
						console.log(error);
					});
				},
				excelUploadClick: (gridNm, file) => {
					if (file != undefined || file) {
						const customHeader = ['col1', 'col2', 'col3', 'col4', 'col5', 'col6'];
						const data = {
							file,
							customHeader,
						};
						this.$store.dispatch('excelStore/uploadExcelDefault', data).catch(error => {
							console.log('===============> excelStore/uploadExcelDefault error');
							console.log(error);
						});
					}
				},
			},
		};
	},

	computed: {
		getExcelToJson() {
			return this.$store.state.excelStore.excelToJson;
		},
		getSheetName() {
			return this.$store.state.excelStore.sheetName;
		},
		getFileName() {
			return this.$store.state.excelStore.fileName;
		},
	},
	methods: {
		// 엑셀 다운로드
		excelDown() {
			this.$store.dispatch('excelStore/downloadExcelFile', this.excelData).catch(error => {
				console.log('===============> excelStore/downloadExcelFile error');
				console.log(error);
			});
		},
		// 엑셀 업로드
		excelUpload(file) {
			console.log(this.files);
			if (file != undefined || file) {
				const customHeader = ['col1', 'col2', 'col3', 'col4', 'col5', 'col6'];
				const data = {
					file,
					customHeader,
				};
				this.$store.dispatch('excelStore/uploadExcelDefault', data).catch(error => {
					console.log('===============> excelStore/uploadExcelDefault error');
					console.log(error);
				});
			}
		},
		//
		uploadCancel() {
			console.log('uploadCancel');
		},
	},
	mounted() {},
};
</script>

<style></style>
