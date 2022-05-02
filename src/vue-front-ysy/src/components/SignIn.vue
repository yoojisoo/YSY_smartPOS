<template>
    <v-main>
        <v-container 
            style="position: relative; top: 20%;"
            v-show="isLogin"
        >
            <v-card class="mx-auto" max-width="400" height="350">
                <v-row style="margin-top: 60px">
                    <v-col cols="12">
                        <!-- <v-form> -->
                            <div class="mx-3">
                                <v-card-title class="text-h5">로그인</v-card-title>
                                <div class="mx-1">
                                    ID
                                    <v-text-field v-model="user_id" type="text" placeholder="ID" />
                                    Password
                                    <v-text-field v-model="user_pw" type="password" placeholder="Password" />
                                </div>
                                <v-divider class="mx-1"></v-divider>
                                <v-card-actions>
                                    <v-btn color="orange" @click="register">회원가입</v-btn>
                                    <v-btn color="orange" @click="loginSubmit">로그인</v-btn>
                                </v-card-actions>
                            </div>
                        <!-- </v-form> -->
                    </v-col>
                </v-row>
            </v-card>
        </v-container>
        <v-container 
            style="position: relative; top: 20%;"
            v-show="isRegister"
        >
            <v-card flat class="mx-auto" max-width="400" height="350">
                <v-row style="margin-top: 60px">
                    <v-col cols="12">
                        <v-form>
                            <div class="mx-3">
                                <v-card-title class="text-h5">회원가입</v-card-title>
                                <div class="mx-1">
                                    ID
                                    <v-text-field v-model="user_id" type="text" placeholder="ID" />
                                    Password
                                    <v-text-field v-model="user_pw" type="password" placeholder="Password" />
                                </div>
                                <v-card-actions>
                                    <v-btn color="primary" @click="login">취소</v-btn>
                                    <v-btn color="primary" @click="registerSubmit">회원가입</v-btn>
                                </v-card-actions>
                            </div>
                        </v-form>
                    </v-col>
                </v-row>
            </v-card>
        </v-container>
    </v-main>
</template>

<script>
export default {
    data() {
        return {
            user_id: "",
            user_pw: "",
            isLogin: true,
            isRegister: false,
            register_id: "",
            register_pwd: "",
            // register_role: "Admin",
        }
    },
    methods: {
        loginSubmit: function() {
            console.log('로그인');
            this.$router.push('/home');
        },
        login: function() {
            this.isLogin = true;
            this.isRegister = false;
        },
        register: function() {
            this.isLogin = false;
            this.isRegister = true;
        },
        registerSubmit: function() {
            console.log('회원가입 완료');
            this.login();
        },
        registBtn: function() {
            console.log('회원가입');
            this.register();
        },
        cancelBtn: function() {
            this.login();
        },
    }
}
</script>
