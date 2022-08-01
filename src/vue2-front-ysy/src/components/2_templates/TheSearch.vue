<template>
	<v-card outlined tile class="overflow-y-auto d-flex flex-column" height="100%">
		<v-card-title class="pb-0">검색</v-card-title>
		<v-card-text>
			<!-- :rules="rules.email" 생명 주기 또는 코드 수정해야함 -->
			<v-row justify="center" align="center">
				<v-col cols="5">
					<!-- v-model="selectMenu[0]" -->
					<v-select
						v-model="selectMenuDefalut"
						:items="selectMenu"
						outlined
						hide-details
						class="ma-0 pa-0"
						dense
						@change="fn_onChg"
					></v-select>
				</v-col>
				<v-col cols="7">
					<v-text-field
						:rules="rules.email"
						:success="isSuccess"
						dense
						class="mt-2"
					></v-text-field>
				</v-col>
			</v-row>
		</v-card-text>

		<v-card-title class="pb-0">필터</v-card-title>
		<v-card-text>
			<v-autocomplete
				v-for="filter in filters.slice(2, 6)"
				:key="filter.index"
				:items="filter.text"
				:label="filter.text"
				clearable
				dense
			></v-autocomplete>
		</v-card-text>

		<v-card-title class="pb-0">
			선택한 조건
		</v-card-title>
		<v-spacer />
		<div class="fixedBtn">
			<v-card-actions>
				<v-btn block outlined>검색하기</v-btn>
			</v-card-actions>
		</div>
	</v-card>
</template>

<script>
export default {
	props: ['filters'],
	data() {
		return {
			selectMenuDefalut: '아이디',
			selectMenu: ['아이디', '전화번호'],
			rules: {
				email: [
					v => {
						console.log('v ===> ' + v);
						return (v || '').length <= 30 || 'Max 30 characters';
					},
					v => {
						console.log('====================================================>' + v);
						const replaceV = v != undefined ? v.replace(/(\s*)/g, '') : '';

						const pattern =
							/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/;
						return pattern.test(replaceV) || '이메일 형식으로 입력해주세요';
					},
				],
			},
		};
	},
	computed: {
		getConditionItems() {
			return this.$store.state.userStore.conditionItems;
		},
	},
	methods: {
		fn_onChg(value) {
			console.log('valu----------------->e');
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
		}
	},
	mounted() {

	},
	methods: {
		onChg(value) {
			console.log('value -------------------------->');
			console.log(value);
		},
	},
};
</script>

<style scoped>
	.fixedBtn {
		position: fixed;
		bottom: 70px;
		width: 312.5px;
		left: 16px;
	}
</style>
