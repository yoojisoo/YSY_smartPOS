<template>
	<v-card outlined tile class="overflow-y-auto" height="100%">
		<v-card-title>
			<span> 유저상세정보 </span>
			<v-spacer />
			<v-card-actions class="pa-0">
				<v-btn v-if		="btnDetailText === '수정'" outlined height="35">수정</v-btn>
				<v-btn v-else-if="btnDetailText === '저장'" outlined height="35">저장</v-btn>
			</v-card-actions>
		</v-card-title>
		<v-card-text>
			<v-row align="center" class="mb-1">
				<v-col cols="4" class="py-0 mt-2">
					<div v-for="subTitle in subTitles" :key="subTitle" style="height: 26px;">
						{{ subTitle }}
					</div>
				</v-col>
				<v-col cols="8" class="py-0">
					<v-text-field
						v-for="n in listLength" :key="n"
						v-model="datas[n-1]"
						dense
						hide-details
						:disabled="isDisabled"
						class="my-0"
					></v-text-field>
				</v-col>
			</v-row>
		</v-card-text>

		<v-sheet>
			<v-card-title class="pb-3" style="height: 65px;">
				<span> 유저주소정보 </span>
				<v-spacer />
				<v-card-actions class="pa-0" >
					<v-btn v-if="userDetailInfo.addrList.length > 0" outlined height="35">추가</v-btn>
				</v-card-actions>
			</v-card-title>

			<v-card-text class="ma-0 pa-0">
				<div v-if="userDetailInfo.addrList">
					<span v-for="addr in userDetailInfo.addrList" :key="addr.id">
						<TheAddrEdit :addr="addr" />
					</span>
				</div>
	
				<div v-if="userDetailInfo.addrList.length <= 0">
					<v-divider class="ma-0 pa-0" />
					<div class="mx-4">
						<v-btn outlined height="35" class="mt-5" block min-height="50">주소지 추가</v-btn>
					</div>
				</div>
			</v-card-text>

		</v-sheet>

	</v-card>
</template>

<script>
import { TheAddrEdit } from '@/assets/util/importFile.js';
import { YsyUtil } from '@/mixin/MixinGlobal.js';

export default {
	props: ['userDetailInfo'],
	mixins: [YsyUtil],
	components: {
		TheAddrEdit,
	},
	data() {
		return {
			btnDetailText: '수정',
			isDisabled: false,
			subTitles: [],
			datas: [],
			listLength: 0,
		};
	},
	methods: {
		fn_init() {
			this.datas = [
				this.userDetailInfo.row.userId, this.userDetailInfo.row.modDt, this.userDetailInfo.row.isEmailAuth,
				this.userDetailInfo.row.userNm, this.userDetailInfo.row.oauthPath, this.userDetailInfo.row.userPw,
				this.userDetailInfo.row.bizNm, this.userDetailInfo.row.grpNm
				],
			this.subTitles = ['아이디', '수정일', '이메일인증', '이름', '가입경로', '비밀번호', '회사명', '권한등급']
			this.listLength = this.subTitles.length;
		},
	},
	mounted() {
		this.fn_init();
	},
};
</script>

