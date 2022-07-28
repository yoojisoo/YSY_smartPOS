<!-- create by clubbboy@naver.com
    첨부파일 모듈
    props
        info : {
            isMulti : bool -> default false : 파일 멀티 선택 여부
            label : String -> text box title
            placeholder : String -> text box placeholder
            callback : function -> params = 선택된 files
        }

        
 -->
<template>
  <!-- 파일 첨부 영역 -->
    <div>
        <v-row no-gutters>
            <v-col cols="12">
                <v-file-input v-model="files" class="mx-3"
                        color="deep-purple accent-4"
                        counter
                        :label="info && info.label?info.label : 'Upload Files'"
                        :multiple = "info && info.isMulti?info.isMulti: false"
                        :dense = false
                        accept="image/*"
                        :placeholder="info && info.placeholder?info.placeholder : 'Select your files'"
                        :prepend-icon="info && info.iconNm?info.iconNm : ''"
                        outlined
                        show-size
                        @change="fileChange">
                    <template v-slot:selection="{  text }">
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
    </div>
    
</template>

<script>
export default {
    props:["info"],
    data(){
        return {
            files : [],
        }
    },
    methods:{
        fileAttachCallback(){
            if(this.info && this.info.callback){
                this.info.callback(this.files);
            }
        },
        fileChange(e) {
            this.fileAttachCallback();
			// console.log(e);
		},
    }

}
</script>

<style>

</style>