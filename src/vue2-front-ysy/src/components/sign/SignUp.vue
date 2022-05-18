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
                                <v-card class="elevation-0">
                                    <v-card-text>
                                        <v-form ref="form" @submit.prevent="signUp">
                                            <v-row justify="space-between" no-gutters>
                                                <v-col cols="12" class="mb-6">
                                                    <v-progress-linear v-model="valueDeterminate" color="deep-purple lighten-1"></v-progress-linear>
                                                </v-col>
                                                <v-col cols="12" class="mb-6">
                                                    <h2>POS 설치를 위해 회원정보를 입력해주세요.</h2>
                                                </v-col>
                                                <v-col cols="12" class="mb-6">
                                                    <v-text-field
                                                        v-model="signUpInfo.bizCd"
                                                        color="deep-purple lighten-1"
                                                        type="text"
                                                        placeholder="회사코드"
                                                        filled
                                                        required
                                                        :rules="rules.bizCd"
                                                    ></v-text-field>
                                                    <!-- required : HTML5에 새로 도입 된 속성.
                                                                : 양식을 제출하기 전에 입력 필드를 채워야 함을 지정한다. -->
                                                </v-col>
                                                <v-col cols="12" class="mb-6">
                                                    <v-text-field
                                                        v-model="signUpInfo.name"
                                                        color="deep-purple lighten-1"
                                                        type="text"
                                                        placeholder="이름"
                                                        filled
                                                        required
                                                        :rules="rules.name"
                                                    ></v-text-field>
                                                </v-col>
                                                <v-col cols="8" class="mb-4">
                                                    <v-text-field
                                                        v-model="signUpInfo.username"
                                                        color="deep-purple lighten-1"
                                                        name="user_email"
                                                        type="text"
                                                        placeholder="이메일"
                                                        filled
                                                        required
                                                        :rules="rules.email"
                                                    ></v-text-field>
                                                </v-col>
                                                <v-col cols="4" class="mb-2">
                                                    <v-btn
                                                        
                                                        color="deep-purple lighten-1"
                                                        outlined
                                                        small
                                                        block
                                                        dark
                                                    >
                                                        중복확인
                                                    </v-btn>
                                                </v-col>
                                                <!-- <v-col cols="12" class="mb-6">
                                                    <v-text-field
                                                        v-model="signUpInfo.username"
                                                        color="deep-purple lighten-1"
                                                        type="text"
                                                        placeholder="아이디"
                                                        filled
                                                        required
                                                        :rules="rules.id"
                                                    ></v-text-field>
                                                </v-col> -->
                                                <v-col cols="12" class="mb-6">
                                                    <v-text-field
                                                        v-model="signUpInfo.password"
                                                        color="deep-purple lighten-1"
                                                        :append-icon="passwordShow ? 'mdi-eye' : 'mdi-eye-off'"
                                                        :type="passwordShow ? 'text' : 'password'"
                                                        @click:append="passwordShow = !passwordShow"
                                                        placeholder="비밀번호"
                                                        filled
                                                        required
                                                        :rules="rules.password"
                                                    ></v-text-field>
                                                </v-col>
                                                <v-col cols="12" class="mb-6">
                                                    <v-text-field
                                                        v-model="confirmPassword"
                                                        color="deep-purple lighten-1"
                                                        :append-icon="confirmPasswordShow ? 'mdi-eye' : 'mdi-eye-off'"
                                                        :type="confirmPasswordShow ? 'text' : 'password'"
                                                        @click:append="confirmPasswordShow = !confirmPasswordShow"
                                                        placeholder="비밀번호 확인"
                                                        filled
                                                        required
                                                        :rules="rules.confirmPassword"
                                                    ></v-text-field>
                                                </v-col>
                                                <!-- <v-col cols="12" class="mb-6">
                                                    <v-text-field
                                                        v-model="signUpInfo.addr"
                                                        color="deep-purple lighten-1"
                                                        name="user_addr"
                                                        type="text"
                                                        placeholder="주소"
                                                        filled
                                                    ></v-text-field>
                                                </v-col>
                                                <v-col cols="12" class="mb-6">
                                                    <v-text-field
                                                        v-model="signUpInfo.phone"
                                                        color="deep-purple lighten-1"
                                                        name="user_phone"
                                                        type="number"
                                                        placeholder="휴대폰번호"
                                                        filled
                                                    ></v-text-field>
                                                </v-col> -->
                                                <v-col cols="12">
                                                    <v-btn
                                                        class="my-2"
                                                        color="deep-purple lighten-1"
                                                        block
                                                        dark
                                                        @click="signUp"
                                                        @keyup.enter="signUp"
                                                    >회원가입</v-btn>
                                                </v-col>
                                                <v-col cols="12">
                                                    <div class="grey--text mt-4" v-on:click="signIn">{{toggleMessage}}</div>
                                                </v-col>
                                            </v-row>
                                        </v-form>
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
import router from '@/router'
export default {
    name: "App",
    data() {
        return {
            passwordShow: false,
            confirmPasswordShow: false,
            toggleMessage: "Already have an Account? Sign In",
            signUpInfo : {
                bizCd: "",
                username: "",
                password: "",
                name: "",
                // addr: "",
                email: "",
                // phone: "",
            },
            confirmPassword: "",
            valueDeterminate: 50,
            rules: {
                bizCd: [
                    v => !!v || '회사코드는 필수 입력사항입니다.',
                ],
                id: [
                    v => !!v || '아이디는 필수 입력사항입니다.',
                ],
                password: [
                    v => !!v || '비밀번호는 필수 입력사항입니다.',
                ],
                confirmPassword: [
                    v => !!v || '비밀번호는 필수 입력사항입니다.',
                    v => v === this.signUpInfo.password || '비밀번호가 일치하지 않습니다.',
                ],
                name: [
                    v => !!v || '이름은 필수 입력사항입니다.',
                    v => !/[~!@#$%^&*()_+|<>?:{}]/.test(v) || '이름에는 특수문자를 사용할 수 없습니다.',
                ],
                email: [
                    v => !!v || '이메일은 필수 입력사항입니다.',
                    v => {
                        const replaceV = v.replace(/(\s*)/g, '')
                        const pattern = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/
                        return pattern.test(replaceV) || '이메일 형식으로 입력해주세요'
                    }
                ]
            },
        }
    },
    methods: {
        signUp (){
            const { signUpInfo } = this;
            const validate = this.$refs.form.validate()

            if(this.signUpInfo.password === this.confirmPassword) {
                if(validate) {
                    this.$axios.post("/ysy/v1/auth/signUp", signUpInfo)
                        .then((res) => {
                            console.log(res.data);
                            if(res.data === "ok") {
                                alert("회원가입 성공");
                                this.$router.replace({ name: 'signIn' })
                            }
                            else{
                                alert(res.data);
                            }
                        });
                } else alert("양식에 맞게 작성해주세요.")
            } else alert("비밀번호가 일치하지 않습니다.")
        },
        signIn (){
            this.$router.replace({ name: 'signIn' })
        }
    },
}
</script>