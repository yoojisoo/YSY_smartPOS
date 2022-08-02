<template>
	<v-card outlined tile class="overflow-y-auto" max-height="450">
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
			<v-row align="center" class="mb-2">
				<v-col cols="4" class="pb-0 pt-1"> 아이디 </v-col>
				<v-col class="py-0">
					<v-text-field
						v-model="userDetailInfo.userId"
						dense
						hide-details
						disabled
						class="my-0"
					></v-text-field>
				</v-col>
			</v-row>

			<v-row align="center" class="mb-2">
				<v-col cols="4" class="pb-0 pt-1"> 수정일 </v-col>
				<v-col class="py-0">
					<v-text-field
						v-model="userDetailInfo.modDt"
						dense
						hide-details
						disabled
						class="my-0"
					></v-text-field>
				</v-col>
			</v-row>

			<v-row align="center" class="mb-2">
				<v-col cols="4" class="pb-0 pt-1"> 이메일인증 </v-col>
				<v-col class="py-0">
					<v-text-field
						v-model="userDetailInfo.isEmailAuth"
						dense
						hide-details
						disabled
						class="my-0"
					></v-text-field>
				</v-col>
			</v-row>

			<v-row align="center" class="mb-2">
				<v-col cols="4" class="pb-0 pt-1"> 이름 </v-col>
				<v-col class="py-0">
					<v-text-field
						v-model="userDetailInfo.userNm"
						dense
						hide-details
						:disabled="isDisabled"
						class="my-0"
						clearable
					></v-text-field>
				</v-col>
			</v-row>

			<v-row align="center" class="mb-2">
				<v-col cols="4" class="pb-0 pt-1"> 가입경로 </v-col>
				<v-col class="py-0">
					<v-text-field
						v-model="userDetailInfo.oauthPath"
						dense
						hide-details
						disabled
						class="my-0"
					></v-text-field>
				</v-col>
			</v-row>

			<v-row align="center" class="mb-2">
				<v-col cols="4" class="pb-0 pt-1"> 비밀번호 </v-col>
				<v-col class="py-0">
					<v-text-field
						v-model="userDetailInfo.userPw"
						dense
						hide-details
						disabled
						class="my-0"
					></v-text-field>
				</v-col>
			</v-row>

			<v-row align="center" class="mb-2">
				<v-col cols="4" class="pb-0 pt-1"> 비즈네임 </v-col>
				<v-col class="py-0">
					<v-text-field
						v-model="userDetailInfo.bizNm"
						dense
						hide-details
						disabled
						class="my-0"
					></v-text-field>
				</v-col>
			</v-row>

			<v-row align="center">
				<v-col cols="4" class="pb-0 pt-1"> 그룹아이디 </v-col>
				<v-col class="py-0">
					<v-text-field
						v-model="userDetailInfo.grpId"
						dense
						hide-details
						disabled
						class="my-0"
					></v-text-field>
				</v-col>
			</v-row>
		</v-card-text>

		<div v-for="addr in userDetailInfo.addrList" :key="addr.id">
			<TheAddrEdit :addr="addr" />
		</div>
	</v-card>
</template>

<script>
import { BaseButtonOutlined, TheAddrEdit } from '@/assets/util/importFile.js';
// import { YsyUtil } from '@/mixin/MixinGlobal.js';

export default {
	props: ['userDetailInfo'],
	comments: {
		TheAddrEdit,
		BaseButtonOutlined,
	},
	// mixins: [YsyUtil],
	data() {
		return {
			btnDetailText: '수정',
			isDisabled: false,
		};
	},
	methods: {
		fn_isNullAndEmpty(info) {
			this.isNullAndEmpty(info.userId) === true ? (info.userId = 'NONE') : '';
			this.isNullAndEmpty(info.modDt) === true ? (info.modDt = 'NONE') : '';
			this.isNullAndEmpty(info.regDt) === true ? (info.regDt = 'NONE') : '';
			this.isNullAndEmpty(info.isEmailAuth) === true ? (info.isEmailAuth = 'NONE') : '';
			this.isNullAndEmpty(info.oauthPath) === true ? (info.oauthPath = 'NONE') : '';
			this.isNullAndEmpty(info.userPw) === true
				? (info.userPw = 'NONE')
				: (this.userDetailInfo.userPw = 'NONE');
			this.isNullAndEmpty(info.bizNm) === true ? (info.bizNm = 'NONE') : '';
			this.isNullAndEmpty(info.grpId) === true ? (info.grpId = 'NONE') : '';
		},
		fn_detailEvent(eventResponse) {
			this.isDisabled === true ? (this.isDisabled = false) : (this.isDisabled = true);
			this.btnDetailText === '수정'
				? (this.btnDetailText = '저장')
				: (this.btnDetailText = '수정');
			this.edlitedUserDetailInfo = Object.assign({}, this.userDetailInfo);
			console.log('👌this.edlitedUserDetailInfo');
			console.log(this.userDetailInfo);
			console.log(this.edlitedUserDetailInfo);
		},
		fn_modify(eventResponse) {
			console.log(eventResponse);
			this.isDisabled === true ? (this.isDisabled = false) : (this.isDisabled = true);
			this.btnDetailText === '수정'
				? (this.btnDetailText = '저장')
				: (this.btnDetailText = '수정');
			console.log('this.userDetailInfo.userNm ------------>');
			console.log(this.userDetailInfo.userNm);
		},
	},
};
</script>

<style></style>
