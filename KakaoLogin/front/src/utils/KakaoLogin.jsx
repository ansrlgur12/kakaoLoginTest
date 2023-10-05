const kakaoClientId = "539c5b89abd17f53932f14046ae0a45f";
const kakaoRedirectURL = "http://localhost:3000/kakao/login";

export const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${kakaoClientId}&redirect_uri=${kakaoRedirectURL}&response_type=code&prompt=login`;