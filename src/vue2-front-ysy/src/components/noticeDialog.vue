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
					<div class="text-h5">{{ boardObj.item.title }}</div>
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
								<span style="font-size: 13px;">admin</span>
							</v-row>
							<v-row class="ma-0 pa-0" no-gutters>
								<span style="font-size: 10px;">22-06-09</span>
							</v-row>
						</v-col>
					</v-row>
				</v-card-subtitle>

				<!-- <v-card-subtitle class="pt-2 pb-0">
								<v-chip
										class="ma-2"
										color="#6667AB"
										outlined
										pill
										small
								>
										Admin
										<v-icon right>
											mdi-account-outline
										</v-icon>
								</v-chip>
				</v-card-subtitle> -->

				<v-divider class="mx-6 py-2"/>

				<v-card-text>
					<div>{{ boardObj.item.content }}</div>
					<!-- <div>업데이트2. 세션 오류 수정</div>
					<div>업데이트3. 내 정보 바로가기 기능 추가</div> -->
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

	export default {
		props: ["boardObj" , "callback"],
		data: () => ({
			isDialog:true,
			dialogMsg : {
				isConfirm : null, // 확인버튼 클릭 여부 true면 확인 클릭
				msg : "1234356677",
			},
		}),
		// watch: {
		// 	content() {
		// 		this.isDialog = !this.isDialog
		// 	}
		// }
		created (){
			console.log("this.boardObj");
			console.log(this.boardObj.item.content);
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