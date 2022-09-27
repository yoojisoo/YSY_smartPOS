<template>
	<!-- flat : remove box-shadow -->
	<div>
		<!-- PC 메뉴 start  -->
		<v-app-bar app flat outlined>
			<v-container class="ma-0 pa-0" fluid>
				<v-row justify="center" no-gutters>
					<v-col cols="12" md="10" lg="10" xl="10">
						<v-row justify="start" align="center" no-gutters>
							<v-col cols="12" md="2" lg="2" xl="1" class="pa-0 ma-0">
								<v-row justify="center" align="center" class="ma-0 pa-0" no-gutters>
									<v-col cols="2" class="ma-0 pa-0 hidden-md-and-up">
										<v-btn icon color="black" @click="drawer = true">
											<v-icon>mdi-menu</v-icon>
										</v-btn>
									</v-col>
									<v-col cols="8" style="text-align: center" class="ma-0 pa-0">
										<BaseButtonLogoSmall />
									</v-col>
									<v-col cols="2" class="ma-0 pa-0 hidden-md-and-up"></v-col>
								</v-row>
							</v-col>
							<v-col cols="9" class="ma-0 pa-0 hidden-sm-and-down">
								<!-- 2022 06 02 smk : tab in menu가 안돼서 버튼으로 변경 -->
								<v-tabs show-arrows v-model="active" :color=menuModeColor>
									<v-menu offset-y transition="slide-y-transition" content-class="menu_content elevation-0" v-for="(item, index) in headerMenu" :key="index">
										<template v-slot:activator="{ on }">
											<v-tab v-on="on" v-text="item.menu_nm" @mouseover="activeTab(index)" />
										</template>
										<v-list>
											<v-list-item v-for="(menus, index) in item.childMenus" :key="index" link>
												<v-list-item-title v-text="menus.menu_nm" @click="goPage(item, menus)" />
											</v-list-item>
										</v-list>
									</v-menu>
								</v-tabs>
							</v-col>
							<v-col cols="1" class="ma-0 pa-0 hidden-sm-and-down">
								<v-btn v-if="getMode == 'user'" text @click="modeChange">관리자메뉴보기</v-btn>
								<v-btn v-else-if="getMode == 'admin'" text @click="modeChange">사용자메뉴보기</v-btn>
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
						<v-btn plain @click="myInfo">내프로필</v-btn>
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

			<v-divider class="ma-0" />
			<v-row justify="center" no-gutters>
				<v-btn v-if="getMode == 'user'" block elevation="0" @click="modeChange">관리자메뉴보기</v-btn>
				<v-btn v-else-if="getMode == 'admin'" block elevation="0" @click="modeChange">사용자메뉴보기</v-btn>
			</v-row>
			<v-divider class="ma-0" />

			<v-list>
				<div v-for="item in headerMenu" :key="item.menu_nm">
					<!-- 하위 메뉴가 존재할때 -->
					<v-list-group v-if="item.childMenus.length > 0" :prepend-icon="item.menu_icon" no-action>
						<template v-slot:activator>
							<v-list-item-title v-text="item.menu_nm" />
						</template>

						<v-list-item v-for="menus in item.childMenus" :key="menus.menu_nm" link>
							<v-list-item-content>
								<v-list-item-title v-text="menus.menu_nm" @click="goPage(item, menus)" />
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
							<v-list-item-title v-text="item.menu_nm" @click="goPage(item, menus)" />
						</v-list-item-content>
					</v-list-item>
				</div>
			</v-list>
		</v-navigation-drawer>
	</div>
</template>

<script>
import { BaseButtonLogoSmall, YsyUtil } from '@/assets/import/index.js';
import store from '@/store/index';
import { eventBus } from '@/main.js';

