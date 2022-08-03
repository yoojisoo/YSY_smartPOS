<!-- <template>
	<v-card outlined tile class="overflow-y-auto d-flex flex-column" height="100%">
		<v-card-title class="pb-0">검색</v-card-title>
		<v-card-text>

			<v-row justify="center" align="center">
				<v-col cols="5">
		
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
</template> -->
 <template>
 
 <div>
	<v-row class="ma-0 pa-0">
		<v-col cols="6" class="ma-0 pa-0">
			<v-select
			:items="idSearchList"
			label="선택"
			></v-select>
		</v-col>
		<v-col cols="6" class="ma-0 pa-0">
			<v-text-field
            label="Regular"
			placeholder="Placeholder"
          ></v-text-field>
		</v-col>
		
	</v-row>

	<v-row class="ma-0 pa-0" v-for="(obj , idx) in arrayList" :key="idx">
		<v-col cols="12" class="ma-0 pa-0">
			<v-select
			:items="obj.dataList"
			:label="obj.displayName"
			v-model = "obj.defaultSelectName"
			></v-select>
		</v-col>
	</v-row>

	<v-row class="ma-0 pa-0" >
		<v-col cols="12" class="ma-0 pa-0">
			<v-select
			:items="selectMenu2"
			label="홈페이지"
			v-model = "testName"
			></v-select>
		</v-col>
	</v-row>
	
 </div>
 
 </template>
<script>
export default {
	props: ['filters'],
	data() {
		return {
			testName : '홈페이지',
			selectMenuDefalut: '아이디',
			idSearchList: ['아이디', '이름'],
			selectMenu2: ["","ALL","홈페이지",'카카오', '네이버'],//가입경로
			selectMenu3: ["","ALL",'Y', 'N'],//이메일 인증
			selectMenu4: ["","ALL",'Y', 'N'],//

			arrayList:[
				{	
					defaultSelectName : '네이버',
					dataList : ["","ALL","홈페이지",'카카오', '네이버'] ,
					displayName : "가입경로 선택"
				},
				{	
					defaultSelectName : 'Y',
					dataList : ["","ALL",'Y', 'N'] ,
					displayName : "이메일 인증여부 선택"
				},
				
			],
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
		},
	},
	mounted() {
	}
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
