<!-- 22.06.29 yoojisoo -->
<template>
  <div class="editor-page">
    <div id="summernote"></div>
  </div>
</template>

<script>
export default {
  props: ['editorInfo', 'isSave'],
  data() {
    return {
      summernoteStr : '',
    }
  },
  mounted() {
    $('#summernote').summernote({
      height: 400,
      minHeight: null,
      maxHeight: null,
      focus: true,
      lang : 'ko-KR',

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
    getContent() {
      if(this.editorInfo.isSave) {
        this.summernoteStr = $('#summernote').summernote('code'); //값 가져오기
        console.log('확인중~' + typeof(this.summernoteStr));
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

<style>

</style>