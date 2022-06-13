import store from '@/store/index';
import jwt_decode from 'jwt-decode';
import router from "@/router/index"
import Axios from 'axios';



class AuthService{

    setLoginData(headers){
        // Axios.defaults.headers.common['access_token'] = headers.access_token;
        var decodedHeader_access  = jwt_decode(headers.access_token , { payload: true });
        var decodedHeader_refresh = jwt_decode(headers.refresh_token, { payload: true });
        if(headers.access_token === null || headers.access_token === ""){
            router.replace('/403');
            return;
        }
        let payload = {
            user_id: decodedHeader_access.username,
            user_name: decodedHeader_access.name,
            access_token: headers.access_token,
            access_token_exp: decodedHeader_access.exp,
            refresh_token: headers.refresh_token,
            refresh_token_exp: decodedHeader_refresh.exp,
        };
        store.dispatch('setUserInfo', payload);
        router.replace('/');
    }

}

export default new AuthService();