<!--
    *** props ***
    - info : {
        title : String - card 제목
        contentList : List - 카드안에 내용 리스트 {title , text}로 구성
        content : String - card 내용 - contentList가 존재하면 적용 안됨.
        img : String - card 대표 이미지 경로
        isAction : boolean - 하단 버튼 리스트
        viewCnt : String - 하단 버튼 리스트 중 조회수 isAction이 false이면 적용 안됨.
        boardId : String - 해당 보드 아이디 - 필수
    }
-->
<template>
	<v-card class="ma-0 pa-0" width="100%" height="470" outlined>
		<!-- 이미지 최적화 사이즈 : 290 * 290 (정방형) -->
		<v-img :src="getImgSrc()" class="cursor_finger" contain height="290" @click="detailMove(imgSrc)">
			<v-chip v-if="isToday()" color="error" class="ma-3" style="float: left"> NEW </v-chip>
			<!--<v-btn absolute bottom right fab elevation="0">
				<v-icon>mdi-bookmark-outline </v-icon>
			</v-btn>-->
		</v-img>
		<v-divider class="ma-0 pa-1" />
		<v-card-title class="content_box">
			<v-icon left v-if="info.attechFileCnt > 0"> mdi-paperclip </v-icon>
			<span>{{ info.title }}</span>
		</v-card-title>
		<v-card-text class="content_box">
			<div v-if="info.subTitle" v-text="info.subTitle" />
			<div v-else>
				{{ !info.subTitle ? info.subTitle : 'Data Not Found!' }}
			</div>
		</v-card-text>
		<v-card-actions class="pa-3">
			<v-row no-gutters align="center" justify="start">
				<v-icon class="mr-2"> mdi-account </v-icon>
				{{ info.userName }}
				<v-spacer />
				<v-btn text> 조회수 {{ info.viewCnt }} </v-btn>
				<v-btn text> 댓글수 {{ info.commentCnt }}</v-btn>
			</v-row>
		</v-card-actions>
	</v-card>
</template>

<script>
import CommonService from '@/service/CommonService';
export default {
	props: ['info', 'styleInfo'],
	data() {
		return {
			default_img: 'https://cdn.vuetifyjs.com/images/cards/cooking.png',
			base_url: 'http://tboom.shop',
			imgSrc: '',
		};
	},
	methods: {
		/** HTML 형식의 content에서 tag 제거한 내용 리턴 */
		//textChg(text) {
		//	const extractTextPattern = /(<([^>]+)>)/gi;
		//	return text.replace(extractTextPattern, '');
		//},
		getImgSrc() {
			if (this.info.attechFileCnt > 0) {
				this.imgSrc = this.base_url + this.info.fileDtoList[0].fileFullPath;
			} else this.imgSrc = this.default_img;

			return this.imgSrc;
		},

		isToday() {
			let today = new Date();
			let year = today.getFullYear();
			let month = ('0' + (today.getMonth() + 1)).slice(-2);
			let day = ('0' + today.getDate()).slice(-2);
			let dateString = year + '-' + month + '-' + day;

			return dateString === this.info.regDt;
		},

		async detailMove(param) {
			if (param === this.default_img) {
				alert('다운로드 받을 파일이 없습니다.');
			} else {
				console.log('boardId >> ', this.info.boardId);
				console.log('fileId >> ', this.info.fileDtoList[0].fileId);
				console.log('fileName >> ', this.info.fileDtoList[0].newFileName);

				let params = {
					boardId: this.info.boardId,
					fileId: this.info.fileDtoList[0].fileId,
					fileName: this.info.fileDtoList[0].newFileName,
				};

				try {
					await CommonService.fn_downloadFile('/ysy/v1/admin/downloadYsyBoardFile', params);
				} catch (error) {}
			}
		},
	},
};
</script>

<style scoped>
.content_box {
	/** 한줄 !! */
	display: block;
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
	text-align: start;
	/** 특정 단위로 텍스트를 자르는 css */
	/*white-space: normal;
	display: -webkit-box;
	-webkit-line-clamp: 3;
	-webkit-box-orient: vertical;
	overflow: hidden;*/
}

.v-responsive {
	overflow: visible;
}
</style>
