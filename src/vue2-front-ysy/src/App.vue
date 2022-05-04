<template>
  <v-app>
    <main-system-bar/>
    <main-header/>

    <v-main app>
      <v-container fluid>
        <router-view/>
      </v-container>
    </v-main>
    <!-- <mainNavi/> -->
    <login-page/>

  </v-app>
</template>

<script>
import MainSystemBar from '@/components/TheSystemBar.vue'
import MainHeader from '@/components/TheHeader.vue'
import MainFooter from '@/components/TheFooter.vue'
// import LoginPage from '@/components/SignIn.vue'
// import MainNavi from '@/components/TheNavi_02.vue'

  export default {
    components: {
      'main-system-bar': MainSystemBar,
      'main-header': MainHeader,
      'main-footer': MainFooter,
      // 'login-page': LoginPage,
      // 'mainNavi': MainNavi
    }
  };
</script>
