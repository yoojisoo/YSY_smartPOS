<template>
	<v-card outlined tile class="overflow-y-auto" height="100%">
		<v-card-title>
			<span> 유저상세정보 </span>
			
			<v-spacer />
			<v-card-actions>
				<BaseButtonOutlined
					v-if="btnDetailText === '수정'"
					:btnText="btnDetailText"
					:btnHeight="35"
					@click="fn_detailEvent"
				/>
				<BaseButtonOutlined
					v-else-if="btnDetailText === '저장'"
					:btnText="btnDetailText"
					:btnHeight="35"
					@click="fn_modify"
				/>
			</v-card-actions>
		</v-card-title>
		<v-card-text>
			✔️newUserInfoListOfLow :{{ newUserInfoListOfLow }}
			✔️userDetailInfo.row : {{ userDetailInfo.row.userId }} 🔅🔅
			<v-row align="center" class="mb-1">
				<v-col cols="4" class="py-0 mt-2">
					<div v-for="subTitle in subTitles" :key="subTitle" style="height: 26px;">
						{{ subTitle }}
					</div>
				</v-col>
				<v-col cols="8" class="py-0">
					<v-text-field
						v-for="n in newUserInfoListOfLow.length" :key="n"
						v-model="newUserInfoListOfLow[n-1]"
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
import { BaseButtonOutlined, TheAddrEdit } from '@/assets/util/importFile.js';

export default {
	props: ['userDetailInfo'],
	components: {
		TheAddrEdit,
		BaseButtonOutlined,
	},
	data() {
		return {
			btnDetailText: '수정',
			isDisabled: false,
			subTitles: [],
			listLength: 0,
			newUserInfoListOfLow: [],
			
		};
	},
	methods: {
		fn_init() {
			this.subTitles = ['아이디', '수정일', '이메일인증', '이름', '가입경로', '비밀번호', '회사명', '권한등급'];
			this.listLength = this.subTitles.length;
		},
		fn_detailEvent() {
			
		},
		fn_modify() {

		},
	},
	mounted() {
		this.fn_init();

	},
	computed: {
		nowUserDetailInfo() {
			 this.newUserInfoListOfLow = [
				this.userDetailInfo.row.userId, this.userDetailInfo.row.modDt, this.userDetailInfo.row.isEmailAuth,
				this.userDetailInfo.row.userNm, this.userDetailInfo.row.oauthPath, this.userDetailInfo.row.password,
				this.userDetailInfo.row.bizNm, this.userDetailInfo.row.grpNm
				]
				console.log('newUserInfoListOfLow -------------------------->', this.newUserInfoListOfLow);
			return this.userDetailInfo
		},
	},
};
</script>

