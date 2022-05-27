<template>
  <v-container fluid style="background-color: lightgoldenrodyellow;">
    <!-- singleSelect : false - 전체선택, selected : [] -->
    <v-data-table
    v-model="selected"
    :headers="headers"
    :items="userInfo"
    single-select
    item-key="userId"
    sort-by="group"
    class="elevation-1"
    @click:row="rowClick"
    >  
      <template v-slot:top>
        <v-toolbar
          flat
        >
          <v-toolbar-title>{{gridTitle}}</v-toolbar-title>
          <v-divider
            class="mx-4"
            inset
            vertical
          ></v-divider>

          <v-spacer />

          <v-dialog
            v-model="dialog"
            max-width="500px"
          >
            <!-- $attrs는 이제 class와 style를 포함하여, 컴포넌트에 전달되는 모든 속성을 포함
                on : onClick 같은 이벤트 발생인거 같다...
            -->
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                color="primary"
                dark
                class="mb-2"
                v-bind="attrs"
                v-on="on"
              >
                New Item
              </v-btn>
            </template>

            <v-card>
              <v-card-title>
                <span class="text-h5">{{ formTitle }}</span>
              </v-card-title>

              <v-card-text>
                <v-container>
                  <v-row>
                    <v-col
                      cols="12"
                      sm="6"
                      md="4"
                    >
                      <v-text-field
                        v-model="editedData.userId"
                        label="ID"
                      ></v-text-field>
                    </v-col>
                    <v-col
                      cols="12"
                      sm="6"
                      md="4"
                    >
                      <v-text-field
                        v-model="editedData.userPw"
                        label="Password"
                      ></v-text-field>
                    </v-col>
                    <v-col
                      cols="12"
                      sm="6"
                      md="4"
                    >
                      <v-text-field
                        v-model="editedData.userPhone"
                        label="Cell phone number"
                      ></v-text-field>
                    </v-col>
                    <v-col
                      cols="12"
                      sm="6"
                      md="4"
                    >
                      <v-text-field
                        v-model="editedData.userGroup"
                        label="Authority"
                      ></v-text-field>
                    </v-col>
                    <v-col
                      cols="12"
                      sm="6"
                      md="4"
                    >
                      <v-text-field
                        v-model="editedData.bizCode"
                        label="Business registration number"
                      ></v-text-field>
                    </v-col>
                  </v-row>
                </v-container>
              </v-card-text>

              <!-- 수정 또는 저장 - 팝업창에서의 선택  -->
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                  color="blue darken-1"
                  text
                  @click="close"
                >
                  Cancel
                </v-btn>
                <v-btn
                  color="blue darken-1"
                  text
                  @click="save"
                >
                  Save
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>

          <!-- 진짜 삭제할건지 -->
          <v-dialog v-model="dialogDelete" max-width="500px">12
            <v-card>
              <v-card-title class="text-h5">Are you sure you want to delete this item?</v-card-title>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue darken-1" text @click="closeDelete">Cancel</v-btn>
                <v-btn color="blue darken-1" text @click="deleteItemConfirm">OK</v-btn>
                <v-spacer></v-spacer>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-toolbar>
      </template>
      

      
      <!-- 수정, 삭제 아이콘 -->
    

      <!-- 아무 데이터도 없을때 나오는 버튼 -->
      <template v-slot:no-data>
        <v-btn
          color="primary"
          @click="initialize"
        >
          Reset
        </v-btn>
      </template>

    </v-data-table>
  </v-container>
</template>

<script>

