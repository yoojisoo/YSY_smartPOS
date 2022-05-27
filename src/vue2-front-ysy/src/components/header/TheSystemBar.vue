<template>
  <v-system-bar app color="#bababa">
    <v-container class="pa-0" style="max-width:69%">
      <v-row justify="end">
        <v-btn-toggle
          tile
          group
          color="black"
          v-if="isLogin"
        >
          <v-btn value="left" plain @click="logout">로그아웃</v-btn>
        </v-btn-toggle>

        <v-btn-toggle
          tile
          group
          color="black"
          v-else
        >
          <v-btn value="left" plain to='/signIn'>로그인</v-btn>
          <v-divider vertical></v-divider>
          <v-btn value="right" plain to='/signUp'>회원가입</v-btn>
        </v-btn-toggle>
      </v-row>
    </v-container>
  </v-system-bar>
</template>

<script>
export default {
    name: 'main-system-bar',
    methods: {
        logout() {
            this.$store.commit('clearUserInfo')
            this.$router.replace('/signIn')
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

<style scoped>
.v-system-bar {
  color: #bababa;
}
.v-btn {
  color: black;
}
</style>