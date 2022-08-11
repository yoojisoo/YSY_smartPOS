<!--
	@author 👻s_plus7
 	@create date 2022-07-14
	@desc   User 관리 페이지
 -->
<template>
	<v-container class="main_layout_container">
		<!-- {{ this.$vuetify.breakpoint.name }} -->
		<v-row class="main_layout_row">
			<v-col cols="3" md="2" class="main_layout_col hidden-sm-and-down">
				<TheLeftCondition :filters="userInfo.headers" />
			</v-col>

			<v-col cols="12" md="7" class="main_layout_col">
				<Y2sGrid :gridInfo="userInfo" :gridDataList="getUserList" />
			</v-col>

			<v-col cols="3" md="3" v-if="isAddr" class="main_layout_col hidden-sm-and-down">
				<TheDetailInfo :userDetailInfo="userDetailInfo" />
			</v-col>
		</v-row>
	</v-container>
</template>

<script>
import { TheLeftCondition, TheDetailInfo } from '@/assets/import/index.js';
import Y2sGrid from '@/components/Y2sGrid.vue';

export default {
	components: {
		TheLeftCondition,
		TheDetailInfo,
		Y2sGrid,
	},
	data() {
		return {
			name: '사용자 관리',
			pageName: 'User Manager',
			isAddr: true, // user 상세 정보 view 변수
			userDetailInfo: {
				addrList: [],
				row: {},
			},
			isAddrDialog: false,
			userInfo: {
				headers: [
					{ text: '아이디', value: 'userId', key: true },
					{ text: '이름', value: 'userNm' },
					{ text: '가입 경로', value: 'oauthPath' },
					{ text: '이메일 인증', value: 'isEmailAuth' },
					{ text: '회사명', value: 'bizNm' },
					{ text: '권한등급', value: 'grpNm' },
					{ text: '생성일', value: 'modDt' },
					{ text: '접속일', value: 'regDt' },
				],
				gridNm: 'user mng',
				page: 1,
				rowCnt: 10,
				gridKey: '',
				gridDense: true,
				isBtnGrp: false,
				isResize: true,
				isNotSort: true,
				isUseHeader: false,
				isUseBody: false,
				isFiltering: true,
				hide_default_header: false,
				hide_default_footer: true,

				rowDbClick: (event, dataInfo, gridNm) => {
					this.fn_getUserAddr(dataInfo.item.userId, dataInfo.item);
				},
			},
		};
	},
	computed: {
		getUserList() {
			return this.$store.state.userStore.userList;
		},
		getUserAddr() {
			return this.$store.state.userStore.userAddr;
		},
	},
	methods: {
		// 🔅 모든 유저 리스트 가져오기
		async fn_getUserList() {
			try {
				await this.$store.dispatch('userStore/fn_getUserList');
				if (this.getUserList) {
					console.log('🟢 userMng fn_getUserList');
					this.userInfo.dataList = this.getUserList;
					console.log(this.userInfo.dataList);
				}
			} catch (error) {
				console.log('⛔ userMng fn_getUserList');
			}
		},
		// 🔅 유저 아이디 1개로 주소 정보 가져오기
		async fn_getUserAddr(userId, row) {
			try {
				await this.$store.dispatch('userStore/fn_getUserAddr', userId);
				if (this.getUserAddr) {
					console.log('🟢 userMng getUserAddr - userAddrInfo');
					console.log(this.userDetailInfo.addrList);
					this.userDetailInfo.addrList = this.getUserAddr;
					this.userDetailInfo.row = row;
				}
			} catch (error) {
				console.log('⛔ userMng fn_getUserAddr error =>' + error);
			}
		},
	},
	mounted() {
		this.fn_getUserList();
	},
};
</script>
