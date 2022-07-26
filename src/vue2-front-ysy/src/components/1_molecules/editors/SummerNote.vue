<!-- 22 07 26 clubbboy@naver.com 
	*** props ***
	info{
		boardId : int  -> 없으면 신규  있으면 수정가능(현재 접속자와 writer이 같아야 함.)
		writer : String -> 작성자
	}
-->
<template>
	<div class="editor-page">

		<!--버튼 영역 -->
		<v-row no-gutters justify="end">
			<v-col class="pt-3 px-3" cols="12" style="text-align: end;">
				<!-- <v-spacer></v-spacer> -->
				<span class="btn_mr_nolast">
					<v-btn outlined height="40px" width="80px" @click="saveClick">
						<v-icon>mdi-content-save</v-icon> 저장
					</v-btn>
					<v-btn outlined height="40px" width="80px" @click="cancelClick">
						<v-icon>mdi-pen</v-icon> 수정
					</v-btn>
					<v-btn outlined  height="40px" width="80px" @click="deleteClick">
						<v-icon>mdi-delete</v-icon> 삭제
					</v-btn>
				</span>
			</v-col>
		</v-row>
		

		



		<!-- 제목 영역 -->
		<v-row no-gutters class="pb-5">
			<v-col class="pt-3" cols="12">
				<v-text-field class="px-3" v-model="title" outlined placeholder="제목 입력" hide-details/>
			</v-col>
		</v-row>


		<!-- 파일 첨부 영역 -->
		<v-row no-gutters>
			<v-col cols="12">
				<v-file-input v-model="files" class="mx-3"
						color="deep-purple accent-4"
						counter label="대표이미지 Upload"
						:multiple = true
						:dense = false
						accept="image/*"
						placeholder="Select your files"
						:prepend-icon="info && info.iconNm?info.iconNm : ''"
						outlined
						:show-size="1000"
						@change="fileChange">
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
		saveClick(){
			console.log("saveClick");
			this.content = $('#summernote').summernote('code'); //값 가져오기
			console.log("content = ["+this.content + "] \n orgContent = [" + this.orgContent +"]");
			console.log('확인중~' + typeof this.content);
			console.log(this.files);
			let param = {
				
				content : this.content,
				title : this.title,
				files : this.files
			};
			// this.$axios.defaults.headers.post['Content-Type'] = 'multipart/form-data';
			let formData = new FormData();
			// formData.append('title', this.title);
			// formData.append('files', this.files);
			formData.append('content', this.content);
			formData.append('title', this.title);
			this.files.forEach(x=>{
				formData.append('files', x);
			})
			
			
			
			this.$axios.post("/ysy/v1/createSummerNote",formData,
						{headers: {'Content-Type': 'multipart/form-data'}})
				.then(res=>{
					console.log("");
					console.log(res);
					$('#summernote').summernote('code', res.data); 
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
