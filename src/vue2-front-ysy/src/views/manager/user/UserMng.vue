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
			<v-container fluid pa-0 ma-0>
				<v-row justify="center" no-gutters>
					<v-col cols="12" md="8" lg="8" xl="8">
						<div> <h2> {{ this.$vuetify.breakpoint.name }} </h2> </div>
						<v-divider />
						<v-row justify="center" no-gutters style="height: inherit">
							<!-- 검색 -->
							<v-col cols="2" class="hidden-sm-and-down">
								<TheSearch/>
							</v-col>

							<!-- 유저 목록 -->
							<v-col cols="5" xs="6">
								<FGrid :gridInfo="userInfo" />
							</v-col>

							<!-- 유저 상세정보  -->
							<v-col cols="5" xs="6" v-if="isAddr">
								<v-card outlined tile>
									<v-card-title>
										유저상세정보
									</v-card-title>
									<v-card-subtitle>
										<v-row>
											<v-col cols="2">
												<div> 아이디 </div>
												<div> 이름 </div>
												<div> 생성일 </div>
												<div> 가입경로 </div>
												<div> 비즈네임 </div>
											</v-col>
											<v-col>
												<div> {{ userDetailInfo.userId }} </div> 
												<div> {{ userDetailInfo.userNm }} </div> 
												<div> {{ userDetailInfo.regDt }} </div> 
												<div> {{ userDetailInfo.oauthPath }} </div> 
												<div> {{ userDetailInfo.bizNm }} </div> 
											</v-col>
										</v-row>
									</v-card-subtitle>

									<template>
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
									</template>

									<template v-if="isAddrDialog">
										<FEditDialog/>
									</template>
									
									<!-- <div
										v-for="addr in userDetailInfo.addrList"
										:key="addr.id"
									>
										<TheAddrEdit :addr="addr" />
									</div> -->
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
import { TheSystemBar, TheHeader, TheFooter, TheAddrEdit, FGrid, FEditDialog, TheSearch } from '@/assets/util/importFile.js';
import MixinGlobal from '@/mixin/MixinGlobal';
import { mapGetters } from 'vuex';

export default {
	components: {
    TheSystemBar,
    TheHeader,
    TheFooter,
    TheAddrEdit,
    FGrid,
    FEditDialog,
	TheSearch,
},
	mixins: [MixinGlobal],
	data() {
		return {
			pageName: 'User Manager',
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
				}	
			} catch (error) {
				console.log('❌ userMng fn_userClick ❌');
			}
		},
		fn_userDbClick(row, gridNm) {},
	},
	mounted() {
		this.fn_getUserList();
	}
};
</script>
