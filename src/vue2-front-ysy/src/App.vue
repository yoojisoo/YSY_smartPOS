<template>
  <!-- <v-app> -->
    <!-- <main-system-bar/>
    <main-header/>

    <v-main app>
      <v-container fluid>
        <router-view/>
      </v-container>
    </v-main> -->
    <!-- <login-page/> -->
    <v-container fluid class="grey lighten-5">
      <router-view/>
    </v-container>
    <!-- <mainNavi/> -->

  <!-- </v-app> -->
</template>

<script>
// import MainSystemBar from '@/components/TheSystemBar.vue'
// import MainHeader from '@/components/TheHeader.vue'
// import MainFooter from '@/components/TheFooter.vue'
// import LoginPage from '@/components/SignIn.vue'
// import MainNavi from '@/components/TheNavi_02.vue'

  export default {
    components: {
      // 'main-system-bar': MainSystemBar,
      // 'main-header': MainHeader,
      // 'main-footer': MainFooter,
      // 'login-page': LoginPage,
      // 'mainNavi': MainNavi
    },
  };
</script>

<style scoped>
.container{
     max-width: 100vw;
     padding:0px;
  }
</style>