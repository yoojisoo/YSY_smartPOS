<!-- create by clubbboy@naver.com
    admin board save modify sample
 -->
<template>
  <div>

<!--버튼 영역 -->
    <ButtonGroup :info="ButtonGroupInfo"/>
<!-- 썸네일 방식 파일 첨부영역 -->
    <Thumbnail :info="ThumbnailInfo" />
<!-- 파일 첨부 영역 -->
    <FileAttach :info = "FileAttachInfo"/>



    <!-- editor 영역 -->
    <SummerNote :info = "SummerNoteInfo" />
  </div>
</template>

<script>
import Thumbnail from '@/components/1_molecules/Thumbnail.vue';
import ButtonGroup from '@/components/1_molecules/buttons/ButtonGroup.vue';
import FileAttach from '@/components/1_molecules/FileAttach.vue';
import SummerNote from '@/components/1_molecules/editors/SummerNote.vue';



export default {

    components:{
        Thumbnail,
        ButtonGroup,
        FileAttach,
        SummerNote,
    },
    data(){
        return {
            //버튼 그룹 정보
			ButtonGroupInfo : {
				// callbackSearch : ()=>{  console.log("callbackSearch Click");    },
				callbackSave   : ()=>{  console.log("callbackSave Click"); this.saveClick();     },
				callbackModify : ()=>{  console.log("callbackModify Click");    },
				callbackDelete : ()=>{  console.log("callbackDelete Click");    },
				// callbackDown   : ()=>{  console.log("callbackDown Click");      },
				// callbackUpload : ()=>{  console.log("callbackUpload Click");    },
			},
			
			//썹네일 파일 업로드 정보
			ThumbnailInfo : {
				isMulti : true,
				isAdd : true,
				callback:(uploadFiles)=>{
                    this.ThumbnailInfo.files = uploadFiles;
					console.log("this.ThumbnailInfo.files callback " , this.ThumbnailInfo.files);
				},
                files : [],
			},
			//파일 업로드 정보
			FileAttachInfo : {
				isMulti : true,
				label : "업로드 파일",
				placeholder : "파일을 선택하세요",
				callback:(uploadFiles)=>{
                    this.FileAttachInfo.files = uploadFiles;
					console.log("this.FileAttachInfo.files callback " , this.FileAttachInfo.files);
				},
                files : [],
			},
            //summerNote info
			SummerNoteInfo : {
				isEdit : true,
				getContent : (content)=>{ 
                    this.SummerNoteInfo.contentStr = content;
                    console.log("this.SummerNoteInfo.contentStr callback ", this.SummerNoteInfo.contentStr); 
                },
                contentStr : "",
			},
        }
    },
    computed:{
        summerNoteContent(){
            return "";
        }
    },
    methods : {
        saveClick(){
			console.log("saveClick");
			// this.content = $('#summernote').summernote('code'); //값 가져오기
			// console.log("content = ["+this.content + "] \n orgContent = [" + this.orgContent +"]");
			// console.log('확인중~' + typeof this.content);
			// console.log(this.files);

			// let formData = new FormData();
			// formData.append('content', this.content);
			// formData.append('title', this.title);
			// this.files.forEach(x=>{
			// 	formData.append('files', x);
			// })

			// this.$axios.post("/ysy/v1/createSummerNote",formData)//,{headers: {'Content-Type': 'multipart/form-data'}} //content-type 셋팅 안해도 올라가네??
			// 	.then(res=>{
			// 		console.log("");
			// 		console.log(res);
			// 		$('#summernote').summernote('code', res.data); 
			// 		alert("success");
			// 	})
			// 	.catch(error=>{
			// 		console.log(error);
			// 	});
		},
    }
}
</script>

<style>

</style>