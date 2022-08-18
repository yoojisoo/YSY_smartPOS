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
	<!--<v-card class="pa-3 ma-0 mx-2" outlined >
      <v-row no-gutters>
          <v-col cols="8" class="ma-0 pa-0">
              <v-card-title class="pl-1 pt-0 ma-0 text-h5" @click="detailMove('content')" v-text="info.title" />
              <v-card-subtitle @click="detailMove('content')"  class="pa-0 pr-3">

                  <v-card-text v-if="info.contentList" class="pa-0">
                      <div v-for="(content1, idx2) in info.contentList" :key="idx2">
                          {{content1.title}} : {{content1.text}}
                      </div>
                  </v-card-text>
                  <v-card-text v-else class="pa-0">
                      <div
                          class="d-inline-block text-truncate cursor_finger"
                          style="max-width: 100%;">
                          {{!info.content ? info.content : "Data Not Found!"}}
                      </div>
                  </v-card-text>
              </v-card-subtitle>
          </v-col>


          <v-col cols="4"  class="ma-0 pa-0" style="text-align: center;"  @click="detailMove('content')">
              <v-avatar size="110" :tile = "info && info.isTile? info.isTile : true">
                  <v-img class="cursor_finger" :src="info.img? info.img : default_img" />
              </v-avatar>
          </v-col>
      </v-row>-->
	<v-card class="ma-0 pa-0" width="450" height="490" outlined>
		<v-img :src="getImgSrc()" class="cursor_finger" contain height="300" @click="detailMove(imgSrc)">
			<v-btn absolute bottom right fab elevation="0">
				<v-icon>mdi-bookmark-outline </v-icon>
			</v-btn>
		</v-img>
		<v-card-title>
			<v-icon left v-if="info.attechFileCnt > 0"> mdi-paperclip </v-icon>
			<span>{{ info.title }}</span>
		</v-card-title>
		<v-card-subtitle style="text-align: start">
			<v-card-text v-if="info.subTitle">
				<div class="content_box" v-text="info.subTitle" />
			</v-card-text>
			<v-card-text v-else>
				<div class="d-inline-block text-truncate cursor_finger" style="max-width: 100%">
					{{ !info.subTitle ? info.subTitle : 'Data Not Found!' }}
				</div>
			</v-card-text>
		</v-card-subtitle>
		<v-card-actions>
			<v-list-item class="grow">
				<v-list-item-avatar color="grey darken-3"></v-list-item-avatar>
				<v-list-item-content>
					<v-list-item-title>{{ info.userName }}</v-list-item-title>
				</v-list-item-content>
				<v-row align="center" justify="end">
					<v-btn text> 조회수 {{ info.viewCnt }} </v-btn>
					<v-btn text> 댓글수 {{ info.commentCnt }}</v-btn>
				</v-row>
			</v-list-item>
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
