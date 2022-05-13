<template>
    <v-app>
        <v-main>
            <v-container fill-height fluid>
                <v-row justify="center" no-gutters>
                    <v-col cols="auto">
                        <v-btn icon to="/">
                            <v-icon>mdi-heart</v-icon>
                        </v-btn>
                    </v-col>
                    <v-col cols="12">
                        <v-row justify="center" no-gutters>
                            <v-col cols="md-4 xs-12">
                                <v-card elevation="0">
                                    <v-card-text>
                                        <v-row justify="space-between" no-gutters>
                                            <v-col cols="12" class="mb-6">
                                                <v-text-field
                                                    v-model="signInInfo.username"
                                                    name="user_id"
                                                    type="text"
                                                    prepend-inner-icon="mdi-human-greeting-variant"
                                                    placeholder="  Hello, Donkey! What's your ID?"
                                                    required
                                                    filled
                                                    hide-details
                                                ></v-text-field>
                                            </v-col>
                                            <v-col cols="12" class="mb-6">
                                                <v-text-field
                                                    v-model="signInInfo.password"
                                                    name="user_pw"
                                                    type="password"
                                                    prepend-inner-icon="mdi-donkey"
                                                    placeholder="  Hmm.. My Password is..."
                                                    required
                                                    filled
                                                    hide-details
                                                ></v-text-field>
                                            </v-col>
                                            <v-col
                                            cols="auto"
                                            class="mb-1"
                                            v-for="(item, idx) in toggleMessage"
                                            :key="idx"
                                            >
                                                <span>{{ item.title }}</span>
                                            </v-col>
                                            <v-col cols="12">
                                                <v-btn block class="my-2 deep-purple lighten-1" dark value="sign in" @click="signIn">로그인</v-btn>
                                            </v-col>
                                            <v-col cols="12">
                                                <v-btn block outlined class="my-2" color="#7E57C2" value="sign up" @click="signUp">회원가입</v-btn>
                                            </v-col>
                                        </v-row>
                                    </v-card-text>
                                </v-card>
                            </v-col>
                        </v-row>
                    </v-col>
                </v-row>
            </v-container>
        </v-main>
    </v-app>
</template>

<script>
import userInfo from "../../assets/common/userInfo.js"


export default {
    name: "App",
    data() {
        return {
            toggleMessage: [
                {title: "REMEMBER"},
                {title: "FORGET ID/PW?"},
            ],
            signInInfo : {
                username : "",
                password : "",
            },
        }
    },
    methods: {
        async signIn() {
            // const { signInInfo } = this;
            console.log(this.signInInfo.username + ", " + this.signInInfo.password)

            await this.$axios.post("/login", this.signInInfo)
                .then((res) => {
                    console.log(res.headers.access_token);
                    console.log(res.headers.refresh_token);
                    if(res.headers.state === "200") {
                        try {
                            this.$axios.defaults.headers.common["access_token"] = res.headers.access_token;
                            console.log("res.headers.access_token = "+res.headers.access_token);
                            let payload = {
                                "access_token":res.headers.access_token
                            }
                            this.$store.dispatch('setUserInfo', payload)
                            //이부분에서 user정보 요청.
                            // this.$axios.get("ysy/v1/user/userInfo")
                            //         .then(res =>{
                            //             console.log(res.data.userInfo);
                            //              this.$store.dispatch('setUserInfo', res.data.userInfo)
                            //         })
                            //         .catch(error=>{
                            //             console.log(" error = "+error);
                            //         });

                            //메뉴도 가져와야함.

                            this.$router.replace('/')
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
