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
    <div>
      <v-row>
        <v-col>
          <!-- <theHeader v-bind:pageName="pageName" /> -->
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <!-- left menu -->
        </v-col>
        <v-col>
          <!-- content  -->
          <router-view class="view two" name="admin"></router-view>
        </v-col>
      </v-row>
    </div>
        
        
    <v-main>
        
        
    </v-main>
  </v-app>
</template>

<script>
import userGrid from '../components/LsyGrid.vue'
// import theHeader from '../components/header/TheHeader.vue'

  export default {
    name: 'admin-base',
    data: () => ({
      drawer: null,
      pageName : "admin",
    }),
    components: {
      userGrid,
      // theHeader
    }
    
  }
</script>