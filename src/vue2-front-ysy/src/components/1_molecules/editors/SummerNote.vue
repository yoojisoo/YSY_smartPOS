<!-- 22 07 26 clubbboy@naver.com 
	*** props ***
	info{
		boardId : int  -> 없으면 신규  있으면 수정가능(현재 접속자와 writer이 같아야 함.)
		writer : String -> 작성자
	}
-->
<template>
	<div>

		<!-- 제목 영역 -->
		<v-row no-gutters class="pb-5">
			<v-col class="pt-3" cols="12">
				<v-text-field class="px-3" v-model="title" outlined placeholder="제목 입력" hide-details/>
			</v-col>
		</v-row>
		
		<!-- content 영역 -->
		<v-row no-gutters class="px-3">
			<v-col  cols="12">
				<div id="summernote"></div>
			</v-col>
		</v-row>

	</div>
</template>

<script>


export default {
	props: ['info'],

	data() {
		return {
			title:"",
			content: "",
			orgContent:"",

		};
	},
	computed:{
	
	},
	watch: {

	},
	mounted() {
		$('#summernote').summernote({
			height: "500",
			minHeight: null,
			maxHeight: null,
			focus: false,
			lang: 'ko-KR',
			callbacks: {
              onImageUpload: function(image) {
					console.log("onImageUpload");
                    
					image.forEach(x=>{
						var reader = new FileReader();
						reader.readAsDataURL(x);
						reader.onloadend = function() {
						console.log("2onImageUpload");
                        var image = $('<img>').attr('src',  reader.result);
                           image.attr('width','100%');
                        $('#summernote').summernote("insertNode", image[0]);
                    }
					})
              }
           }
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
		// $('#summernote').summernote('disable');
		// if ($('#summernote').summernote('isEmpty')) {
		// 	alert('editor content is empty');
		// }
	},
	methods: {
		
	},
};
</script>

<style scoped>
/*  */
</style>