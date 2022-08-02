<!-- 22.06.29 yoojisoo -->
<template>
	<div class="editor-page">
		<BaseButtonOutlined :btnText="'저장'" :btnHeight="100" @click="aaa" />
		<v-btn outlined height="100px" width="100px" @click="aaa"> 저장 </v-btn>
		<div id="summernote"></div>
	</div>
</template>

<script>
import { BaseButtonOutlined } from '@/assets/util/importFile.js';
export default {
	props: ['editorInfo', 'isSave'],
	components: {
		BaseButtonOutlined,
	},
	data() {
		return {
			summernoteStr: '',
			text: 'aa',
			height: 20,
		};
	},

	mounted() {
		$('#summernote').summernote({
			height: 400,
			minHeight: null,
			maxHeight: null,
			focus: true,
			lang: 'ko-KR',

			// placeholder: '내용을 작성하세요.',
			// toolbar: [
			//   ['style', ['bold', 'italic', 'underline']],
			//   ['fontsize', ['fontsize']],
			//   ['color', ['color']],
			//   ['para', ['paragraph']],
			//   ['height', ['height']],
			//   ['Insert', ['picture']],
			//   ['Mics',['codeview']]
			// ]
		});
		// let str = 'hello world';
		// $('#summernote').summernote('code', str); //내용 넣기
		// $('#summernote').summernote('editor.insertText', 'hello world'); //내용 넣기
		// $('#summernote').summernote('codeview.toggle'); //코드 뷰  아이콘 활성화
		// let markupStr1 = $('.summernote').eq(1).summernote('code'); //html의 콘텐츠 가져오기
	},
	methods: {
		aaa() {
			console.log('btn click');
		},
		getContent() {
			if (this.editorInfo.isSave) {
				this.summernoteStr = $('#summernote').summernote('code'); //값 가져오기
				console.log('확인중~' + typeof this.summernoteStr);
			}
		},
		btnclick() {
			if (this.editorInfo.btnClick) {
				this.editorInfo.btnClick($('#summernote').summernote('code'), 'save');
			}
		},
	},
	watch: {
		isSave() {
			this.getContent();
		},
	},
};
</script>
