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

  export default {
    beforeCreate() {
      localStorage.clear()
    },
  };
</script>

<style scoped>
.container{
     max-width: 100vw;
     padding:0px;
  }
</style>