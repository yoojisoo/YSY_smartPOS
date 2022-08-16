<!--
	@author 👻s_plus7
 	@create date 2022-08-11
	@desc   사용자 관리 페이지 HTML 레이아웃
 -->
<template>
	<v-container class="main_layout_container">
		{{ this.$vuetify.breakpoint.name }}
		<v-row class="main_layout_row">
			<v-col cols="3" md="2" class="main_layout_col hidden-sm-and-down">
				<TheLeftCondition :filters="userInfo.headers" />
			</v-col>

			<v-col cols="12" md="7" class="main_layout_col">
				<Y2sGrid :gridInfo="userInfo" :gridDataList="getUserList" />
			</v-col>

			<v-col cols="3" md="3" v-if="isAddr" class="main_layout_col hidden-sm-and-down">
				<!-- <TheDetailInfo :userDetailInfo="userDetailInfo" /> -->
			</v-col>
		</v-row>
	</v-container>
</template>

<!--
	1.setup 함수
		composition api setup() 함수 : beforeCreate , create의 life cycle 포함
		나머지 라이프 사이플은 onXXX function으로 사용

	2.setup 함수의 매개변수
		- props
		- context : attrs, slots, emit, parent, root 속성
-->
<script>
import { computed, getCurrentInstance, onMounted, onUnmounted, reactive, toRefs, ref } from 'vue';
import { TheLeftCondition, TheDetailInfo } from '@/assets/import/index.js';
import Y2sGrid from '@/components/Y2sGrid.vue';
import UserStore from '@/store/modules/UserStore';

export default {
	components: {
		TheLeftCondition,
		TheDetailInfo,
		Y2sGrid,
	},
	props: [],	
	
	setup(props, context) {
		const vm = getCurrentInstance(); //이거 잘 모름

		// 💠data💠
		const state = reactive({
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
        });


		// 💠computed💠
		const getUserList = computed(() => { return UserStore.state.userList });
		const getUserAddr = computed(() => { return UserStore.state.userAddr });

		// console.log('🔅🔅🔅🔅🔅UserStore.state -> ', UserStore.state);
		// console.log('🔅🔅🔅🔅🔅UserStore.state.userList -> ', UserStore.state.userList);

		// 💠methods💠  
		// 🔅 모든 유저 리스트 가져오기
		// ✔️방법 1
		// const fn_getUserList = async() => {
		// 	try {
		// 		await this.$store.dispatch('userStore/fn_getUserList');
		// 		if (this.getUserList) {
		// 			console.log('🟢 userMng fn_getUserList');
		// 			this.userInfo.dataList = this.getUserList;
		// 			console.log(this.userInfo.dataList);
		// 		}
		// 	} catch (error) {
		// 		console.log('⛔ userMng fn_getUserList');
		// 	}
		// }

		// ✔️방법 2
		async function fn_getUserList() {
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
		}

		// 🔅 유저 아이디 1개로 주소 정보 가져오기
		async function fn_getUserAddr(userId, row) {
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
		}

		//마운티드는 아직 잘 모르겠음
		// onMounted(() => {
		// 	fn_getUserList();
		// 	console.log('🔅🔅🔅🔅🔅마운티드');
		// })

		// state , function return
		 return {
            ...toRefs(state), //toRefs으로 destructuring 해줌
            getUserList,
			getUserAddr,
			fn_getUserList, //return 안해도 돼서 해야되는 이유 모르겠음... return이 없는 타입이여서 그런지?
			fn_getUserAddr,
        };
	},
	mounted() {
		this.fn_getUserList();
		console.log('🔅🔅🔅🔅🔅마운티드');
	},
};
</script>
