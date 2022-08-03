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
		<FileAttach :info="FileAttachInfo" />

		<!-- editor 영역 -->
		<SummerNote :info="SummerNoteInfo" />
	</div>
</template>

<script>
import { BaseThumbnail, BlockButtonGroup ,CommonService } from '@/assets/util/importFile.js';
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
				},
				callbackDelete: () => {
					console.log('callbackDelete Click');
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
				orgTitle: '',
				orgContent: '',
				contentCallback: chgContent => {
					console.log('ContentCallback', chgContent);
					this.$store.dispatch('adminFreeBoardStore/setContent', chgContent);
				},
				titleCallback: chgTitle => {
					console.log('TitleCallback', chgTitle);
					this.$store.dispatch('adminFreeBoardStore/setTitle', chgTitle);
				},
			},
		};
	},
	computed: {},
	methods: {
		async saveClick() {
			console.log('saveClick');

			

			const title = this.$store.state.adminFreeBoardStore.title;
			const content = this.$store.state.adminFreeBoardStore.content;
			if (title === this.$store.state.adminFreeBoardStore.orgTitle && content === this.$store.state.adminFreeBoardStore.orgContent) {
				console.log('같음');
				return;
			}

			console.log('title ', title);
			console.log('content ', content);
			console.log('this.files', this.files);

			let formData = new FormData();
			formData.append('title', title);
			formData.append('content', content);
			this.files.forEach(x => {
				formData.append('files', x);
			});

			let res = await CommonService.fn_save("/ysy/v1/admin/saveYsyBoard" , formData);
			console.log("res",res);
			if(res.data.status == "OK"){
				alert("success");
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
	},
};
</script>

<style></style>
