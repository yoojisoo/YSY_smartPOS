<!-- main 화면 -->
<template>
	<v-app>
		<!-- Header Start -->
		<mainSystemBar />
		<mainHeader :pageName="pageName" />
		<!-- Header End -->

		<!-- Main Start -->
		<v-main>
			<v-container fluid pa-0 ma-0 style="background-color: lightgray">
				<v-row justify="center" no-gutters>
					<v-col cols="12" md="8" lg="8" xl="8" style="background-color: lightslategray">
						<v-row justify="center" no-gutters style="height: inherit">
							<v-col :cols="filterCols" class="hidden-xs-only">
								<v-card
									v-if="filterShow"
									height="100%"
									tile
									color="indigo"
									flat
								></v-card>
							</v-col>
							<v-col :cols="mainCols">
								<!--<v-row justify="center" no-gutters>
									<v-col align-self="center" class="hidden-xs-only">
										<v-btn
										class="ma-2 hidden-xs-only"
										@click="colsChange"
										>
										web filter
										</v-btn>
									</v-col>
									<v-col align-self="center" class="hidden-sm-and-up">
										<v-btn
										class="ma-2"
										@click="colsChange"
										>
										mobile filter 
										</v-btn>
									</v-col>
								</v-row>-->
								<v-row justify="center" no-gutters>
									<v-col clos="12">
										<pageHistory :pageNameKo="pageNameKo" />
									</v-col>
								</v-row>
								<v-row align="start" justify="center" no-gutters>
									<v-col cols="12"
										><ysyGrid :headers="gridHeaders" :userInfo="userData"
									/></v-col>
									<v-col cols="6"
										><ysyGrid :headers="gridHeaders" :userInfo="userData"
									/></v-col>
									<v-col cols="6"
										><ysyGrid :headers="gridHeaders" :userInfo="userData"
									/></v-col>
								</v-row>
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
import mainFooter from '@/components/TheFooter.vue';
import ysyGrid from '@/components/YsyGrid.vue';
import pageHistory from '@/components/PageHistory.vue';
import { mapGetters } from 'vuex';

export default {
	components: {
		mainSystemBar,
		mainHeader,
		mainFooter,
		ysyGrid,
		pageHistory,
	},
	data() {
		return {
			filterCols: 0,
			mainCols: 12,
			filterShow: false,
			pageName: 'home',
			pageNameKo: '메인페이지',
			config: {
				headers: {
					access_token: this.$store.state.authStore.loginData.userToken,
				},
			},
			// gridHeaders: [
			// 	{ text: '아이디'     , value: 'userId'},
			// 	{ text: '비밀번호'   , value: 'userPw'},
			// 	{ text: '전화번호'   , value: 'userPhone'},
			// 	{ text: '그룹'       , value: 'userGroup'},
			// 	{ text: '비즈코드'   , value: 'bizCode'},
			// 	{ text: '날짜'       , value: 'dateVal'},
			// ],
		};
	},
	methods: {
		login() {
			this.$router.replace('/signIn');
		},
		logout() {
			this.$store.commit('clearUserInfo');
			this.$router.replace('/signIn');
		},
		colsChange() {
			// 필터와 메인 그리드 cols 변경 (아이콘 눌렀을때)
			this.filterShow = !this.filterShow;

			if (this.filterCols < 2) this.filterCols = 2;
			else this.filterCols = 2;

			if (this.mainCols < 12) this.mainCols = 12;
			else this.mainCols = 10;
		},

		getUserData() {
			this.$store.dispatch('getUserData');
		},
	},
	computed: {
		isLogin() {
			return this.$store.getters.isLogin;
		},
		userId() {
			return this.$store.state.authStore.loginData.userId;
		},

		gridHeaders() {
			console.log('computed - gridHeaders() -------->');
			return this.$store.state.gridStore.gridHeaders;
		},
		...mapGetters({
			userData: 'getUserData',
		}),
	},
	mounted() {
		this.getUserData();
	},
};
</script>
