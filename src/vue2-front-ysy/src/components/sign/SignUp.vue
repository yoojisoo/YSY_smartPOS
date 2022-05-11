<template>
    <v-app>
        <v-main>
            <v-container fill-height fluid>
                <v-layout align-center justify-center>
                    <v-flex xs12 sm8 md4>
                        <v-card class="elevation-12">
                            <v-toolbar dark color="primary">
                                <v-toolbar-title>Sign Up</v-toolbar-title>
                            </v-toolbar>
                            <v-card-text>
                                <v-text-field
                                    v-model="signUpInfo.username"
                                    name="user_id"
                                    label="userId"
                                    type="text"
                                    placeholder="userId"
                                    required
                                ></v-text-field>

                                <v-text-field
                                    v-model="signUpInfo.password"
                                    name="user_pw"
                                    label="password"
                                    type="password"
                                    placeholder="password"
                                    required
                                ></v-text-field>
                                
                                <v-text-field
                                    v-model="confirmPassword"
                                    name="confirmPassword"
                                    label="confirm password"
                                    type="password"
                                    placeholder="confirm password"
                                    required
                                ></v-text-field>

                                <v-text-field
                                    v-model="signUpInfo.name"
                                    name="user_name"
                                    label="user name"
                                    type="text"
                                    placeholder="user name"
                                    required
                                ></v-text-field>

                                <v-text-field
                                    v-model="signUpInfo.addr"
                                    name="user_addr"
                                    label="user addr"
                                    type="text"
                                    placeholder="user addr"
                                    required
                                ></v-text-field>

                                <v-text-field
                                    v-model="signUpInfo.email"
                                    name="user_email"
                                    label="user email"
                                    type="text"
                                    placeholder="user email"
                                    required
                                ></v-text-field>

                                <v-text-field
                                    v-model="signUpInfo.phone"
                                    name="user_phone"
                                    label="user phone"
                                    type="number"
                                    placeholder="user phone"
                                    required
                                ></v-text-field>

                                <div class="red--text">{{errorMessage}}</div>
                                <v-btn class="mt-4" color="primary" value="sign up" @click="signUp">sign up</v-btn>
                                <div class="grey--text mt-4" v-on:click="signIn">{{toggleMessage}}</div>
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
            toggleMessage: "Already have an Account? Sign In",
            signUpInfo : {
                username: "",
                password: "",
                name: "",
                addr: "",
                email: "",
                phone: "",
            },
            confirmPassword: "",
            errorMessage: "",
        }
    },
    methods: {
        signUp (){
            const { signUpInfo } = this;

            if(signUpInfo.password == this.confirmPassword) {
                this.errorMessage = "";
                this.$axios.post("/ysy/v1/auth/signUp", signUpInfo)
                    .then((res) => {
                        console.log(res.data);
                        if(res.data === "ok -> login page move!") {
                            alert("회원가입 성공");
                            this.$router.replace({ name: 'signIn' })
                        }
                        else{
                            alert(res.data);
                        }
                    });
            }
            else {
                this.errorMessage = "password did not match"
            }
        },
        signIn (){
            this.$router.replace({ name: 'signIn' })
        }
    },
}
</script>