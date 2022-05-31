<template>
	<!-- flat : remove box-shadow -->
	<div>
		<!-- web 메뉴 start -->
		<v-app-bar color="purple lighten-2" app dense flat>
			<v-container class="ma-0 pa-0" fluid>
				<v-row justify="center" no-gutters>
					<v-col cols="12" xs="12" sm="12" md="8" lg="8" xl="8">
						<v-row justify="center" align="center" no-gutters>
							<v-col cols="12" xs="12" sm="2" md="2" lg="2" xl="2" class="pa-0 ma-0">
								<v-row justify="center" align="center" class="ma-0 pa-0" no-gutters>
									<v-col cols="2" class="ma-0 pa-0 hidden-sm-and-up">
										<v-btn icon color="black" @click="drawer = true">
											<v-icon>mdi-format-list-bulleted-square</v-icon>
										</v-btn>
									</v-col>
									<v-col
										cols="10"
										md="12"
										style="text-align: center"
										class="ma-0 pa-0"
									>
										<v-btn class="ma-0 pa-0 ml-n6" plain to="/">
											<v-img
												contain
												height="30"
												width="200"
												src="@/assets/img/svg/logoWhite.svg"
											/>
										</v-btn>
									</v-col>
								</v-row>
							</v-col>
							<v-col cols="10" class="ma-0 pa-0 hidden-xs-only">
								<v-tabs centered dark background-color="purple lighten-1">
									<v-tab
										v-for="(item, idx) in headerMenu"
										:key="idx"
										:to="item.path"
									>
										{{ item.name }}
									</v-tab>
								</v-tabs>
							</v-col>
						</v-row>
					</v-col>
				</v-row>
			</v-container>
		</v-app-bar>

		<!-- mobile navigation -->
		<navigationDrawer :drawer="drawer" :headerMenu="headerMenu" />
	</div>
</template>

<script>
import v_menus from '@/assets/common/vMenus.js';
import { eventBus } from '@/main.js';
import navigationDrawer from '@/components/header/TheNavigationDrawer.vue';

export default {
	name: 'main-header',
	props: ['pageName'],
	components: { navigationDrawer },

	data: () => ({
		windowWidth: 0, // 화면 사이즈 변경되면 drawer false 처리하기 위해서 저장
		drawer: null,
		eventData: 'header event data',
		appTitle: 'Title',
		group: true,
		headerMenu: null,
		row: null,
		iconImg: 'mdi-dots-vertical',
	}),

	mounted() {
		this.getMenuList();
		this.windowWidth = window.innerWidth; // 현재 화면 사이즈
		window.addEventListener('resize', this.viewResize); // 화면 resize 이벤트, 실행함수 추가
	},

	beforeDestroy() {
		window.removeEventListener('resize', this.viewResize); // 화면 resize 이벤트, 실행함수 제거
	},

	watch: {
		windowWidth() {
			// windowWidth 라는 값이 변경되면 실행
			this.drawer = false;
		},
	},

	methods: {
		// 화면 가로 사이즈가 변경될때 값 변경
		viewResize() {
			this.windowWidth = window.innerWidth;
		},

		headerMenuClick() {
			console.log('TheHeader -> headerMenuClick data: ' + this.eventData);
			eventBus.$emit('headerTabCallback', this.eventData);
		},

		doMouseOver() {
			this.iconImg = 'mdi-heart';
			console.log(this.iconImg);
		},

		doMouseOut() {
			this.iconImg = 'mdi-dots-vertical';
			console.log(this.iconImg);
		},

		setHeaderMenu() {
			console.log('headerMenuFilter start');
			console.log(v_menus);
			console.log('this.pageValue = ' + this.pageName);
			console.log(this.pageName.indexOf('admin') > -1);

			var list = [];
			v_menus.forEach(x => {
				if (this.pageName === 'admin' && x.isAdmin === 'Y' && x.pmenuId == null) {
					// admin 메뉴 구성
					list.push(x);
				} else if (
					this.pageName === 'home' &&
					x.isAdmin === 'N' &&
					(x.pmenuId == null || x.pmenuId == '')
				) {
					// home 메뉴 구성
					list.push(x);
				}
			});
			this.headerMenu = [];
			this.headerMenu = list;
			console.log('this.headerMenu=');
			4;
			console.log(this.headerMenu);
			console.log('header mounted end');
		},

		async getMenuList() {
			console.log('getMenuList start');
			if (v_menus.length <= 0) {
				var res1 = await this.$axios.get('ysy/v1/menu/getMenuList');
				var list = res1.data;
				console.log('list => ' + list);
				for (var i = 0; i < list.length; i++) {
					var menuData = {};
					menuData.path = list[i].menu_path;
					menuData.name = list[i].menu_nm;
					menuData.menuId = list[i].menu_id;
					menuData.menuSeq = list[i].menu_seq;
					menuData.icon = 'mdi-home';
					menuData.component = () => import(list[i].menu_pull_path);
					menuData.pmenuId = list[i].p_menu_id;
					menuData.isAdmin = list[i].is_admin;
					v_menus.push(menuData);
				}
			}

			console.log(v_menus);

			this.setHeaderMenu();
		},
	},
};
</script>

<style scoped>
.v-container {
	max-width: 70%;
	height: 100%;
}
</style>
