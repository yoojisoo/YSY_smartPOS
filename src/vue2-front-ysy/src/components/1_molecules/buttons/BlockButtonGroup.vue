<!-- create by clubbboy@naver.com
    버튼 그룹 : 설정에 따라서 버튼 처리함.
    props 
        info{
            isIcon : bool 
            callbackSearch : function 함수 -> 조회
            callbackSave   : function 함수 -> 저장
            callbackModify : function 함수 -> 수정
            callbackDelete : function 함수 -> 삭제
            callbackDown   : function 함수 -> excelDown   - 그리드 정보 같이 있어야함
            callbackUpload : function 함수 -> excelUpload - 그리드 정보 같이 있어야함
        }

 -->
<template>
	<div>
		<!--버튼 영역 -->
		<v-row no-gutters justify="end">
			<v-col class="pt-3 px-3" cols="12" style="text-align: end">
				<!-- <v-spacer></v-spacer> -->
				<span class="btn_mr_nolast">
					<v-btn
						v-if="info && info.callbackSearch ? true : false"
						outlined
						height="40px"
						width="80px"
						@click="searchClick"
					>
						<v-icon>mdi-account-search</v-icon> 조회
					</v-btn>
					<v-btn
						v-if="info && info.callbackSave ? true : false"
						outlined
						height="40px"
						width="80px"
						@click="saveClick"
					>
						<v-icon>mdi-content-save</v-icon> 저장
					</v-btn>
					<v-btn
						v-if="info && info.callbackModify ? true : false"
						outlined
						height="40px"
						width="80px"
						@click="cancelClick"
					>
						<v-icon>mdi-pen</v-icon> 수정
					</v-btn>
					<v-btn
						v-if="info && info.callbackDelete ? true : false"
						outlined
						height="40px"
						width="80px"
						@click="deleteClick"
					>
						<v-icon>mdi-delete</v-icon> 삭제
					</v-btn>
					<v-btn
						v-if="info && info.callbackDown ? true : false"
						outlined
						height="40px"
						width="100px"
						@click="excelDownload"
					>
						<v-icon>mdi-file-excel</v-icon> Down
					</v-btn>
					<v-btn
						v-if="info && info.callbackUpload ? true : false"
						outlined
						height="40px"
						width="100px"
						@click="excelUpload"
					>
						<v-icon>mdi-folder-upload</v-icon> Upload
					</v-btn>
				</span>
			</v-col>
		</v-row>
	</div>
</template>

<script>
export default {
	props: ['info'],
	methods: {
		searchClick() {
			if (this.info && this.info.callbackSearch) this.info.callbackSearch();
		},
		saveClick() {
			if (this.info && this.info.callbackSave) this.info.callbackSave();
		},
		cancelClick() {
			if (this.info && this.info.callbackModify) this.info.callbackModify();
		},
		deleteClick() {
			if (this.info && this.info.callbackDelete) this.info.callbackDelete();
		},
		excelDownload() {
			if (this.info && this.info.callbackDown) this.info.callbackDown();
		},
		excelUpload() {
			if (this.info && this.info.callbackUpload) this.info.callbackUpload();
		},
	},
};
</script>

<style></style>
