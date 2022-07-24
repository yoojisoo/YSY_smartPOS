<template>
  <v-card outlined tile>
	<v-card-title class="pb-0">검색</v-card-title>
	<v-card-text>
		<!-- :rules="rules.email" 생명 주기 또는 코드 수정해야함 -->
		<v-text-field 
			label="이메일 검색"
			:rules="rules.email"
			:success="isSuccess"
			dense
		></v-text-field>
		<v-text-field 
			label="이름 검색"
			dense
		></v-text-field>
		<p>{{rules.email}}</p>
	</v-card-text>

	<v-card-title class="pb-0">필터</v-card-title>
	<v-card-text>
		<v-autocomplete
			v-for="filter in filterList" :key="filter.index"
			v-model="selected"
			:items="filter"
			clearable
			dense
		></v-autocomplete>
	</v-card-text>

	<!-- <v-card-text>
		<v-menu
          v-model="menu2"
          :close-on-content-click="false"
          :nudge-right="40"
          lazy
          transition="scale-transition"
          offset-y
          full-width
          max-width="290px"
          min-width="290px"
        >
          <template v-slot:activator="{ on }">
            <v-text-field
              v-model="computedDateFormatted"
              label="Date (read only text field)"
              prepend-icon="mdi-calendar"
              readonly
              v-on="on"
            ></v-text-field>
          </template>
          <v-date-picker v-model="date" no-title @input="menu2 = false"></v-date-picker>
        </v-menu>
        <p>Date in ISO format: <strong>{{ date }}</strong></p>
	</v-card-text> -->

	<v-card-title class="pb-0">선택한 조건</v-card-title>
	<v-card-text>
	</v-card-text>

	<v-card-text>
		<v-btn block outlined>검색하기</v-btn>
	</v-card-text>

  </v-card>
</template>

<script>
export default {
	data() {
		return {
			selected: null,
			isSuccess: false,
			filterList: { //필터 조건들
				lastDate: ['최근 6개월'],
				role: [],
				bizCode: [],
				oauthPath: [],
				city: [],
				date: [],
			},
			rules: {
				email: [
					// v => (v || '').length <= 30 || 'Max 30 characters',
					// v => {
					// 	const replaceV = v.replace(/(\s*)/g, '');
					// 	const pattern  = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/
					// 	return pattern.test(replaceV) || '이메일 형식으로 입력해주세요'		
					// }
				],
				name: []
			},
			// date: new Date().toISOString().substr(0, 10),
			// dateFormatted: vm.formatDate(new Date().toISOString().substr(0, 10)),
			// menu1: false,
			// menu2: false,
		}
	},
	// data:vm => ({
	// 	date: new Date().toISOString().substr(0, 10),
	// 	dateFormatted: vm.formatDate(new Date().toISOString().substr(0, 10)),
	// 	menu1: false,
	// 	menu2: false
	// }),

	//  computed: {
    //   computedDateFormatted () {
    //     return this.formatDate(this.date)
    //   }
    // },

    // watch: {
    //   date (val) {
    //     this.dateFormatted = this.formatDate(this.date)
    //   }
    // },

	mounted() {
		// this.setRules();
	},

    methods: {
		// setRules() {
		// 	this.rules.email = [
				
		// 	]
		// }
    //   formatDate (date) {
    //     if (!date) return null

    //     const [year, month, day] = date.split('-')
    //     return `${month}/${day}/${year}`
    //   },
    //   parseDate (date) {
    //     if (!date) return null

    //     const [month, day, year] = date.split('/')
    //     return `${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')}`
    //   }
    }
}
</script>

<style>

</style>