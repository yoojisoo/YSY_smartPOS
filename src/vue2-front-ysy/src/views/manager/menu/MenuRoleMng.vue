<!--
    @author 신민경
    @create 2022-07-06
    @desc   사용자별 메뉴 권한을 관리하는 페이지
-->

<template>
	<v-container class="main_layout_container">
		<v-row class="main_layout_row">
			<v-col class="main_layout_col" cols="12" md="10" lg="10" xl="10">
				<v-row justify="center" no-gutters style="height: inherit">
					<v-col cols="12">
						<v-breadcrumbs :items="breadCrumbsInfo">
							<template v-slot:divider>
								<v-icon>mdi-chevron-right</v-icon>
							</template>
						</v-breadcrumbs>
					</v-col>
					<v-col cols="12">
						<v-row align="start" justify="start" no-gutters>
							<v-col cols="3">
								<v-autocomplete label="사용자선택" :items="users" :item-text="itemText" item-value="userId" @input="findFilterMenuList"></v-autocomplete>
							</v-col>
							<v-col cols="12">
								<ysyGrid :gridInfo="gridInfo">
									<template v-slot:item.isUseAble="{ item }">
										<v-simple-checkbox v-model="item.isUseAble"> </v-simple-checkbox>
									</template>
								</ysyGrid>
							</v-col>
						</v-row>
					</v-col>
				</v-row>
			</v-col>
		</v-row>
	</v-container>
</template>

<script>
import { TheSystemBar, TheHeader, TheFooter } from '@/assets/import/index.js';
import ysyGrid from '@/components/FGrid.vue';

export default {
	components: {
		TheSystemBar,
		TheHeader,
		TheFooter,
		ysyGrid,
	},
	props: ['parentPageName'],
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
			breadCrumbsInfo: [{ text: this.parentPageName }, { text: '메뉴권한관리' }],
		};
	},

	mounted() {
		// 해당 유저 권한 이하의 사용자들을 불러옴
		this.findFilterUserList();
	},

	methods: {
		itemText: item => item.userNm + ' : ' + item.userId,

		// 해당 유저 권한 이하의 사용자들을 불러옴
		async findFilterUserList() {
			await this.$store.dispatch('userStore/findFilterUserList', this.getUserId).catch(error => {
				console.log('===============> userStore/findFilterUserList error');
				console.log(error);
				alert('페이지 접근 권한이 없습니다.');
				this.$router.replace('/');
			});

			if (this.getFilterUserList) this.users = this.getFilterUserList;
			else console.log('MenuRoleMng --- findFilterUserList() 실패 !!');
		},

		// 선택된 유저의 접근권한이 있는 메뉴 리스트를 불러옴
		async findFilterMenuList(userId) {
			await this.$store.dispatch('menuStore/findFilterMenuList', userId).catch(error => {
				console.log('===============> menuStore/findFilterMenuList error');
				console.log(error);
			});

			if (this.getFilterMenuList) this.gridInfo.dataList = this.getFilterMenuList;
			else console.log('MenuRoleMng --- findFilterMenuList() 실패 !!');
		},
	},

	computed: {
		getUserId() {
			return this.$store.state.authStore.loginData.user_id;
		},
		getFilterUserList() {
			return this.$store.state.userStore.filterUserList;
		},
		getFilterMenuList() {
			return this.$store.state.menuStore.filterMenuList;
		},
	},
};
</script>
