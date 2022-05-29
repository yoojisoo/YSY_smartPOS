<!-- main 화면 -->
<template>
  <v-app>
    <!-- Header Start -->
    <mainSystemBar/>
    <mainHeader :pageName="pageName"/>
    <!-- Header End -->

    <!-- Main Start -->
    <v-main>
      <v-container fluid pa-0 ma-0 style="background-color: lightgray;">
        <v-row justify="center" no-gutters>
          <v-col cols="12" xs="12" sm="12" md="8" lg="8" xl="8" style="background-color: lightslategray;">
            <v-row justify="center" no-gutters style="height: inherit;">
              <v-col :cols="filterCols" class="hidden-xs-only">
                <v-card v-if="filterShow" height="100%" tile color="indigo" flat></v-card>
              </v-col>
              <v-col :cols="mainCols">
                <!-- <v-row justify="center" no-gutters>
                  <v-col align-self="center" class="hidden-xs-only">
                    <v-btn
                      class="ma-2 hidden-xs-only"
                      @click="colsChange"
                    >
                      web filter
                    </v-btn>
                  </v-col>
                  <v-col align-self="center" class="hidden-sm-and-up">
                    <v-btn
                      class="ma-2"
                      @click="colsChange"
                    >
                      mobile filter 
                    </v-btn>
                  </v-col>
                </v-row> -->
                <v-row justify="center" no-gutters>
                  <v-col clos="12">
                    <v-card
                      color="#385F73"
                      dark
                    >
                      <v-card-title class="text-h5">
                        Unlimited music now
                      </v-card-title>

                      <v-card-subtitle>Listen to your favorite artists and albums whenever and wherever, online and offline.</v-card-subtitle>

                      <v-card-actions>
                        <v-btn text>
                          Listen Now
                        </v-btn>
                      </v-card-actions>
                    </v-card>
                  </v-col>
                </v-row>
                <v-row align="start" justify="center" no-gutters>
                  <v-col cols="12"><ysyGrid/></v-col>
                  <v-col cols="6"><ysyGrid/></v-col>
                  <v-col cols="6"><ysyGrid/></v-col>
                </v-row>
              </v-col>
            </v-row>
          </v-col>
        </v-row>
      </v-container>
    </v-main>
    <!-- Main End -->
    
    <!-- Footer Start -->
    <v-footer class="ma-0 pa-0" fixed app>
      <mainFooter/>
    </v-footer>
    <!-- Footer End -->
  </v-app>
</template>

<script>
import mainSystemBar from '@/components/header/TheSystemBar.vue'
import mainHeader from '@/components/header/TheHeader.vue'
import mainFooter from '@/components/TheFooter.vue'
import ysyGrid from '@/components/ysyGrid.vue'

  export default {
    components: {
      mainSystemBar,
      mainHeader,
      mainFooter,
      ysyGrid,
    },
    data () {
      return {
        filterCols: 0,
        mainCols: 12,
        filterShow : false,
        pageName : "home",
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
      colsChange() { // 필터와 메인 그리드 cols 변경 (아이콘 눌렀을때)
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