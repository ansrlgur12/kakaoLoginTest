import React from "react";
import { useNavigate } from "react-router-dom";

const MainPage = () => {

    const nav = useNavigate();

    return(
        <>
        <h1>메인페이지</h1>
        <button onClick={()=>nav("/login")}>로그인 하러가기</button>
        </>
    );
}

export default MainPage;