import { eventBus } from "@/main.js";

  export default {
    name: 'admin-user-grid',
    props : ["noticeList"],
    data: () => ({
      selectedRow: -1,
      gridTitle : "Grid Title",
      singleSelect: false,
      selected: [],
      dialog: false,
      dialogDelete: false,
      headers: [
        { text: 'ID'      , value: 'userId',  align: 'start'  },
        { text: 'password', value: 'userPw'                   },
        { text: 'phone'   , value: 'userPhone'                },
        { text: 'group'   , value: 'userGroup'                },
        { text: 'bizcode' , value: 'bizCode'                  },
        { text: 'Actions' , value: 'actions', sortable: false },
      ],
      userInfo: [],
      editedIndex: -1,
      editedData: {
        userId: '',
        userPw: '',
        userPhone: '',
        userGroup: '',
        bizCode: '',
        actions: '1',
      },
      defaultItem: {
        userId: '',
        userPw: '',
        userPhone: '',
        userGroup: '',
        bizCode: '',
        actions: '1',
      },
    }),

    computed: {
      formTitle () {
        return this.editedIndex === -1 ? 'New Item' : 'Edit Item'
      },
    },

    watch: {
      // 버튼팝업창 액션 - true, false를 주는 값의 모션 || 그 외의 모션 (바탕화면 클릭 시?) 발동하는 메소드 
      dialog (val) {
        //val이 있으면 왼쪽(true 또는 false), 없으면 오른쪽
        val || this.close()
      },

      // 딜리트 팝업창 액션
      dialogDelete (val) {
        val || this.closeDelete()
      },
    },

    created () {
      this.initialize();
      //TheHeader Tab menu click Callback
       eventBus.$on("headerTabCallback", (headerCallbackValue) => {
        // window.alert(headerValue);
        this.gridTitle = headerCallbackValue;
      });
    },

    methods: {
      /** grid click event */
      rowClick(item, row){
        console.log( item);
        console.log( row);
        
        row.select(true);
         this.selectedRow=item.userId;
        
      },
      initialize () {

        // this.noticeList;


        this.userInfo = [
          {userId: 'clubbboy@naver.com1',userPw: '1234',userPhone: '010-8756-9851',userGroup: 'admin',bizCode: '0001',},
          {userId: 'clubbboy@naver.com2',userPw: '1234',userPhone: '010-8756-9851',userGroup: 'admin',bizCode: '0001',},
          {userId: 'clubbboy@naver.com3',userPw: '1234',userPhone: '010-8756-9851',userGroup: 'admin',bizCode: '0001',},
          {userId: 'clubbboy@naver.com4',userPw: '1234',userPhone: '010-8756-9851',userGroup: 'admin',bizCode: '0001',},
          {userId: 'clubbboy@naver.com5',userPw: '1234',userPhone: '010-8756-9851',userGroup: 'admin',bizCode: '0001',},
          {userId: 'clubbboy@naver.com6',userPw: '1234',userPhone: '010-8756-9851',userGroup: 'admin',bizCode: '0001',},
          {userId: 'clubbboy@naver.com7',userPw: '1234',userPhone: '010-8756-9851',userGroup: 'admin',bizCode: '0001',},
          {userId: 'clubbboy@naver.com8',userPw: '1234',userPhone: '010-8756-9851',userGroup: 'admin',bizCode: '0001',},
          {userId: 'clubbboy@naver.com9',userPw: '1234',userPhone: '010-8756-9851',userGroup: 'admin',bizCode: '0001',},
          {userId: 'clubbboy@naver.com0',userPw: '1234',userPhone: '010-8756-9851',userGroup: 'admin',bizCode: '0001',},
          {userId: 'clubbboy@naver.com11',userPw: '1234',userPhone: '010-8756-9851',userGroup: 'admin',bizCode: '0001',},
          {userId: 'clubbboy@naver.com12',userPw: '1234',userPhone: '010-8756-9851',userGroup: 'admin',bizCode: '0001',},
          {userId: 'clubbboy@naver.com13',userPw: '1234',userPhone: '010-8756-9851',userGroup: 'admin',bizCode: '0001',},
          {userId: 'clubbboy@naver.com14',userPw: '1234',userPhone: '010-8756-9851',userGroup: 'admin',bizCode: '0001',},
          {userId: 'clubbboy@naver.com15',userPw: '1234',userPhone: '010-8756-9851',userGroup: 'admin',bizCode: '0001',},
          {userId: 'clubbboy@naver.com16',userPw: '1234',userPhone: '010-8756-9851',userGroup: 'admin',bizCode: '0001',},
          {userId: 'clubbboy@naver.com17',userPw: '1234',userPhone: '010-8756-9851',userGroup: 'admin',bizCode: '0001',},
          {userId: 'clubbboy@naver.com18',userPw: '1234',userPhone: '010-8756-9851',userGroup: 'admin',bizCode: '0001',},
          {userId: 'clubbboy@naver.com19',userPw: '1234',userPhone: '010-8756-9851',userGroup: 'admin',bizCode: '0001',},
          {userId: 'clubbboy@naver.com20',userPw: '1234',userPhone: '010-8756-9851',userGroup: 'admin',bizCode: '0001',},
          {userId: 'clubbboy@naver.com21',userPw: '1234',userPhone: '010-8756-9851',userGroup: 'admin',bizCode: '0001',},
          
        ]
      },

      //수정 아이콘 함수
      editIcon (item) {
        this.editedIndex = this.userInfo.indexOf(item)
        this.editedData = Object.assign({}, item)
        this.dialog = true
      },

      // 휴지통 아이콘 함수
      deleteIcon (item) {
        this.editedIndex = this.userInfo.indexOf(item)
        this.editedData = Object.assign({}, item)
        //true시 진짜 삭제할건지 물어보는 팝업창 뜸
        this.dialogDelete = true
      },

      // 휴지통 -> OK버튼
      deleteItemConfirm () {
        this.userInfo.splice(this.editedIndex, 1)
        this.closeDelete()
      },

      // 팝업창 - Cancel (취소)
      close () {
        this.dialog = false
        this.$nextTick(() => {
          this.editedData = Object.assign({}, this.defaultItem)
          this.editedIndex = -1
        })
      },

      // 휴지통 -> 캔슬
      closeDelete () {
        this.dialogDelete = false
        this.$nextTick(() => {
          //빈값으로 초기화
          this.editedData = Object.assign({}, this.defaultItem)
          this.editedIndex = -1
        })
      },

      // new item 또는 수정 클릭 후 -> SAVE 버튼 클릭시 발동하는 함수
      save () {
        var resData = this.validationCheck(  "userId" , "bizCode");
        if(resData !== undefined && resData !== null && resData !== ""){
          alert("["+resData + "] 값이 없습니다.");
          return;
        }

        //row의 인덱스가 있으면
        if (this.editedIndex > -1) {
          //두번째걸로 오버라이드
          Object.assign(this.userInfo[this.editedIndex], this.editedData)
          
        //row의 인덱스가 없으면
        } else {
          //new item의 경우
          this.userInfo.push(this.editedData)
        }
        this.close()
      },
      validationCheck ( ...str){
        for( var i = 0 ; i < str.length ; i++){
          if(this.editedData[str[i]] === null || this.editedData[str[i]] === "") 
            return str[i];
        }
        
      }
    },
  }
</script>
<style>
tr.v-data-table__selected {
    background: #7d92f5 !important;
  }

</style>