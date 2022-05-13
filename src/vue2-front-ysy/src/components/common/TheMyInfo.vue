<template>
  <!-- <v-container
    fluid
    style="height: 300px"
  > -->
    <!-- <v-row justify="center"> -->
        <v-menu
            open-on-hover
            bottom
            min-width="200px"
            rounded
            offset-y
            v-if="isLogin"
        >
            <template v-slot:activator="{ on }">
                <v-badge
                    top
                    color="deep-purple accent-4"
                    dot
                    offset-x="20"
                    offset-y="20"
                >
                    <v-btn
                        icon
                        x-large
                        v-on="on"
                    >
                        <v-icon>mdi-account</v-icon>
                    </v-btn>
                </v-badge>
                <h3>{{ userId }}</h3>
            </template>
            <v-card>
                <v-list-item-content class="justify-center">
                    <div class="mx-auto text-center">
                        <h3>{{ userId }}</h3>
                        <!-- <p class="text-caption mt-1">
                            {{ user.email }}
                        </p> -->
                        <v-divider class="my-3"></v-divider>
                        <v-btn
                            depressed
                            rounded
                            text
                            @click="logout"
                        >
                            Logout
                        </v-btn>
                    </div>
                </v-list-item-content>
            </v-card>
        </v-menu>
        <v-btn plain color="white" to="/signIn" v-else>login</v-btn>
    <!-- </v-row> -->
  <!-- </v-container> -->
</template>

<script>
    export default {
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