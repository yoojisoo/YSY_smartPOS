<!--상단 메뉴 component -->
<template>
  <header>
    <header-system-bar/>
    <!-- flat : remove box-shadow -->
    <!-- web 메뉴 start -->
    <v-app-bar color="purple lighten-2" app dense flat>
      <v-container style="max-width:70%">
        <v-row align="center" justify="center" class="purple lighten-1">
          <v-col cols="1" class="ma-0 pa-0">
            <span class="hidden-sm-and-up">
              <v-app-bar-nav-icon @click="drawer = true"></v-app-bar-nav-icon>
            </span>
            <v-app-bar-title>
              <v-btn plain color="white" to="/">SEMES</v-btn>
            </v-app-bar-title>
          </v-col>
          <v-col cols="11" class="ma-0 pa-0">
            <v-tabs
              centered
              class="hidden-xs-only"
              background-color="purple lighten-1"
              dark
            >
              <!-- :to="item.path" -->
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
      </v-container>
    </v-app-bar>
    <!-- web 메뉴 end -->

    <!-- mobile 메뉴 start -->
    <v-navigation-drawer
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
  </header>
</template>

<script>
import v_menus from '@/assets/common/vMenus.js'
import MyInfo from '@/components/TheMyInfo.vue'
import headerSystemBar from '@/components/TheSystemBar.vue'
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
                  menuData.path = list[i].menuPath;
                  menuData.name = list[i].menuNm;
                  menuData.icon = "mdi-home";
                  menuData.component = () => import(list[i].menuFullPath);
                  menuData.pmenuId = list[i].pmenuId;
                  menuData.isAdmin = list[i].isAdmin;
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
.v-icon {
  padding-right: 10px;
}
</style>