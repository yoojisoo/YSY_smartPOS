<template>
	<!-- flat : remove box-shadow -->
	<div class="mb-3">
		<!-- web 메뉴 start -->
		<v-app-bar app flat color="white">
			<v-container class="ma-0 pa-0" fluid>
				<v-row justify="center" no-gutters>
					<v-col cols="12" md="8" lg="8" xl="8">
						<v-row justify="start" align="center" no-gutters>
							<v-col cols="12" md="2" lg="2" xl="1" class="pa-0 ma-0">
								<v-row justify="center" align="center" class="ma-0 pa-0" no-gutters>
									<v-col cols="2" class="ma-0 pa-0 hidden-md-and-up">
										<v-btn icon color="black" @click="drawer = true">
											<v-icon>mdi-menu</v-icon>
										</v-btn>
									</v-col>
									<v-col cols="8" style="text-align: center" class="ma-0 pa-0">
										<v-btn
											:ripple="false"
											block
											text
											class="btn_remove_bg"
											to="/"
										>
											<v-img
												contain
												height="30"
												width="60"
												src="@/assets/img/svg/logoWhite.svg"
											/>
										</v-btn>
									</v-col>
									<v-col cols="2" class="ma-0 pa-0 hidden-md-and-up"></v-col>
								</v-row>
							</v-col>
							<v-col cols="10" class="ma-0 pa-0 hidden-sm-and-down">
								<!-- 2022 06 02 smk : menu in tab이 안돼서 버튼으로 변경 -->
								<v-menu offset-y v-for="(item, idx) in headerMenu" :key="idx" tile>
									<template v-slot:activator="{ on }">
										<v-btn v-on="on" plain @click="goPage(item.menu_path)">
											{{ item.menu_nm }}
										</v-btn>
									</template>
									<v-list v-if="item.childMenus.length > 0">
										<v-list-item
											v-for="(menus, idx) in item.childMenus"
											:key="idx"
											link
										>
											<v-list-item-title
												v-text="menus.menu_nm"
												@click="goPage(menus.menu_path)"
											></v-list-item-title>
										</v-list-item>
									</v-list>
								</v-menu>
							</v-col>
						</v-row>
					</v-col>
				</v-row>
			</v-container>
		</v-app-bar>

		<!-- mobile navigation -->
		<v-navigation-drawer v-model="drawer" absolute temporary app>
			<div class="mt-2" v-if="isLogin">
				<v-list-item>
					<v-list-item-avatar>
						<v-icon>mdi-account</v-icon>
					</v-list-item-avatar>

					<v-list-item-content>
						<v-list-item-title> {{ getUserName }}님 </v-list-item-title>
						<v-list-item-subtitle> {{ getUserId }} </v-list-item-subtitle>
					</v-list-item-content>
				</v-list-item>
				<v-row class="mb-3" justify="space-between" no-gutters>
					<v-col style="text-align-last: center">
						<v-btn plain to="/userInfo">내프로필</v-btn>
					</v-col>
					<v-col style="text-align-last: center">
						<v-btn plain @click="logout">로그아웃</v-btn>
					</v-col>
				</v-row>
			</div>

			<div class="mt-2" v-else>
				<v-list-item>
					<v-list-item-content>
						<v-list-item-title>로그인이 필요합니다.</v-list-item-title>
					</v-list-item-content>
				</v-list-item>
				<v-row class="mb-3" justify="space-between" no-gutters>
					<v-col style="text-align-last: center">
						<v-btn block plain to="/signIn"> 로그인 </v-btn>
					</v-col>
				</v-row>
			</div>

			<v-divider />

			<v-list>
				<div v-for="item in headerMenu" :key="item.menu_nm">
					<!-- 하위 메뉴가 존재할때 -->
					<v-list-group
						v-if="item.childMenus.length > 0"
						:prepend-icon="item.menu_icon"
						no-action
					>
						<template v-slot:activator>
							<v-list-item-title v-text="item.menu_nm" />
						</template>

						<v-list-item v-for="menus in item.childMenus" :key="menus.menu_nm" link>
							<v-list-item-content>
								<v-list-item-title
									v-text="menus.menu_nm"
									@click="goPage(menus.menu_path)"
								/>
							</v-list-item-content>
						</v-list-item>
						<v-divider />
					</v-list-group>

					<!-- 하위 메뉴가 존재하지 않을때 -->
					<v-list-item v-else :to="item.menu_path">
						<v-list-item-icon>
							<v-icon> {{ item.menu_icon }} </v-icon>
						</v-list-item-icon>
						<v-list-item-content>
							<v-list-item-title
								v-text="item.menu_nm"
								@click="goPage(menus.menu_path)"
							/>
						</v-list-item-content>
					</v-list-item>
				</div>
			</v-list>
		</v-navigation-drawer>
	</div>
</template>

<script>
import store from '@/store/index';
import { eventBus } from '@/main.js';
import { mapGetters } from 'vuex';
import navigationDrawer from '@/components/header/TheNavigationDrawer.vue';

const authStore = 'authStore';

export default {
	name: 'main-header',
	props: ['pageName'],
	components: { navigationDrawer },

	data: () => ({
		menuList: [],
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
		this.setMenuList();
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

	computed: {
		...mapGetters(authStore, ['isLogin', 'getUserId', 'getUserName']),
		...mapGetters({ getMenuList: 'menuStore/getMenuList' }),
	},

	methods: {
		logout() {
			this.$store.commit('clearUserInfo');
			this.$router.replace('/signIn');
		},
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
			console.log(' ↓↓↓ headerMenuFilter Start ↓↓↓');
			console.log(' →→→ this.pageValue = ' + this.pageName);

			var parentList = [];
			var childList = [];
			this.menuList.forEach(x => {
				if (this.pageName === 'admin' && x.is_admin === 'Y' && x.p_menu_id == null) {
					// admin 메뉴 구성
					parentList.push(x);
				} else {
					if (
						//this.pageName === 'home' &&
						//x.isAdmin === 'N' &&
						//(x.pmenuId == null || x.pmenuId == '')
						x.p_menu_id == null ||
						x.p_menu_id == ''
					) {
						// home 메뉴 구성
						parentList.push(x);
					} else if (
						//this.pageName === 'home' &&
						//x.isAdmin === 'N' &&
						//(x.pmenuId != null || x.pmenuId != '')
						x.p_menu_id != null ||
						x.p_menu_id != ''
					) {
						childList.push(x);
					}
				}
			});

			parentList.forEach(h => {
				h.childMenus = [];
				childList.forEach(c => {
					if (c.p_menu_id === h.menu_id) {
						h.childMenus.push(c);
					}
				});
			});

			this.headerMenu = [];
			this.headerMenu = parentList;

			console.log(' ↓↓↓ this.headerMenu ↓↓↓ ');
			console.log(this.headerMenu);
			console.log(' ↑↑↑ this.headerMenu ↑↑↑ ');
			console.log(' ↑↑↑ headerMenuFilter End ↑↑↑ ');
		},

		async setMenuList() {
			await store.dispatch('menuStore/findMenuList');

			if (this.getMenuList) {
				this.menuList = this.getMenuList;
				this.setHeaderMenu();
			} else {
				console.log('this.getMenuList 실패 !!');
			}
		},

		goPage(path) {
			if (path !== undefined && path !== '' && path !== null) {
				this.$router.push({ path: path });
			}
		},
	},
};
</script>
