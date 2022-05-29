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
                                        <v-row>
                                            
                                            <a href="https://kauth.kakao.com/oauth/authorize?client_id=4c9e081b17404f289741f6792bd4c6e7&redirect_uri=http://localhost:8000/ysy/v1/auth/kakao/redirect&response_type=code">
                                                <v-img src="@/assets/img/png/kakao_btn.png" ></v-img>
                                            </a>
                                            
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
import jwt_decode from "jwt-decode"

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

                    var decodedHeader_access = jwt_decode(res.headers.access_token, { payload: true })
                    console.log('decodedHeader_access > exp : ' + decodedHeader_access.exp)
                    console.log('decodedHeader_access > username : ' + decodedHeader_access.username)
                    var decodedHeader_refresh = jwt_decode(res.headers.refresh_token, { payload: true })
                    console.log('decodedHeader_refresh > exp : ' + decodedHeader_refresh.exp)
                    console.log('decodedHeader_refresh > username : ' + decodedHeader_refresh.username)

                    if(res.headers.state === "200") {
                        try {
                            this.$axios.defaults.headers.common["access_token"] = res.headers.access_token;

                            let payload = {
                                "user_id": decodedHeader_access.username,
                                "access_token": res.headers.access_token,
                                "access_token_exp": decodedHeader_access.exp,
                                "refresh_token": res.headers.refresh_token,
                                "refresh_token_exp": decodedHeader_refresh.exp,
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
        kakaoLogin(){
            console.log("kakao login click");
        }
    },
}
</script>
