<!-- 22 07 26 clubbboy@naver.com 
	*** props ***
	info{
		boardId : int  -> 없으면 신규  있으면 수정가능(현재 접속자와 writer이 같아야 함.)
		writer : String -> 작성자
	}
-->
<template>
	<div class="editor-page">

		<v-btn outlined height="50px" width="100px" @click="saveClick">
			<v-icon>mdi-content-save</v-icon> 저장
		</v-btn>
		<v-btn outlined height="50px" width="100px" @click="cancelClick">
			<v-icon>mdi-pen</v-icon> 수정
		</v-btn>
		<v-btn outlined height="50px" width="100px" @click="deleteClick">
			<v-icon>mdi-delete</v-icon> 삭제
		</v-btn>


		<v-file-input v-model="files" color="deep-purple accent-4" counter label="대표이미지 Upload" multiple
		accept="image/*"
			placeholder="Select your files" prepend-icon="mdi-paperclip" outlined :show-size="1000" @onChange="fileChange">
			<template v-slot:selection="{ index, text }">
				<v-chip color="deep-purple accent-4" dark label small>
					{{ text }}
				</v-chip>
				<!-- <v-chip v-if="index < 2" color="deep-purple accent-4" dark label small>
					{{ text }}
				</v-chip>

				<span v-else-if="index === 2" class="text-overline grey--text text--darken-3 mx-2">
					+{{ files.length - 2 }} File(s)
				</span> -->
			</template>
		</v-file-input>





		<v-text-field class="px-3" v-model="title" hint="제목" placeholder="제목 입력">

		</v-text-field>

		<div id="summernote"></div>
	</div>
</template>

<script>

export default {
	props: ['info'],
	components:{

	},
	data() {
		return {
			title:"Title1",
			content: "",
			orgContent:"",
			height:20,
			files: [],

		};
	},
	mounted() {
		$('#summernote').summernote({
			height: "400",
			minHeight: null,
			maxHeight: null,
			focus: true,
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
		saveClick(){
			console.log("saveClick");
			this.content = $('#summernote').summernote('code'); //값 가져오기
			console.log("content = ["+this.content + "] \n orgContent = [" + this.orgContent +"]");
			console.log('확인중~' + typeof this.content);
			let param = {
				content : this.content,
				title : "title1",
			};
			
			this.$axios.post("/ysy/v1/createSummerNote",param).then(res=>{
				console.log("");
				console.log(res);
				console.log(res.config.data);
				$('#summernote').summernote('code', res.data); 
				// $('#summernote').summernote('fullscreen.toggle');

				alert("success");
			})
			.catch(error=>{
				console.log(error);
			});
		},
		cancelClick() {

		},
		deleteClick() {

		},
		fileChange(e) {
			console.log(e);
		},
	},
	computed:{

	},
	watch: {

	},
};
</script>
