<!--
    *** props ***
    - data : {
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
  <v-card class="pa-3 ma-0 mx-2" outlined >
      <v-row no-gutters>
          <v-col cols="8" class="ma-0 pa-0">
              <v-card-title class="pl-1 pt-0 ma-0 text-h5" @click="detailMove('content')" v-text="data.title" />
              <v-card-subtitle @click="detailMove('content')"  class="pa-0 pr-3">

                  <v-card-text v-if="data.contentList" class="pa-0">
                      <div v-for="(content1, idx2) in data.contentList" :key="idx2">
                          {{content1.title}} : {{content1.text}}
                      </div>
                  </v-card-text>
                  <v-card-text v-else class="pa-0">
                      <div
                          class="d-inline-block text-truncate cursor_finger"
                          style="max-width: 100%;">
                          {{!data.content ? data.content : "Data Not Found!"}}
                      </div>
                  </v-card-text>
              </v-card-subtitle>
          </v-col>


          <v-col cols="4"  class="ma-0 pa-0" style="text-align: center;"  @click="detailMove('content')">
              <v-avatar size="110" :tile = "data && data.isTile? data.isTile : true">
                  <v-img class="cursor_finger" :src="data.img? data.img : default_img" />
              </v-avatar>
          </v-col>
      </v-row>
      <v-row no-gutters v-if="data.isAction">
          <v-col cols="12">
              <v-card-actions>
                  <span v-if="data.viewCnt">
                      <v-icon>mdi-eye </v-icon>
                      {{data && data.viewCnt ?data.viewCnt : 0}}
                  </span>
                  <v-btn @click="detailMove('comment')" icon>
                      <v-icon>mdi-comment</v-icon>
                      {{data && data.commentCnt ?data.commentCnt : 0}}
                  </v-btn>
                  <v-spacer></v-spacer>
                  <v-btn icon>
                      <v-icon>mdi-heart</v-icon>
                  </v-btn>
                  <v-btn icon>
                      <v-icon>mdi-bookmark</v-icon>
                  </v-btn>
                  <v-btn icon>
                      <v-icon>mdi-share-variant</v-icon>
                  </v-btn>
                  <v-btn v-if="data.isOwner" icon>
                      <v-icon>mdi-pencil</v-icon>
                  </v-btn>
              </v-card-actions>
          </v-col>
      </v-row>
  </v-card>
</template>

<script>
export default {
  props :["data" ,"styleInfo"],
  data(){
    return {
      default_img : "https://cdn.vuetifyjs.com/images/cards/cooking.png",
    }
  },
  methods:{
    detailMove(type){
      console.log("detailMove => "+type);
    }
  },

}
</script>

<style>

</style>