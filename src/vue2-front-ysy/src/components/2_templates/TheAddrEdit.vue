<template>
	<v-container class="pa-0 ma-0">
		<v-divider class="ma-0 pa-0" />
		<v-card-title class="pt-3">
			{{ addr.addrType }}
			<v-spacer />
			<span class="btn_mr_nolast">
				<v-btn outlined>삭제</v-btn>
				<v-btn outlined>저장</v-btn>
			</span>
		</v-card-title>

		<v-card-text class="pb-2">
			<v-text-field
				v-for="n in textFieldLength" :key="n"
				:label="textFieldInfo.labels[n-1]"
				:prepend-icon="textFieldInfo.icons[n-1]"
				v-model="textFieldInfo.datas[n-1]"
				dense
			></v-text-field>
		</v-card-text>
	</v-container>
</template>
<script>
import { YsyUtil } from '@/mixin/MixinGlobal.js';
export default {
	props: ['addr'],
	mixins: [YsyUtil],
	data() {
		return {
			textFieldLength: 0,
			textFieldInfo: {
				labels: [],
				icons: [],
				datas: [],
			},
		}
	},
	methods: {
		fn_textFieldInfoInit() {
			this.textFieldInfo.labels = ['우편번호', '기본주소', '상세주소', '전화번호1', '전화번호2'];
			// this.textFieldInfo.icons = ['mdi-mailbox-up', 'mdi-map-marker', 'mdi-home-map-marker', 'mdi-phone', 'mdi-phone'];
			this.textFieldInfo.datas = [this.addr.addrZipCode, this.addr.addrDetail, this.addr.addrEtc, this.addr.phone1, this.addr.phone2];
			this.textFieldLength = this.textFieldInfo.labels.length;
		},
		fn_isNullAndEmpty() {
			this.isNullAndEmpty(this.addr.addrZipCode) === true ? this.addr.addrZipCode = 'NONE' : '';
			this.isNullAndEmpty(this.addr.addrDetail) === true ? this.addr.addrDetail = 'NONE' : '';
			this.isNullAndEmpty(this.addr.addrEtc) === true ? this.addr.addrEtc = 'NONE' : '';
			this.isNullAndEmpty(this.addr.phone1) === true ? this.addr.phone1 = 'NONE' : '';
			this.isNullAndEmpty(this.addr.phone2) === true ? this.addr.phone2 = 'NONE' : '';
		},
	},
	mounted() {
		this.fn_isNullAndEmpty();
		this.fn_textFieldInfoInit();
	}
};
</script>

<style></style>
