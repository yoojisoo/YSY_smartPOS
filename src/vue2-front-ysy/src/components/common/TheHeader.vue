<template>
  <header>
    <v-app-bar color="deep-purple accent-4">
      <span class="hidden-sm-and-up">
        <v-app-bar-nav-icon @click="drawer = true"></v-app-bar-nav-icon>
      </span>
      <v-app-bar-title>
        <!-- <v-img class="hidden-xs" max-width="100" src='@/assets/logo.png'/>
        <span class="hidden-sm-and-up" > SEMES </span> -->
        <span> SEMES </span>
      </v-app-bar-title>
      <v-tabs centered class="hidden-xs-only" color="white" dark>
        <v-tab
          v-for="(item, idx) in sideMenu"
          :key="idx"
          :to="item.path"
        >
          {{ item.name }}
        </v-tab>
      </v-tabs>
      <v-spacer/>
      <v-spacer/>
      <v-spacer/>
      <v-spacer/>
      <v-spacer/>
      <!-- <v-radio-group
        v-model="row"
        row
      >
        <v-radio
          label="Option 1"
          value="radio-1"
          row
        ></v-radio>
        <v-radio
          label="Option 2"
          value="radio-2"
        ></v-radio>
      </v-radio-group> -->
      <v-menu
        bottom
        left
      >
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            icon
            color="white"
            v-bind="attrs"
            v-on="on"
          >
            <v-icon @mouseover="doMouseOver" @mouseout="doMouseOut">{{ iconImg }}</v-icon>
          </v-btn>
        </template>

        <v-list>
          <v-list-item
            v-for="(item, idx) in items"
            :key="idx"
          >
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
    </v-app-bar>

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
          v-for="(item, idx) in sideMenu"
          :key="idx"
          :to="item.path">
          <v-icon>{{ item.icon }}</v-icon>
          <v-list-item-title>{{ item.name }}</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
  </header>
</template>

<script>
import v_menus from '@/assets/common/vMenus.js'

  export default {
    name: 'main-header',
    data: () => ({
      appTitle: 'Title',
      drawer: false,
      group: true,
      sideMenu: v_menus,
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
      doMouseOver() {
        this.iconImg = 'mdi-heart'
        console.log(this.iconImg)
      },
      doMouseOut() {
        this.iconImg = 'mdi-dots-vertical'
        console.log(this.iconImg)
      }
    }
  }
</script>

<style scoped>
.v-icon {
  padding-right: 10px;
}
</style>