<template>
    <!-- flat : remove box-shadow -->

    <!-- web 메뉴 start -->
    <v-app-bar color="purple lighten-2" app dense flat>
      <v-container fluid class="ma-0 pa-0">
        <v-row justify="center" no-gutters>
          <v-col cols="12" xs="12" sm="12" md="8" lg="8" xl="8">
            <v-row justify="center" no-gutters>
              <v-col cols="12" xs="12" sm="2" md="2" lg="2" xl="2" class="pa-0 ma-0" style="display: contents;">
                <v-row justify="center" class="ma-0 pa-0" style="text-align: center; align-self: center;" no-gutters>
                  <v-col cols="2" class="ma-0 pa-0 hidden-sm-and-up">
                      <v-app-bar-nav-icon @click="drawer = true"></v-app-bar-nav-icon>
                  </v-col>
                  <v-col cols="10" align-self="center" class="ma-0 pa-0">
                      <v-btn plain color="white" to="/">홈페이지 로고</v-btn>
                  </v-col>
                </v-row>
              </v-col>
              <v-col cols="10" class="ma-0 pa-0 hidden-xs-only">
                <v-tabs
                  centered
                  background-color="purple lighten-1"
                  dark
                >
                  <v-tab
                    v-for="(item, idx) in headerMenu"
                    :key="idx"
                    :to="item.path"
                  >
                      {{ item.name }}
                  </v-tab>
                </v-tabs>
              </v-col>
            </v-row>
          </v-col>
        </v-row>
      </v-container>
    </v-app-bar>
    <!-- web 메뉴 end -->

    <!-- mobile 메뉴 start -->
    <!-- <v-navigation-drawer
      v-model="drawer"
      absolute
      temporary
    >
      <v-list
        nav
        dense
      >
        <v-list-item 
          v-for="(item, idx) in headerMenu"
          :key="idx"
          :to="item.path">
          <v-icon v-if="item.pmenuId != '' || item.pmenuId != null">{{ item.icon }}</v-icon>
          <v-list-item-title v-if="item.pmenuId != '' || item.pmenuId != null">{{ item.name }}</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
  </header> -->
</template>

<script>
import v_menus from '@/assets/common/vMenus.js'
import MyInfo from '@/components/TheMyInfo.vue'
import headerSystemBar from '@/components/header/TheSystemBar.vue'
import { eventBus } from "@/main.js";

  export default {
    name: 'main-header',
    props: ["pageName"],
    components: {
      'my-info' : MyInfo,
      'header-system-bar' : headerSystemBar,
    },
    data: () => ({
      eventData : "header event data",
      appTitle: 'Title',
      drawer: false,
      group: true,
      headerMenu: null,
      radioGroup: 1,
      radios: 'slide-x-transition-1',
      row: null,
      items: [
        { title: 'Click Me' },
        { title: 'Click Me' },
        { title: 'Click Me' },
        { title: 'Click Me 2' },
      ],
      iconImg: 'mdi-dots-vertical',
    }),
    methods: {
      headerMenuClick (){
        console.log("TheHeader -> headerMenuClick data: " + this.eventData);
         eventBus.$emit("headerTabCallback", this.eventData);
      },


      doMouseOver() {
        this.iconImg = 'mdi-heart'
        console.log(this.iconImg)
      },
      doMouseOut() {
        this.iconImg = 'mdi-dots-vertical'
        console.log(this.iconImg)
      },
      setHeaderMenu(){
        console.log("headerMenuFilter start");
        console.log( v_menus);
        console.log( "this.pageValue = " + this.pageName);
        console.log( this.pageName.indexOf("admin") > -1);

        var list = [];
        v_menus.forEach(x=>{
          if(this.pageName === "admin" && x.isAdmin === "Y" && x.pmenuId== null){ // admin 메뉴 구성
            list.push(x);
          }
          else if(this.pageName === "home" && x.isAdmin === "N" && x.pmenuId== null){// home 메뉴 구성
            list.push(x);
          }
        });
        this.headerMenu = [];
        this.headerMenu = list;
        console.log( "this.headerMenu=");
        console.log( this.headerMenu);
        console.log( "header mounted end");
      },
      async getMenuList(){
          console.log("getMenuList start");
          if(v_menus.length <= 0)
          {
            var res1 = await this.$axios.get("ysy/v1/menu/getMenuList");
            var list = res1.data;
            for(var i = 0 ; i < list.length ; i++){

                  var menuData = {};
                  menuData.path = list[i].menu_path;
                  menuData.name = list[i].menu_nm;
                  menuData.icon = "mdi-home";
                  menuData.component = () => import(list[i].menu_pull_path);
                  menuData.pmenuId = list[i].pmenu_id;
                  menuData.isAdmin = list[i].is_admin;
                  v_menus.push(menuData);
            }
          }

          console.log(v_menus);

          this.setHeaderMenu();
       }
    },
    mounted()
    {
      this.getMenuList();
    },

    created()
    {
      console.log("header created start");
    }
  }
</script>

<style scoped>
.v-container {
  max-width:70%;
  height: 100%;
}
</style>