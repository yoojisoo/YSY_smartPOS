 <!--
	@author 유지수
 	@create date 2022-07-14
	@desc   User 관리 페이지
 -->
<template>
	<v-container
		fluid
		id="scroll-target"
		style="max-height: 550px"
		class="overflow-y-auto"
	>
	<!-- <v-container fluid > -->
		<div class="pa-0 ma-0"> <h2 class="pa-0 ma-0"> {{ this.$vuetify.breakpoint.name }} </h2> </div>
		<v-sheet color="red">
			<v-row justify="center" no-gutters>
				<v-col >
					<v-row justify="center" no-gutters>
						<v-col cols="3" class="hidden-sm-and-down">
							<TheSearch/>
						</v-col>

						<v-col cols="12" md="6">
							<Y2sGridVue :gridInfo="userInfo" :gridDataList="getUserList"/>
						</v-col>

						<v-col cols="3" v-if="isAddr" class="hidden-xs-and-down">
							<!-- <TheDetailInfoVue :userDetailInfo="userDetailInfo"/>							 -->
						</v-col>
					</v-row>
				</v-col>
			</v-row>
		</v-sheet>
	</v-container>
</template>

<script>
import { TheSystemBar, TheHeader, TheFooter, TheAddrEdit, FGrid, TheSearch, FOutlinedBtn, TheDetailInfoVue} from '@/assets/util/importFile.js';
import { mapGetters } from 'vuex';
import Y2sGridVue from '@/components/Y2sGrid.vue';
import axios from 'axios';
export default {
	components: {
		TheSystemBar,
		TheHeader,
		TheFooter,
		TheAddrEdit,
		FGrid,
		TheSearch,
		FOutlinedBtn,
		TheDetailInfoVue,
		Y2sGridVue,
	},
	data() {
		return {
			pageName: 'User Manager',
			offsetTop: 0,
			isDisabled: true,
			isAddr: true, // user 상세 정보 view 변수
			userDetailInfo: {},
			isAddrDialog: false,
			edlitedUserDetailInfo: null,
			userInfo: {
				headers: [
					{ text: '아이디', 		value: 'userId', key: true },
					{ text: '이름', 		value: 'userNm' },
					{ text: '가입 경로', 	value: 'oauthPath' },
					{ text: '이메일 인증',	value: 'isEmailAuth' },
					{ text: '비즈네임', 	value: 'bizNm' },
					{ text: '권한', 		value: 'grpNm' },
					{ text: '생성일', 		value: 'modDt' },
					{ text: '접속일', 		value: 'regDt' },
				],
				gridNm: 'user mng',
				page: 1,
				rowCnt: 10,
				gridKey: '',
				gridDense: true,
                searchBtnClick : '검색',
                addBtnClick : '추가',
                delBtnClick : '삭제',
                moreBtnClick : '더보기',
                excelDownClick : '엑셀다운로드',
                isBtnGrp:false,
                isResize:true,
                isNotSort:true,
                isUseHeader:false,
                isUseBody:false,
                isFiltering:true,
				hide_default_header:false,
                hide_default_footer:true,

				rowDbClick: (event, dataInfo, gridNm) => {
					this.fn_rowDbClick(event, dataInfo, gridNm);
				},
                moreBtnClick: (gridNm) => {
					this.fn_moreBtnClick(gridNm);
				},
                addBtnClick: (gridNm, curIndex) => {
					this.fn_addBtnClick(gridNm, curIndex);
				},
                excelDownClick: (gridNm) => {
					this.fn_excelDownClick(gridNm);
				},
                excelUploadClick: (gridNm, file) => {
					this.fn_excelUploadClick(gridNm, file);
				},
			},
			
		};
	},
	computed: {
		...mapGetters({ getUserDetail: 'userStore/getUserDetail' }),
		...mapGetters({ getUserList: 'userStore/getUserList' }),
		// isLength() {
		// 	if(this.userDetailInfo.addrList !== undefined) {
		// 		return this.userDetailInfo.addrList.length > 0 ? true : false;
		// 	}
		// 	else {
		// 		return false;
		// 	}
		// }
	},
	methods: {
		//🔅 get data function
		async fn_getUserList() {
			let condition = {
					bizCd: '0001',
					grpId: 'ROLE_ADMIN'
			}

			try {
				await this.$store.dispatch('userStore/fn_getUserList', condition);
				if (this.getUserList) {
					console.log('🟢 userMng fn_getUserList');
					this.userInfo.dataList = this.getUserList;
					console.log(this.userInfo.dataList);
				}	
			} catch (error) {
				console.log('⛔ userMng fn_getUserList');
			}
		},
		async fn_userClick(row, gridNm) {
			try {
				console.log('fn_userClick ------------------------->');
				console.log(row);
				await this.$store.dispatch('userStore/fn_getUserDetail', row.userId);

				if (this.getUserDetail) {
					console.log('🟢 userMng fn_userClick');
					this.userDetailInfo = this.getUserDetail.obj;
					console.log(this.userDetailInfo);

					this.fn_isNullAndEmpty(this.userDetailInfo);
				}
			} catch (error) {
				console.log('❌ userMng fn_userClick ❌');
			}
		},
		//🔅 grid return function
		fn_rowDbClick: (event, dataInfo, gridNm) => {
					console.log("rowDbClick=>" + gridNm);
					console.log(event);
					console.log(dataInfo);
                    dataInfo.item.col2 = 12345;
		},
		fn_searchBtnClick: ( gridNm ) => {
			console.log('searchBtnClick =>' + gridNm);
		},
		fn_moreBtnClick: ( gridNm) => {
			console.log('moreBtnClick =>' + gridNm);
		},
		fn_addBtnClick: ( gridNm, curIndex ) => {
			console.log('addBtnClick =>' + curIndex , gridNm);
		},
		fn_excelDownClick: ( gridNm ) => {
			console.log('excelDownClick =>' + gridNm);
		},
		fn_excelUploadClick: ( gridNm , file) => {
			console.log('excelUploadClick =>' + gridNm , file);
			if (file != undefined || file) {
				console.log('체크중 ---------------------------------->');
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
		// async fn_test() {
		// 	try {
		// 		console.log('fn_test fumction 시작 --------------------------------------->');
		// 			// userId: 's_plus7@naver.com'
		// 		let condition = {
		// 			bizCd: '0001',
		// 			grpId: 'ROLE_ADMIN'
		// 		}
		// 		await axios.post('/ysy/v1/getUserListCondition', condition)
		// 				   .then(res => {
		// 						console.log('컨디션 -------------------------------->');
		// 						console.log(res.data);
		// 				   })
		// 				   .catch(error=>{
		// 						console.log(error);
		// 				   });
		// 		if (res) {
		// 			console.log('✅ UserMng fn_test 🔅🔅🔅🔅🔅🔅🔅🔅🔅🔅');
		// 			console.log(res.data);
		// 			return res.data;
		// 		}
		// 	} catch (error) {
		// 		console.log('UserMng fn_test error => ' + error);
		// 		return error;
		// 	}
		// }
	},
	mounted() {
		this.fn_getUserList();
	},

};
</script>