<template>
  <v-app id="inspire">
    <v-navigation-drawer
      v-model="drawer"
      app
    >
    </v-navigation-drawer>

    <!-- <v-app-bar app>
      <v-app-bar-nav-icon @click="drawer = !drawer"></v-app-bar-nav-icon>
      <v-toolbar-title>Application</v-toolbar-title>
    </v-app-bar> -->
        <theHeader v-bind:pageName="pageName" />
    <v-main>
        <userGrid />
    </v-main>
  </v-app>
</template>

<script>
import userGrid from '../../components/admin/ysyGrid.vue'
import theHeader from '../../components/common/TheHeader.vue'

  export default {
    name: 'admin-base',
    data: () => ({
      drawer: null,
      pageName : "admin",
    }),
    components: {
      userGrid,
      theHeader
    }
    
  }
</script>