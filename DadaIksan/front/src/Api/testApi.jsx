import axios from "axios"

const domain = "http://localhost:8111"

const TestApi = {

    // 테스트 로그인
    login: async(email, password) => {
        const data = {
            email: email,
            password : password
        };
        return await axios.post(domain + "/auth/login", data);
    },

    // 테스트 회원가입
    signup: async(email, password, nickName) => {
        const member = {
            email : email,
            password : password,
            nickname : nickName
        };
        return await axios.post(domain + "/auth/signup", member);
    },

    // 카카오
   

    
}

export default TestApi;