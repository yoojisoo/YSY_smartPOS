<!-- create by clubbboy@naver.com
    admin board save modify sample
 -->
<template>
	<div>
		<!--버튼 영역 -->
		<BlockButtonGroup :info="ButtonGroupInfo" />
		<!-- 썸네일 방식 파일 첨부영역 -->
		<BaseThumbnail :info="ThumbnailInfo" />
		<!-- 파일 첨부 영역 -->
		<!--<FileAttach :info="FileAttachInfo" />-->

		<!-- editor 영역 -->
		<SummerNote :info="SummerNoteInfo" />
	</div>
</template>

<script>
import { BaseThumbnail, BlockButtonGroup, CommonService, YsyUtil } from '@/assets/import/index.js';
import FileAttach from '@/components/1_molecules/FileAttach.vue';
import SummerNote from '@/components/1_molecules/editors/SummerNote.vue';

export default {
	components: {
		BaseThumbnail,
		BlockButtonGroup,
		FileAttach,
		SummerNote,
	},
	data() {
		return {
			files: [],
			//버튼 그룹 정보
			ButtonGroupInfo: {
				// callbackSearch : ()=>{  console.log("callbackSearch Click");    },
				callbackSave: () => {
					console.log('callbackSave Click');
					this.saveClick();
				},
				callbackModify: () => {
					console.log('callbackModify Click');
					this.modClick();
				},
				callbackDelete: () => {
					console.log('callbackDelete Click');
					this.delClick();
				},
				// callbackDown   : ()=>{  console.log("callbackDown Click");      },
				// callbackUpload : ()=>{  console.log("callbackUpload Click");    },
			},

			//썹네일 파일 업로드 정보
			ThumbnailInfo: {
				isMulti: true,
				isAdd: true,
				callback: uploadFiles => {
					this.files = uploadFiles;
					console.log('this.ThumbnailInfo.files callback ', this.files);
				},
			},
			//파일 업로드 정보
			FileAttachInfo: {
				isMulti: true,
				label: '업로드 파일',
				placeholder: '파일을 선택하세요',
				callback: uploadFiles => {
					console.log('this.FileAttachInfo.files callback ', uploadFiles);
				},
			},
			//summerNote info
			SummerNoteInfo: {
				isEdit: true,
				isTitle: true,
				isSubTitle: true,
				orgTitle: '',
				orgSubTitle: '',
				orgContent: '',
				titleCallback: chgTitle => {
					console.log('TitleCallback', chgTitle);
					this.$store.dispatch('adminFreeBoardStore/setTitle', chgTitle);
				},
				subTitleCallback: chgSubTitle => {
					console.log('SubTitleCallback', chgSubTitle);
					this.$store.dispatch('adminFreeBoardStore/setSubTitle', chgSubTitle);
				},
				contentCallback: chgContent => {
					console.log('ContentCallback', chgContent);
					this.$store.dispatch('adminFreeBoardStore/setContent', chgContent);
				},
			},
		};
	},
	methods: {
		async saveClick() {
			console.log('saveClick');

			const title = this.$store.state.adminFreeBoardStore.title;
			const subTitle = this.$store.state.adminFreeBoardStore.subTitle;
			const content = this.$store.state.adminFreeBoardStore.content;
			if (
				title === this.$store.state.adminFreeBoardStore.orgTitle &&
				subTitle === this.$store.state.adminFreeBoardStore.orgSubTitle &&
				content === this.$store.state.adminFreeBoardStore.orgContent
			) {
				console.log('같음');
				return;
			}

			console.log('title ', title);
			console.log('subTitle', subTitle);
			console.log('content ', content);
			console.log('this.files', this.files);

			let formData = YsyUtil.createFormData({ 'title ': title, 'subTitle ': subTitle, 'content ': content }, this.files);
			// let formData = new FormData();
			// formData.append('title', title);
			// formData.append('content', content);
			// this.files.forEach(x => {
			// 	formData.append('files', x);
			// });

			let res = await CommonService.fn_save('/ysy/v1/admin/saveYsyBoard', formData);
			console.log('res', res);
			if (res.data.status == 'OK') {
				alert('success');
			}

			// this.$axios
			// 	.post('/ysy/v1/saveYsyBoard', formData) //,{headers: {'Content-Type': 'multipart/form-data'}} //content-type 셋팅 안해도 올라가네??
			// 	.then(res => {
			// 		console.log('');
			// 		console.log(res.data);
			// 		alert('success');
			// 	})
			// 	.catch(error => {
			// 		console.log(error);
			// 	});
		},
		async modClick() {
			let params = {
				boardId: '1',
				fileId: '1',
				fileName: '262832165620200_영업비밀1 - 복사본 (2).jpg',
			};
			try {
				await CommonService.fn_downloadFile('/ysy/v1/admin/downloadYsyBoardFile', params);
			} catch (error) {}
		},
		async delClick() {
			let params = {
				boardId: '1',
			};
			try {
				let res = await CommonService.fn_downloadFiles('/ysy/v1/admin/downloadYsyBoardFiles', params);
				// let res = await CommonService.fn_getDataList("?boardId=1&fileId=1&fileName=262832165620200_영업비밀1 - 복사본 (2).jpg" );
			} catch (error) {}
		},
	},
};
</script>

<style></style>
