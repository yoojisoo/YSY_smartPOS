<template>
  <v-app>
    <!-- <v-toolbar color="primary" dark fixed>
      <v-toolbar-title>Application</v-toolbar-title>
    </v-toolbar> -->
    <!-- <main-system-bar/> -->
    <main-header/>
    <v-navigation-drawer permanent>
        <v-list>
          <v-list-item class="px-2">
            <v-list-item-avatar>
              <v-img src="https://randomuser.me/api/portraits/women/85.jpg"></v-img>
            </v-list-item-avatar>
          </v-list-item>

          <v-list-item link>
            <v-list-item-content>
              <v-list-item-title class="text-h6">
                {{ userId }}
              </v-list-item-title>
              <v-list-item-subtitle v-if="isLogin" v-on:click="logout">log out</v-list-item-subtitle>
              <v-list-item-subtitle v-else v-on:click="login">log in</v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
        </v-list>

      <v-divider></v-divider>

      <v-list
        dense
        nav
      >
        <v-list-item
          v-for="item in items"
          :key="item.title"
          link
        >
          <v-list-item-icon>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
    <main-footer/>
  </v-app>
</template>

<script>
import MainSystemBar from '@/components/TheSystemBar.vue'
import MainHeader from '@/components/TheHeader.vue'
import MainFooter from '@/components/TheFooter.vue'

  export default {
    components: {
      'main-system-bar': MainSystemBar,
      'main-header': MainHeader,
      'main-footer': MainFooter,
    },
    data () {
      return {
        items: [
          { title: 'Dashboard', icon: 'mdi-view-dashboard' },
          { title: 'Photos', icon: 'mdi-image' },
          { title: 'About', icon: 'mdi-help-box' },
        ],
      }
    },
    methods: {
      login() {
        this.$router.replace({ name:'signIn' })
      },
      logout() {
        this.$store.commit('clearUserInfo')
        this.$router.replace({ name:'signIn' })
      }
    },
    computed: {
      isLogin() {
        return this.$store.getters.isLogin
      },
      userId() {
        return this.$store.state.authStore.loginData.userId
      }
    }
  }
</script>