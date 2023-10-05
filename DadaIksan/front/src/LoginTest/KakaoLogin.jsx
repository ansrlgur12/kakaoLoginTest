import React from "react";
import LoginBtn from '../image/kakaoLogin.png'
import { KAKAO_AUTH_URL } from "../utils/KakaoLogin";

const Login = () => {

    return(
        <>
        <a href={KAKAO_AUTH_URL}>
            <img src={LoginBtn} alt='kakaoLogin' />
        </a>
        </>
    );
}

export default Login;