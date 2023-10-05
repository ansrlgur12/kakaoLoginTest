import React, { useEffect, useState } from "react";
import AxiosApi from "./KakaoAxios";
import Functions from "../utils/Functions";
import { useLocation} from "react-router-dom";


const LoginFinish = () => {


    // const navigate = useNavigate();
    const location = useLocation();
    const code = new URLSearchParams(location.search).get('code');
    if (code) {
        const getAccessToken = async () => {
        try {
            console.log(code);
            const response = await AxiosApi.kakaoAccessToken(code);
            console.log(response.data);
            if(response.status === 200){
              const accessToken = response.data.tokenDto.accessToken;
              const refreshToken = response.data.tokenDto.refreshToken;    

              Functions.setAccessToken(accessToken);
              Functions.setRefreshToken(refreshToken);

              localStorage.setItem("userId", response.data.member.email);             
              localStorage.setItem("nickName", response.data.member.nickName);
              localStorage.setItem("kakaoAccessToken", response.data.kakaoToken.access_token);

              console.log(localStorage.getItem("kakaoAccessToken"));
              
            
            //   navigate("/");
          }
        } catch (error) {
            console.error('액세스 토큰 요청 실패:', error);
        }
        }
        getAccessToken();
    };

    const LogOut = () => {
        const clientId = "539c5b89abd17f53932f14046ae0a45f";
        const redirectUri = "http://localhost:3000/kakao/logout";
        const authorizeUrl = `https://kauth.kakao.com/oauth/logout?client_id=${clientId}&logout_redirect_uri=${redirectUri}`;
        window.location.href = authorizeUrl;
    }
    return (
        <>
        <h1>로그인 끝</h1>
        <p>닉네임 : {localStorage.getItem("nickName")}</p>

        <button onClick={LogOut}>로그아웃</button>
        </>
    )
}
export default LoginFinish;