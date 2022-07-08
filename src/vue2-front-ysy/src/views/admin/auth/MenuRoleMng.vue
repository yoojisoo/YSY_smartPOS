<!--
    @author 신민경
    @create 2022-07-06
    @desc   사용자별 메뉴 권한을 관리하는 페이지
-->

<template>
	<v-app>
		<mainSystemBar />
		<mainHeader :pageName="pageName" />
		<v-main>
			<v-container class="main_layout_container">
				<v-row class="main_layout_row">
					<v-col class="main_layout_row" clos="12" md="8" lg="8" xl="8">
						<v-row justify="center" no-gutters style="height: inherit">
							<v-col cols="12">
								<v-row align="start" justify="start" no-gutters>
									<v-col cols="3">
										<v-autocomplete
											label="사용자선택"
											:items="users"
											:item-text="itemText"
											item-value="user_id"
											@input="findUserMenuList"
										></v-autocomplete>
									</v-col>
									<v-col cols="12">
										<ysyGrid :gridInfo="gridInfo" />
									</v-col>
								</v-row>
							</v-col>
						</v-row>
					</v-col>
				</v-row>
			</v-container>
		</v-main>
		<v-footer class="ma-0 pa-0" fixed app>
			<mainFooter />
		</v-footer>
	</v-app>
</template>

<script>
import mainSystemBar from '@/components/header/TheSystemBar.vue';
import mainHeader from '@/components/header/TheHeader.vue';
import mainFooter from '@/components/footer/TheFooter.vue';
import ysyGrid from '@/components/YsyGrid.vue';
import { mapGetters } from 'vuex';

export default {
	components: {
		mainSystemBar,
		mainHeader,
		mainFooter,
		ysyGrid,
	},

	data() {
		return {
			pageName: 'menuRoleManager',
			pageNameKo: '메뉴권한관리',
			users: [],
			gridInfo: {
				dataList: [],
				headers: [
					{ text: '상위메뉴 아이디', value: 'p_menu_id', width: '25%' },
					{ text: '하위메뉴 아이디', value: 'menu_id', width: '25%' },
					{ text: '메뉴이름', value: 'menu_nm', width: '25%' },
					{ text: '접근권한', value: 'isUseAble', width: '25%' },
				],
				gridNm: '접근가능메뉴',
				gridDense: true,
				useBtn: false,
				rowCnt: 100,
			},
		};
	},

	mounted() {
		// 해당 유저 권한 이하의 사용자들을 불러옴
		this.findFilterUserList();
	},

	methods: {
		itemText: item => item.user_nm + ' : ' + item.user_id,

		// 해당 유저 권한 이하의 사용자들을 불러옴
		async findFilterUserList() {
			await this.$store.dispatch('userStore/findFilterUserList', this.userId).catch(error => {
				console.log('===============> userStore/findFilterUserList error');
				console.log(error);
			});

			if (this.getFilterUserList) this.users = this.getFilterUserList;
			else console.log('MenuRoleMng --- findFilterUserList() 실패 !!');
		},

		// 선택된 유저의 접근권한이 있는 메뉴 리스트를 불러옴
		async findUserMenuList(userId) {
			await this.$store.dispatch('userStore/findUserMenuList', userId).catch(error => {
				console.log('===============> userStore/findUserMenuList error');
				console.log(error);
			});

			if (this.getMenuList) this.gridInfo.dataList = this.getMenuList;
			else console.log('MenuRoleMng --- findUserMenuList() 실패 !!');
		},
	},

	computed: {
		...mapGetters({ userId: 'authStore/getUserId' }),
		...mapGetters({ getFilterUserList: 'userStore/getFilterUserList' }),
		...mapGetters({ getMenuList: 'userStore/getMenuList' }),
	},
};
</script>