export default {
	name: 'main-header',
	props: ['parentPage'],
	components: { BaseButtonLogoSmall },
	data: () => ({
		menuModeColor: '',
		active: null,
		isOpen: false,
		menuList: [],
		windowWidth: 0,
		drawer: null, // 모바일 메뉴 on off -> true : on
		eventData: 'header event data',
		appTitle: 'Title',
		group: true,
		headerMenu: null,
		row: null,
		iconImg: 'mdi-dots-vertical',
		userLogin: false,
	}),
	mounted() {
		this.findMenuList();
		this.windowWidth = window.innerWidth; // 현재 화면 사이즈
		window.addEventListener('resize', this.viewResize); // 화면 resize 이벤트, 실행함수 추가
	},

	watch: {
		windowWidth() {
			// windowWidth 라는 값이 변경되면 실행
			this.drawer = false;
		},
		userLogin() {
			this.findMenuList();
		},
	},

	computed: {
		isLogin() {
			if (this.$store.state.authStore.loginData.isLogin) this.userLogin = true;
			return this.$store.state.authStore.loginData.isLogin;
		},
		getUserId() {
			return this.$store.state.authStore.loginData.user_id;
		},
		getUserName() {
			return this.$store.state.authStore.loginData.user_name;
		},
		getUserMenuList() {
			return this.$store.state.menuStore.userMenuList;
		},
		getAdminMenuList() {
			return this.$store.state.menuStore.adminMenuList;
		},
		getErrorMsg() {
			return this.$store.state.menuStore.errorMsg;
		},
		getMode() {
			return this.$store.state.menuStore.mode;
		}
	},
	methods: {
		activeTab(index) {
			this.active = index;
		},
		myInfo() {
			this.$router.push({ name: 'myInfo' }).catch(() => {});
		},
		logout() {
			this.$store.dispatch('authStore/clearUserInfo'); // 로그아웃 함수 실행
			this.drawer = false; // drawer 닫아줌
			this.$router.replace({ name: 'home' }).catch(() => { }); // home으로 이동
			location.reload(); // 다시 drawer 켰을때 메뉴 reload를 위해서 미리 실행
		},
		modeChange() {
			this.$store.dispatch('menuStore/modeChange');

			if (this.getMode == 'user') {
				this.menuModeColor = '';
				this.menuList = this.getUserMenuList;
			} else if (this.getMode == 'admin') {
				this.menuModeColor = 'deep-orange';
				this.menuList = this.getAdminMenuList;
			}
			this.setHeaderMenu();
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
			var parentList = [];
			var childList = [];
			this.menuList.forEach(x => {
				if (x.p_menu_id == null || x.p_menu_id == '') {
					// home 메뉴 구성
					parentList.push(x);
				} else if (x.p_menu_id != null || x.p_menu_id != '') {
					childList.push(x);
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
			console.log(' ⬇️ this.headerMenu ⬇️ ');
			console.log(this.headerMenu);
			console.log(' ⬆️ this.headerMenu ⬆️ ');
		},
		async findMenuList() {
			let params = {
				url: 'ysy/v1/findMenuList',
			};
			await store.dispatch('menuStore/findMenuList', params);
			if (this.getMode == 'user') {
				if (this.getUserMenuList) {
					this.menuList = this.getUserMenuList;
					this.setHeaderMenu();
				} else {
					alert(this.getErrorMsg);
					console.log('this.findMenuList 실패 !!');
				}
			} else if (this.getMode == 'admin') {
				if (this.getAdminMenuList) {
					this.menuList = this.getAdminMenuList;
					this.setHeaderMenu();
				} else {
					alert(this.getErrorMsg);
					console.log('this.findMenuList 실패 !!');
				}
			} else {
				YsyUtil.log('🧨 TheHeader → findMenuList ERROR !! [this.getMode undefined]')
			}
		},
		goPage(parent, child) {
			if (parent !== undefined && parent !== '' && parent !== null && child !== undefined && child !== '' && child !== null) {
				this.$emit('menuClick', parent.menu_nm);
				this.$router.push({ path: child.menu_path });
			}
		},
	},

	beforeDestroy() {
		window.removeEventListener('resize', this.viewResize); // 화면 resize 이벤트, 실행함수 제거
	},
};
</script>
