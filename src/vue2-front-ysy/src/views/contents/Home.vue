<!-- main 화면 -->
<template>
  <v-app>
    <!-- Header Start -->
    <mainSystemBar/>
    <mainHeader :pageName="pageName"/>
    <!-- Header End -->

    <!-- Main Start -->
    <v-main>
      <v-container class="py-0" style="max-width:70%; height: 100%; background-color: lightcoral;">
        <v-row justify="center" no-gutters style="height: inherit;">
          <v-col :cols="filterCols">
            <v-card v-if="filterShow" height="100%" tile color="indigo" flat></v-card>
          </v-col>
          <v-col :cols="mainCols">
            <v-row justify="center" no-gutters>
              <v-col align-self="center">
                <v-btn
                  class="ma-2"
                  color="error"
                  @click="chg"
                >
                  😍
                </v-btn>
              </v-col>
            </v-row>
            <v-row align="start" justify="center" no-gutters>
              <v-col cols="12"><ysyGrid/></v-col>
              <v-col cols="6"><ysyGrid/></v-col>
              <v-col cols="6"><ysyGrid/></v-col>
            </v-row>
          </v-col>
        </v-row>
        <!-- <v-navigation-drawer v-model="filterShow" absolute temporary clipped>
          <v-list></v-list>
        </v-navigation-drawer> -->
      </v-container>

    </v-main>
    <!-- Main End -->
    
    <!-- Footer Start -->
    <v-footer class="ma-0 pa-0" fixed app>
      <TheFooter/>
    </v-footer>
    <!-- Footer End -->
  </v-app>
</template>

<script>
import mainSystemBar from '@/components/header/TheSystemBar.vue'
import mainHeader from '@/components/header/TheHeader.vue'
import TheFooter from '@/components/TheFooter.vue'
import ysyGrid from '@/components/ysyGrid.vue'
import TheSystemBar from '@/components/header/TheSystemBar.vue'

// import SignInVue from '@/components/sign/SignIn.vue'

  export default {
    components: {
      mainSystemBar,
      mainHeader,
      TheFooter,
      ysyGrid,
      TheSystemBar
    },
    data () {
      return {
        filterCols: 2,
        mainCols: 10,
        filterShow : true,
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
      chg() {
        this.filterShow = !this.filterShow;

        if(this.filterCols < 2) this.filterCols = 2;
        else                    this.filterCols = 2;

        if(this.mainCols < 12) this.mainCols = 12;
        else                   this.mainCols = 10;
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