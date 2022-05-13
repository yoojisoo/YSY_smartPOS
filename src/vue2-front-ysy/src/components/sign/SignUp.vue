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
                                <!-- <v-stepper
                                    v-model="e6"
                                    vertical
                                >
                                    <v-stepper-step
                                        :complete="e6 > 1"
                                        step="1"
                                    >
                                    Select an app
                                    <small>Summarize if needed</small>
                                    </v-stepper-step>

                                    <v-stepper-content step="1">
                                        <v-card
                                            color="grey lighten-1"
                                            class="mb-12"
                                            height="200px"
                                        ></v-card>
                                        <v-btn
                                            color="primary"
                                            @click="e6 = 2"
                                        >
                                            Continue
                                        </v-btn>
                                        <v-btn text>
                                            Cancel
                                        </v-btn>
                                        </v-stepper-content>

                                        <v-stepper-step
                                            :complete="e6 > 2"
                                            step="2"
                                        >
                                        Configure analytics for this app
                                        </v-stepper-step>

                                        <v-stepper-content step="2">
                                        <v-card
                                            color="grey lighten-1"
                                            class="mb-12"
                                            height="200px"
                                        ></v-card>
                                        <v-btn
                                            color="primary"
                                            @click="e6 = 3"
                                        >
                                            Continue
                                        </v-btn>
                                        <v-btn text>
                                            Cancel
                                        </v-btn>
                                        </v-stepper-content>

                                        <v-stepper-step
                                            :complete="e6 > 3"
                                            step="3"
                                        >
                                        Select an ad format and name ad unit
                                        </v-stepper-step>

                                        <v-stepper-content step="3">
                                        <v-card
                                            color="grey lighten-1"
                                            class="mb-12"
                                            height="200px"
                                        ></v-card>
                                        <v-btn
                                            color="primary"
                                            @click="e6 = 4"
                                        >
                                            Continue
                                        </v-btn>
                                        <v-btn text>
                                            Cancel
                                        </v-btn>
                                        </v-stepper-content>

                                        <v-stepper-step step="4">
                                        View setup instructions
                                        </v-stepper-step>
                                        <v-stepper-content step="4">
                                        <v-card
                                            color="grey lighten-1"
                                            class="mb-12"
                                            height="200px"
                                        ></v-card>
                                        <v-btn
                                            color="primary"
                                            @click="e6 = 1"
                                        >
                                            Continue
                                        </v-btn>
                                        <v-btn text>
                                            Cancel
                                        </v-btn>
                                    </v-stepper-content>
                                </v-stepper> -->
                                <v-card class="elevation-0">
                                    <v-card-text>
                                        <v-row justify="space-between" no-gutters>
                                            <v-col cols="12" class="mb-6">
                                                <v-progress-linear v-model="valueDeterminate" color="deep-purple lighten-1"></v-progress-linear>
                                            </v-col>
                                            <v-col cols="12" class="mb-6">
                                                <h2>POS 설치를 위해 회원정보를 입력해주세요.</h2>
                                            </v-col>
                                            <v-col cols="12" class="mb-6">
                                                <v-text-field
                                                    v-model="signUpInfo.username"
                                                    name="user_id"
                                                    type="text"
                                                    placeholder="userId"
                                                    required
                                                    filled
                                                    hide-details
                                                ></v-text-field>
                                            </v-col>
                                            <v-col cols="12" class="mb-6">
                                                <v-text-field
                                                    v-model="signUpInfo.password"
                                                    name="user_pw"
                                                    type="password"
                                                    placeholder="password"
                                                    required
                                                    filled
                                                    hide-details
                                                ></v-text-field>
                                            </v-col>
                                            <v-col cols="12" class="mb-6">
                                                <v-text-field
                                                    v-model="confirmPassword"
                                                    name="confirmPassword"
                                                    type="password"
                                                    placeholder="confirm password"
                                                    required
                                                    filled
                                                    hide-details
                                                ></v-text-field>
                                            </v-col>
                                            <v-col cols="12" class="mb-6">
                                                <v-text-field
                                                    v-model="signUpInfo.name"
                                                    name="user_name"
                                                    type="text"
                                                    placeholder="user name"
                                                    required
                                                    filled
                                                    hide-details
                                                ></v-text-field>
                                            </v-col>
                                            <v-col cols="12" class="mb-6">
                                                <v-text-field
                                                    v-model="signUpInfo.addr"
                                                    name="user_addr"
                                                    type="text"
                                                    placeholder="user addr"
                                                    required
                                                    filled
                                                    hide-details
                                                ></v-text-field>
                                            </v-col>
                                            <v-col cols="12" class="mb-6">
                                                <v-text-field
                                                    v-model="signUpInfo.email"
                                                    name="user_email"
                                                    type="text"
                                                    placeholder="user email"
                                                    required
                                                    filled
                                                    hide-details
                                                ></v-text-field>
                                            </v-col>
                                            <v-col cols="12" class="mb-6">
                                                <v-text-field
                                                    v-model="signUpInfo.phone"
                                                    name="user_phone"
                                                    type="number"
                                                    placeholder="user phone"
                                                    required
                                                    filled
                                                    hide-details
                                                ></v-text-field>
                                            </v-col>

                                            <div class="red--text">{{errorMessage}}</div>

                                            <v-col cols="12">
                                                <v-btn block class="my-2 deep-purple lighten-1" dark>다음</v-btn>
                                            </v-col>
                                            <v-col cols="12">
                                                <div class="grey--text mt-4" v-on:click="signIn">{{toggleMessage}}</div>
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
            valueDeterminate: 50,
            e6: 1,
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