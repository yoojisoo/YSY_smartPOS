  <template>
	<v-card outlined tile class="overflow-y-auto d-flex flex-column" height="100%">
		<v-card-title >검색</v-card-title>
		<v-card-text>
			<v-select
				class="ma-0 pa-0"
				v-model="searchInfo.searchSelectDefalut"
				:items="searchInfo.dataList"
				dense
				outlined
				hide-details
				@change="fn_change"
				style="width: 120px;"
			></v-select>
			<v-text-field
				dense
				class="mt-2"
				label="검색어를 입력하세요"
				append-icon="mdi-magnify"
			></v-text-field>
		</v-card-text>

		<v-card-title class="pb-0">필터</v-card-title>
		<v-card-text>
			<v-autocomplete
				v-for="(list, idx) in autocompleteInfo.dataList"
				:key="idx"
				:items="list"
				:placeholder="autocompleteInfo.placeholderList[idx]"
				clearable
				hide-details
				dense
				class="mb-2"
			></v-autocomplete>
		</v-card-text>
		<v-card-actions class="fixed_condition_btn ma-0 pa-0">
			<v-btn block outlined>검색하기</v-btn>
		</v-card-actions>
	</v-card>
</template>

<script>
export default {
	props: ['filters'],
	data() {
		return {
			searchInfo: {
				searchSelectDefalut: '아이디',
				dataList: ['아이디', '이름'],
				// rules: {
				// 	email: [
				// 		v => {
				// 			console.log('v ===> ' + v);
				// 			return (v || '').length <= 30 || 'Max 30 characters';
				// 		},
				// 		v => {
				// 			console.log('====================================================>' + v);
				// 			const replaceV = v != undefined ? v.replace(/(\s*)/g, '') : '';

				// 			const pattern =
				// 				/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/;
				// 			return pattern.test(replaceV) || '이메일 형식으로 입력해주세요';
				// 		},
				// 	],
				// },
			},
			autocompleteInfo:{
				dataList : [
					['All', '홈페이지', '카카오', '네이버'], //가입경로
					['Y', 'N'], //이메일 인증
					['All', '메카테크', '세메스'], //회사이름
					['All', '비회원', '준회원', '정회원', 'VIP1', 'VIP2', 'VIP3', '매니저', '수퍼매니저', '관리자'], //권한등급
				],
				placeholderList: ['가입경로', '이메일 인증', '회사이름', '권한등급']
			}
		};
	},
	computed: {
		getConditionItems() {
			return this.$store.state.userStore.conditionItems;
		},
	},
	methods: {
		fn_change(value) {
			console.log('fn_change');
			console.log(value);
		},
		async fn_getConditionItems() {
			try {
				await this.$store.dispatch('userStore/fn_getConditionItems');
				if (this.getConditionItems) {
					console.log('🟢 userMng fn_getConditionItems');
				}
			} catch (error) {
				console.log('⛔ userMng fn_getConditionItems');
			}
		},
	},
	mounted() {
	}
};
</script>

<style scoped>
	.fixed_condition_btn {
		position: fixed;
		bottom: 70px;
		width: 214.39px;
		left: 16px;
	}
	.v-autocomplete, .v-text-field {
		font-size: 13px;
	}
</style>
