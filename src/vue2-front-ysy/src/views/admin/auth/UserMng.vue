<template>
	<v-app>
		<!-- Header Start -->
		<mainSystemBar />
		<mainHeader :pageName="pageName" />
		<!-- Header End -->

		<!-- Main Start -->
		<v-main>
			<v-container fluid pa-0 ma-0>
				<v-row justify="center" no-gutters>
					<v-col cols="12" md="8" lg="8" xl="8">
						<v-row justify="center" no-gutters style="height: inherit">
							<!--<v-col cols="12">
								<pageHistory :pageNameKo="pageNameKo" />
							</v-col>-->
							<v-col cols="12">
								<ysyGrid :gridInfo="userInfo" />
							</v-col>
						</v-row>
					</v-col>
				</v-row>
			</v-container>
		</v-main>
		<!-- Main End -->

		<!-- Footer Start -->
		<v-footer class="ma-0 pa-0" fixed app>
			<mainFooter />
		</v-footer>
		<!-- Footer End -->
	</v-app>
</template>

<script>
import mainSystemBar from '@/components/header/TheSystemBar.vue';
import mainHeader from '@/components/header/TheHeader.vue';
import mainFooter from '@/components/footer/TheFooter.vue';
import ysyGrid from '@/components/YsyGrid.vue';
import { mapGetters, mapActions } from 'vuex';
import userService from '@/service/auth/UserService.js';

export default {
	components: {
		mainSystemBar,
		mainHeader,
		mainFooter,
		ysyGrid,
	},
	data() {
		return {
			pageName: 'User Manager',
			userInfo: {
				dataList: [],
				headers: [
					{ text: '아이디', value: 'username', key: true },
					{ text: '전화번호', value: 'addressList.phone1' },
					{ text: '이름', value: 'name' },
					{ text: '날짜', value: 'regDt' },
					{ text: '권한', value: 'roleList' },
				],
				dateGubun: '/',
				gridNm: '사용자 관리',
				path: '/userMng',
				isCheckBox: true,
				isSingleSelect: false,
				rowCnt: 10,
				gridDense: true,
				isDetail: true,
				rowClick: (row, gridNm) => {
					this.rowClick(row, gridNm);
				}, //로우 클릭 이벤트 콜백
				rowDbClick: (row, gridNm) => {
					this.rowDbClick(row, gridNm);
				}, //로우 더블클릭 이벤트 콜백
			},
		};
	},
	computed: {
		...mapGetters({getUserList: 'userStore/getUserList'}),
	},

	mounted() {
		this.setUserList();
	},

	methods: {
		rowClick(row, gridNm) {},
		rowDbClick(row, gridNm) {},
		async setUserList() {
			await userService.setUserList();
			if(this.getUserList) {
				this.userInfo.dataList = this.getUserList;
			} else {
				console.log('this.getUserList 실패 !!');
			}
		},
	},
};
</script>

<style>
@import '../../../assets/css/global_layout.css';
</style>
