 <!--
	@author 유지수
 	@create date 2022-07-14
	@desc   User 관리 페이지
 -->
<template>
	<v-app>
		<!-- Header Start -->
		<TheSystemBar />
		<TheHeader :pageName="pageName" />
		<!-- Header End -->

		<!-- Main Start -->
		<v-main>
			<v-container class="main_layout_container">
				<v-row class="main_layout_row">
					<v-col cols="12" md="8" lg="8" xl="8">
						<div> <h2> {{ this.$vuetify.breakpoint.name }} </h2> <v-divider /> </div>
						<v-row justify="center" no-gutters style="height: inherit">
							<!-- 검색 -->
							<v-col cols="3" class="hidden-sm-and-down">
								<TheSearch/>
							</v-col>

							<!-- 유저 목록 -->
							<v-col cols="5" xs="6">
								<FGrid :gridInfo="userInfo" />
							</v-col>

							<!-- 유저 상세정보  -->
							<v-col cols="4" xs="6" v-if="isAddr">
								<v-card outlined tile>
									<v-card-title>
										<span> 유저상세정보 </span>
										<v-spacer />
										<v-card-actions>
											<FOutlinedBtn v-if	   ="btnDetailText === '수정'" :btnText="btnDetailText" :btnHeight="35" @click="fn_detailEvent"/>
											<FOutlinedBtn v-else-if="btnDetailText === '저장'" :btnText="btnDetailText" :btnHeight="35" @click="fn_modify"/>
										</v-card-actions>
									</v-card-title>
									
									<v-card-text >
										<v-row align="center" class="mb-2">
											<v-col cols="4" class="pb-0 pt-1"> 아이디 </v-col>
											<v-col class="py-0">
												<v-text-field v-model="userDetailInfo.userId" dense hide-details disabled class="my-0"></v-text-field>
											</v-col>
										</v-row>
										<v-row align="center" class="mb-2">
											<v-col cols="4" class="pb-0 pt-1"> 수정일 </v-col>
											<v-col class="py-0">
												<v-text-field v-model="userDetailInfo.modDt" dense hide-details disabled class="my-0"></v-text-field>
											</v-col>
										</v-row>
										<v-row align="center" class="mb-2">
											<v-col cols="4" class="pb-0 pt-1"> 이메일인증 </v-col>
											<v-col class="py-0">
												<v-text-field v-model="userDetailInfo.isEmailAuth" dense hide-details disabled class="my-0"></v-text-field>
											</v-col>
										</v-row>
										<v-row align="center" class="mb-2">
											<v-col cols="4" class="pb-0 pt-1"> 이름 </v-col>
											<v-col class="py-0">
												<!-- <v-text-field v-model="userDetailInfo.userNm" dense hide-details :disabled="isDisabled" class="my-0" clearable
															  @input="inputText($event)" -->
												<v-text-field v-model="userDetailInfo.userNm" dense hide-details :disabled="isDisabled" class="my-0" clearable
												></v-text-field>
											</v-col>
										</v-row>
										<v-row align="center" class="mb-2">
											<v-col cols="4" class="pb-0 pt-1"> 가입경로 </v-col>
											<v-col class="py-0">
												<v-text-field v-model="userDetailInfo.oauthPath" dense hide-details disabled class="my-0"></v-text-field>
											</v-col>
										</v-row>
										<v-row align="center" class="mb-2">
											<v-col cols="4" class="pb-0 pt-1"> 비밀번호 </v-col>
											<v-col class="py-0">
												<v-text-field v-model="userDetailInfo.userPw" dense hide-details disabled class="my-0"></v-text-field>
											</v-col>
										</v-row>
										<v-row align="center" class="mb-2">
											<v-col cols="4" class="pb-0 pt-1"> 비즈네임 </v-col>
											<v-col class="py-0">
												<v-text-field v-model="userDetailInfo.bizNm" dense hide-details disabled class="my-0"></v-text-field>
											</v-col>
										</v-row>
										<v-row align="center">
											<v-col cols="4" class="pb-0 pt-1"> 그룹아이디 </v-col>
											<v-col class="py-0">
												<v-text-field v-model="userDetailInfo.grpId" dense hide-details disabled class="my-0"></v-text-field>
											</v-col>
										</v-row>
									</v-card-text>

									<!-- <template>
										<div v-if="isLength">
											<v-divider class="ma-0"/>
											<v-card-title class="pa-0">
												기본 주소지
												<v-spacer />
												<v-btn
													text
													style="font-size:0.9em"
													@click="isAddrDialog = !isAddrDialog"
												>
													변경하기
												</v-btn>
											</v-card-title>
											<div 
												v-for="(addr, idx) in userDetailInfo.addrList"
												:key="addr.id"
											>
												
												<div v-if="idx === 0">
													<div> {{ addr.addrDetail }} </div>
													<div> {{ addr.addrEtc }} </div>
													<div> {{ addr.phone1 }} </div>
												</div>
											</div>
										</div>
										<div v-else>
											<v-btn>추가하기</v-btn>
										</div>
									</template> -->
