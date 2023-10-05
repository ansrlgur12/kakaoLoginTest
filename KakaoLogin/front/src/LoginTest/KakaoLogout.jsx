import React from "react";
import AxiosApi from "./KakaoAxios";
import LoginBtn from '../image/kakaoLogin.png'
import { KAKAO_AUTH_URL } from "../utils/KakaoLogin";

const KakaoLogout = () => {

    const accessToken = localStorage.getItem("kakaoAccessToken");
    const kakaoLogout = async() => {
        try {
            const response = await AxiosApi.kakaologout(accessToken);
            if (response) {
                localStorage.clear();
            }
        } catch (e) {
            console.log(e);
        }
    }
    kakaoLogout();
    return(
        <>
        <h1>로그아웃 완료</h1>
        <p>닉네임 : {localStorage.getItem("nickName")}</p>
        <a href={KAKAO_AUTH_URL}>
            <img src={LoginBtn} alt='kakaoLogin' />
        </a>
        </>
    );
}
export default KakaoLogout;