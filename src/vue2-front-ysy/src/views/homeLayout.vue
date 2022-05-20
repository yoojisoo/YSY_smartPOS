<template>
  <v-app >
      <v-header
          fixed
        >
        <theHeader v-bind:pageName="pageName" />
    </v-header>
    
    <v-main>
        <v-row justify="start" no-gutters>
            <v-col cols="2" class="hidden-xs-only" v-show="isLeftHidden"  height="100%">
                <v-card >
                    left menu
                    left menu
                    left menu
                    left menu
                    left menu
                    left menu
                </v-card>
            </v-col>
            <v-col cols="1" class="hidden-xs-only">
                <v-btn @click="isLeftHidden = !isLeftHidden"></v-btn>
            </v-col>
            <v-col cols="9" height="100%">
                <userGrid v-bind:noticeList="noticeList"/>
            </v-col>

        </v-row>
        </v-main>
        <v-footer
          fixed
        >
            <theFooter/>

        </v-footer>
  </v-app>
</template>

<script>
import userGrid from '../components/ysyGrid.vue'
import theHeader from '../components/TheHeader.vue'
import theFooter from '../components/TheFooter.vue'

  export default {
    name: 'home',
    data: () => ({
      drawer: null,
      isLeftHidden : true,
      pageName : "home",
      noticeList : ["asdasd"],
    }),
    components: {
      userGrid,
      theHeader,
      theFooter
    },
    created()
    {
        this.getNotice();
    },
    methods:{
       getNotice(){
           console.log("async getNotice");
        },
    }
  }
</script>


