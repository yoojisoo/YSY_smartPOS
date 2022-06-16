<!-- amdin -> 사용자관리 페이지 -->
<template>
	<v-app>
		<main-system-bar />
		<main-header />

		<!-- {{ this.$vuetify.breakpoint.name }} -->
		<v-main>
			<v-container>
				<v-row align="start" justify="center" no-gutters>
					<v-col cols="12">
						<ysy-grid :gridInfo="userMngInfo" />
					</v-col>
				</v-row>
				<v-row align="start" justify="center" no-gutters>
					<v-col cols="12">
						<ysy-grid :gridInfo="userMngInfo" />
					</v-col>
				</v-row>
				<v-row align="start" justify="center" no-gutters>
					<v-col cols="12">
						<ysy-grid :gridInfo="userMngInfo" />
					</v-col>
				</v-row>
			</v-container>
		</v-main>

		<v-footer class="ma-0 pa-0" fixed app>
			<main-footer />
		</v-footer>
	</v-app>
</template>

<script>
import mainSystemBar from '@/components/header/TheSystemBar.vue';
import mainHeader from '@/components/header/TheHeader.vue';
import mainFooter from '@/components/TheFooter.vue';
import userMng from '@/views/admin/UserMng.vue';
import ysyGrid from '@/components/YsyGrid.vue';
// import pageHistory from '@/components/PageHistory.vue';

export default {
	components: {
		mainSystemBar,
		mainHeader,
		mainFooter,
		userMng,
		ysyGrid,
		// pageHistory
	},
	data() {
		return {
			userMngInfo: {
				dataList: [],
				headers: [
					{ text: '아이디',   value: 'username', key: true },
					{ text: '전화번호', value: 'addressList.phone1' },
					{ text: '이름',     value: 'name' },
					{ text: '날짜',     value: 'regDt' },
					{ text: '권한',     value: 'roleList' },
				],
				dateGubun: '/',
				gridNm: '사용자 관리',
				path: '/userMng',
				isCheckBox: true,
				isSingleSelect: false,
				rowCnt: 5,
				gridDense: true,
				isDetail: false,
				rowClick: (row, gridNm) => { this.rowClick(row, gridNm) }, //로우 클릭 이벤트 콜백
				rowDbClick: (row, gridNm) => { this.rowDbClick(row, gridNm) }, //로우 더블클릭 이벤트 콜백
			},
			async setUserMngList() {
				await this.$store.dispatch('findUserList');
				if (this.getUserMgnList) {
					this.userMngInfo.dataList = this.getUserMgnList;
				}
			},
		};
	},
	methods: {
		rowClick(row, gridNm) {
		},
		rowDbClick( row, gridNm ) {
		},
	},
	computed: {
		getUserMgnList() {
			return this.$store.state.userStore.userList;
		},
	},
	mounted() {
		this.setUserMngList();
	},
};
</script>

<style>
</style>