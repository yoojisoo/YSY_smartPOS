<template>
  <!-- <v-app v-if="isLogin"> -->
  <v-app>
    <!-- <v-toolbar color="primary" dark fixed>
      <v-toolbar-title>Application</v-toolbar-title>
    </v-toolbar> -->
    <main-header/>
    <v-navigation-drawer permanent>
      <v-list
        dense
        nav
      >
        <v-list-item
          v-for="(item, idx) in items"
          :key="idx"
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
    <v-btn @click="userFn"> user접근</v-btn>
    <v-btn @click="adminFn"> admin접근</v-btn>
    <main-footer/>
  </v-app>
  <!-- <v-app v-else>
    <sign-in-vue/>
  </v-app> -->
</template>

<script>
import MainHeader from '@/components/common/TheHeader.vue'
import MainFooter from '@/components/common/TheFooter.vue'
import SignInVue from '@/components/sign/SignIn.vue'

  export default {
    components: {
      'main-header': MainHeader,
      'main-footer': MainFooter,
      'sign-in-vue': SignInVue,
    },
    data () {
      return {
        items: [
          { title: 'Dashboard', icon: 'mdi-view-dashboard' },
          { title: 'Photos', icon: 'mdi-image' },
          { title: 'About', icon: 'mdi-help-box' },
        ],
        config : {
          headers : {
            "access_token":this.$store.state.authStore.loginData.userId
          }
        }

      }
    },
    methods: {
      login() {
        this.$router.replace('/signIn')
      },
      logout() {
        this.$store.commit('clearUserInfo')
        this.$router.replace('/signIn')
      },
      userFn(){
        console.log("userFn before = headers.access_token = "+this.config.headers.access_token);
        this.$axios.get("/ysy/v1/user/testData" , this.config)
        // this.$axios.get("/test21/testData")

        .then(res=>{
          console.log(res);
        })
        .catch(error=>{
           console.log("userFn fn error = "+error);
        })
      },
      adminFn(){
        this.$axios.get("/ysy/v1/admin/testData")
        .then(res=>{
          console.log(res);
        })
        .catch(error=>{
           console.log("adminFn fn error = "+error);
        })
      },
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