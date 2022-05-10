<template>
    <v-app>
        <v-main>
            <v-container fill-height fluid>
                <v-layout align-center justify-center>
                    <v-flex xs12 sm8 md4>
                        <v-card class="elevation-12">
                            <v-toolbar dark color="primary">
                                <v-toolbar-title>Sign In</v-toolbar-title>
                            </v-toolbar>
                            <v-card-text>
                                <v-text-field
                                    v-model="signInInfo.account"
                                    name="user_id"
                                    label="userId"
                                    type="text"
                                    placeholder="userId"
                                    required
                                ></v-text-field>

                                <v-text-field
                                    v-model="signInInfo.password"
                                    name="user_pw"
                                    label="password"
                                    type="password"
                                    placeholder="password"
                                    required
                                ></v-text-field>

                                <v-btn class="mt-4" color="primary" value="sign in" @click="signIn">login</v-btn>
                                <div class="grey--text mt-4" v-on:click="signUp">{{toggleMessage}}</div>
                            </v-card-text>
                        </v-card>
                    </v-flex>
                </v-layout>
            </v-container>
        </v-main>
    </v-app>
</template>

<script>
export default {
    name: "App",
    data() {
        return {
            toggleMessage: "Sign Up",
            signInInfo : {
                account : "",
                password : ""
            },
        }
    },
    methods: {
        signIn() {
            const { signInInfo } = this;
            console.log(signInInfo.account + ", " + signInInfo.password)

            this.$http.post("/testCtrl/signIn", signInInfo)
                .then((res) => {
                    console.log(res.data);
                    if(res.data === "ok") {
                        try {
                            this.$store.dispatch('setUserInfo', signInInfo.account)
                            alert("로그인 완료");
                            this.$router.replace({ name: "home"})
                        } catch (error) {
                            alert("로그인 실패");
                        }
                    }
                    else {
                        alert("로그인 실패 관리자 문의바람");
                    }
                });
        },
        signUp() {
            this.$router.replace({ name: 'signUp' })
        },
    },
}
</script>
