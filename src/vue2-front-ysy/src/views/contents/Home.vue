<!-- main 화면 -->
<template>
  <v-app>
      <TheHeader :pageName="pageName"/>
      <v-main>
        <v-container fluid>
          <v-row justify="center" no-gutters>
            <v-col cols="2">
              <v-btn
                class="ma-2"
                color="secondary"
              >
                Accept Terms
              </v-btn>
            </v-col>
            <v-col cols="10">
              <v-row justify="center" no-gutters>
                <v-col align-self="center">
                  <v-btn
                    class="ma-2"
                    color="secondary"
                  >
                    Accept Terms
                  </v-btn>
                </v-col>
              </v-row>
              <v-row>
                <v-col><ysyGrid/></v-col>
              </v-row>
            </v-col>
          </v-row>
        </v-container>
      </v-main>
      <v-footer class="ma-0 pa-0" fixed>
        <TheFooter/>
      </v-footer>
       
      
  

  </v-app>

</template>

<script>
import TheHeader from '@/components/TheHeader.vue'
import TheFooter from '@/components/TheFooter.vue'
import ysyGrid from '@/components/ysyGrid.vue'

// import SignInVue from '@/components/sign/SignIn.vue'

  export default {
    components: {
      TheHeader,
      TheFooter,
      ysyGrid,
      // 'sign-in-vue': SignInVue,
    },
    data () {
      return {
        pageName : "home",
        items: [],
        loading: false,
        loading2: false,
        loading3: false,
        loading4: false,
        loading5: false,
        config : {
          headers : {
            "access_token":this.$store.state.authStore.loginData.userToken
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
        this.$axios.post("/ysy/v1/user/testData1" )//,"", this.config
        // this.$axios.get("/test21/testData")

        .then(res=>{
          console.log("user 접근 res = ");
          console.log(res);
        })
        .catch(error=>{
           console.log("userFn fn error = "+error);
        })
      },
      userFnGet(){
        console.log("userFn before = headers.access_token = "+this.config.headers.access_token);
        this.$axios.get("/ysy/v1/user/gettestData1" )//,"", this.config
        // this.$axios.get("/test21/testData")

        .then(res=>{
          console.log("user 접근 res = ");
          console.log(res);
        })
        .catch(error=>{
           console.log("userFn fn error = "+error);
        })
      },
      adminFn(){
        this.$axios.post("/ysy/v1/admin/testData")
        .then(res=>{
          console.log(res);
        })
        .catch(error=>{
           console.log("adminFn fn error = "+error);
        })
      },
      basicFn(){//일반접근
        this.$axios.get("/test21/testData")
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