<!-- v-model="isDialog" -->
<template>
	<v-row justify="center">
		<!-- persistent : overlay를 클릭해도 닫히지 않도록 한다 -->
		<v-dialog
				v-model="isDialog"
				persistent
				max-width="600">
			<v-card>
				<v-toolbar
						color="#6667AB"
						dark
						flat
						height="18"
				>
				</v-toolbar>

				<v-card-title>
					<div class="text-xs-center pb-1 pr-2">
    					<v-chip small outlined color="#6667AB">공지</v-chip>
  					</div>
					<div class="text-h5">{{ boardObj.title }}</div>
				</v-card-title>

<!-- 	align-center, justify-space-around, wrap, row, spacer -->
				<v-card-subtitle class="pt-2 pb-0">
					<v-row class="ma-0 pa-0" no-gutters>
						<v-col cols="1">
							<v-btn icon large >
								<v-icon>mdi-account</v-icon>
							</v-btn>
						</v-col>
						<v-col cols="3">
							<v-row class="mt-1 mb-n2 pa-0" no-gutters>
								<span style="font-size: 13px;"> {{ boardObj.regId }} </span>
							</v-row>
							<v-row class="ma-0 pa-0" no-gutters style="font-size: 10px;">
								<span>{{dateVal}}</span>
								<!-- <dateForm :dateVal="dateVal" :gubun="gubun"/> -->
								<!-- <span style="font-size: 10px;">22-06-09</span> -->
							</v-row>
						</v-col>
					</v-row>
				</v-card-subtitle>

				<v-divider class="mx-6 py-2"/>

				<v-card-text>
					{{ boardObj.content }}
				</v-card-text>

				<v-card-actions>
					<v-spacer />
					<v-btn
						color="#6667AB"
						text
						@click="clickConfirm">
							확인
					</v-btn>
				</v-card-actions>
			</v-card>
		</v-dialog>
	</v-row>
</template>

<script>
	import dateForm from '@/components/gridModule/DateForm.vue';

	export default {
		props: ["boardObj" , "callback"],
		data: () => ({
			isDialog:true,
			dialogMsg : {
				isConfirm : null, // 확인버튼 클릭 여부 true면 확인 클릭
				msg : "1234356677",
			},
			// dateVal: (boardObj.regDt != null || boardObj.regDt != "") ? boardObj.regDt : '220613',
			dateVal: '220613',
			// gubun: '-'

		}),
		components: {
			dateForm
		},
		created (){
			console.log("this.boardObj ***************");
			console.log(this.boardObj);
		},
		methods: {
			//확인버튼 클릭시 콜백함수 줘야함.
			clickConfirm(){
				if(this.callback){
					this.callback(this.dialogMsg);
				}
			}
		},

	}
</script>

<style>

</style>