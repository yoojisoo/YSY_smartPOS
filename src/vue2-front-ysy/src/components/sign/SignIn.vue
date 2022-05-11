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
                                    v-model="signInInfo.username"
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
                username : "",
                password : ""
            },
           

        }
    },
    methods: {
        async signIn() {
            // const { signInInfo } = this;
            console.log(this.signInInfo.username + ", " + this.signInInfo.password)

            await this.$axios.post("/login", this.signInInfo)
                .then((res) => {
                    debugger;
                    console.log(res.headers.access_token);
                    console.log(res.headers.refresh_token);
                    if(res.headers.state === "200") {
                        try {
                            // this.$axios.defaults.headers.common["access_token"] = res.headers.access_token;
                            this.$store.dispatch('setUserInfo', res.headers.access_token)
                            this.$router.replace({ name: "home" })
                        } catch (error) {
                            alert("로그인 실패");
                        }
                    }
                    else {
                        alert("아이디나 비밀번호가 틀렸습니다.");
                    }
                });
        },
        signUp() {
            this.$router.replace({ name: 'signUp' })
        },
    },
}
</script>
