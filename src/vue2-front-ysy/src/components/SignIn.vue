<template>
    <v-main>
        <v-container 
            style="position: relative;"
            v-show="isLogin"
            fill-height fluid
        >
            <v-row align="center" justify="center" style="height:100vh" dense>
                <v-col cols="12" lg="6" md="6" class="grey lighten-2 fill-height d-flex flex-column justify-center align-center">
                    <v-card flat height="400px" width="500px">
                        <v-card-title class="text-h5">로그인</v-card-title>
                        <v-card-content>
                            <v-text-field label="ID" v-model="user_id" type="text" />
                            <v-text-field label="Password" v-model="user_pw" type="password" />
                        </v-card-content>
                        <v-card-actions>
                            <v-btn color="orange" @click="registBtn">회원가입</v-btn>
                            <v-btn color="orange" @click="loginSubmit">로그인</v-btn>
                        </v-card-actions>
                    </v-card>
                </v-col>
            </v-row>
        </v-container>
        <v-container 
            style="position: relative;"
            v-show="isRegister"
            fill-height fluid
        >
            <v-row align="center" justify="center" style="height:80vh" dense>
                <v-col cols="12" lg="6" md="6" class="grey lighten-2 fill-height d-flex flex-column justify-center align-center">
                    <v-card flat height="400px" width="500px">
                        <v-card-title class="text-h5">회원가입</v-card-title>
                        <v-card-content>
                            <v-text-field label="ID" v-model="user_id" type="text" />
                            <v-text-field label="Password" v-model="user_pw" type="password"/>
                            <v-text-field label="Name" v-model="user_name" type="text" />
                            <v-text-field label="Phone Number" v-model="user_phone" type="text"/>
                            <v-text-field label="E-mail" v-model="user_email" type="text" />
                        </v-card-content>
                        <v-card-actions>
                            <v-btn color="primary" @click="cancelBtn">취소</v-btn>
                            <v-btn color="primary" @click="registerSubmit">회원가입</v-btn>
                        </v-card-actions>
                    </v-card>
                </v-col>
            </v-row>
        </v-container>
    </v-main>
</template>

<script>
export default {
    data() {
        return {
            user_id: "",
            user_pw: "",
            user_name: "",
            user_phone: "",
            user_email: "",
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
