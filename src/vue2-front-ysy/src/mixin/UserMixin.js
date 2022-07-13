/**
 * @author 신민경
 * @create date 2022-07-12
 * @desc   User 관련 Mixin
 */

import { mapGetters } from 'vuex';

const UserMixin = {
	data() {
		return {
			userDetailInfo: {},
		};
	},
	computed: {
		...mapGetters({ getUserDetail: 'userStore/getUserDetail' }),
		...mapGetters({ getUserList: 'userStore/getUserList' }),
	},
	methods: {
		async fn_userClick(row, gridNm) {
			await this.$store.dispatch('userStore/fn_getUserDetail', row.userId);

			if (this.getUserDetail) {
				console.log('🟢 userMng rowClick');
				this.userDetailInfo = this.getUserDetail;
				console.log(this.userDetailInfo);
				console.log(this.userDetailInfo.userId);
				console.log('🔴 userMng rowClick');
			} else {
				console.log('❌ userMng rowClick ❌');
			}
		},
		fn_userDbClick(row, gridNm) {},

		async fn_getUserList() {
			await this.$store.dispatch('userStore/fn_getUserList');

			if (this.getUserList) {
				console.log('🟢 userMng fn_getUserList');
				this.userInfo.dataList = this.getUserList;
				console.log(this.userInfo.dataList);
				console.log('🔴 userMng fn_getUserList');
			} else {
				console.log('❌ userMng fn_getUserList ❌');
			}
		},
	},
};

export default UserMixin;
