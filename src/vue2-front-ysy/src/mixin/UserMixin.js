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
				console.log('🟢 UserMixin fn_userClick');
				this.userDetailInfo = this.getUserDetail.obj;
				console.log('🔴 UserMixin fn_userClick');
			} else {
				console.log('❌ UserMixin fn_userClick ❌');
			}
		},
		fn_userDbClick(row, gridNm) {},
	},
};

export default UserMixin;
