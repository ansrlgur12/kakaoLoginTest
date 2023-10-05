import axios from "axios";
export const domain = "http://localhost:8111";

const AxiosApi = {

    memberLogin: async (email, password) => {
        const loginData = {
            email: email,
            password: password
        };
        return await axios.post(domain + "/auth/login", loginData);
    },

    memberReg : async(nickName, email, password) => {
        const member = {
            nickName : nickName,
            email : email,
            password : password
        };
        return await axios.post(domain + "/auth/signup", member);
    
    },

    kakaoAccessToken: async(code) => {
        const kakaoAccessTokencmd = {
            code: code
        }
        return await axios.post(domain + `/kakao/callback`, kakaoAccessTokencmd)
    },

    kakaologout: async(token) => {
        const kakaoAccessTokencmd = {
            token: token
        };
        return await axios.post(domain + `/kakao/logout`, kakaoAccessTokencmd);
    }

};
export default AxiosApi;