<!-- 
									<template v-if="isAddrDialog">
										<FEditDialog/>
									</template> -->
									
									<div
										v-for="addr in userDetailInfo.addrList"
										:key="addr.id"
									>
										<TheAddrEdit :addr="addr" />
									</div>
								</v-card>
							</v-col>
						</v-row>
					</v-col>
				</v-row>
			</v-container>
		</v-main>
		<!-- Main End -->

		<!-- Footer Start -->
		<v-footer class="ma-0 pa-0" fixed app>
			<TheFooter />
		</v-footer>
		<!-- Footer End -->
	</v-app>
</template>

<script>
import { TheSystemBar, TheHeader, TheFooter, TheAddrEdit, FGrid, TheSearch, FOutlinedBtn} from '@/assets/util/importFile.js';
import MixinGlobal from '@/mixin/MixinGlobal';
import { mapGetters } from 'vuex';
import YsyUtil from '@/mixin/YsyUtil';

export default {
	components: {
    TheSystemBar,
    TheHeader,
    TheFooter,
    TheAddrEdit,
    FGrid,
	TheSearch,
	FOutlinedBtn,
},
	mixins: [MixinGlobal],
	data() {
		return {
			pageName: 'User Manager',
			btnDetailText: '수정',
			isDisabled: true,
			isAddr: true, // user 상세 정보 view 변수
			userDetailInfo: {},
			isAddrDialog: false,
			userInfo: {
				dataList: [{}],
				headers: [
					{ text: '아이디', value: 'userId', key: true },
					{ text: '이름', value: 'userNm' },
					{ text: '날짜', value: 'regDt' },
				],
				dateGubun: '/',
				gridNm: '유저관리',
				path: '/userMng',
				isCheckBox: false,
				isSingleSelect: false,
				rowCnt: 10,
				gridDense: true,
				isDetail: true,
				rowClick: (row, gridNm) => {
					this.fn_userClick(row, gridNm);
				}, //로우 클릭 이벤트 콜백
				rowDbClick: (row, gridNm) => {
					this.fn_userDbClick(row, gridNm);
				}, //로우 더블클릭 이벤트 콜백
			},
			edlitedUserDetailInfo: null,
		};
	},
	computed: {
		...mapGetters({ getUserDetail: 'userStore/getUserDetail' }),
		...mapGetters({ getUserList: 'userStore/getUserList' }),
		isLength() {
			if(this.userDetailInfo.addrList !== undefined) {
				return this.userDetailInfo.addrList.length > 0 ? true : false;
			}
			else {
				return false;
			}
		}
	},
	methods: {
		async fn_getUserList() {
			try {
				await this.$store.dispatch('userStore/fn_getUserList');
				if (this.getUserList) {
					console.log('🟢 userMng fn_getUserList');
					this.userInfo.dataList = this.getUserList;
					console.log(this.userInfo.dataList);
				}	
			} catch (error) {
				console.log('❌ userMng fn_getUserList ❌');
			}
		},
		async fn_userClick(row, gridNm) {
			try {
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
		fn_userDbClick(row, gridNm) {},
		fn_isNullAndEmpty(info) {
			YsyUtil.methods.isNullAndEmpty(info.userId) 	 === true ? info.userId 	 = 'NONE' : '';
			YsyUtil.methods.isNullAndEmpty(info.modDt) 		 === true ? info.modDt 		 = 'NONE' : '';
			YsyUtil.methods.isNullAndEmpty(info.regDt) 		 === true ? info.regDt 		 = 'NONE' : '';
			YsyUtil.methods.isNullAndEmpty(info.isEmailAuth) === true ? info.isEmailAuth = 'NONE' : '';
			YsyUtil.methods.isNullAndEmpty(info.oauthPath) 	 === true ? info.oauthPath 	 = 'NONE' : '';
			YsyUtil.methods.isNullAndEmpty(info.userPw) 	 === true ? info.userPw 	 = 'NONE' : this.userDetailInfo.userPw = 'NONE';
			YsyUtil.methods.isNullAndEmpty(info.bizNm) 		 === true ? info.bizNm 		 = 'NONE' : '';
			YsyUtil.methods.isNullAndEmpty(info.grpId) 		 === true ? info.grpId 		 = 'NONE' : '';
		},
		fn_detailEvent(eventResponse) {
			this.isDisabled === true ? this.isDisabled = false : this.isDisabled = true;
			this.btnDetailText === '수정' ? this.btnDetailText = '저장' : this.btnDetailText = '수정';
			this.edlitedUserDetailInfo = Object.assign({}, this.userDetailInfo)
			console.log('👌this.edlitedUserDetailInfo');
			console.log(this.userDetailInfo);
			console.log(this.edlitedUserDetailInfo);
		},
		fn_modify(eventResponse) {
			console.log(eventResponse);
			this.isDisabled === true ? this.isDisabled = false : this.isDisabled = true;
			this.btnDetailText === '수정' ? this.btnDetailText = '저장' : this.btnDetailText = '수정';
			console.log('this.userDetailInfo.userNm ------------>')
			console.log(this.userDetailInfo.userNm)
		},
	},
	mounted() {
		this.fn_getUserList();
	},

};
</script>
