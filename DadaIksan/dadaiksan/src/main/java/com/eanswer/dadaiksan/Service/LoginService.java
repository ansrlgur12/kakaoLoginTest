package com.eanswer.dadaiksan.Service;

import com.eanswer.dadaiksan.Dto.MemberRequestDto;
import com.eanswer.dadaiksan.Dto.MemberResponseDto;
import com.eanswer.dadaiksan.Entity.Member;
import com.eanswer.dadaiksan.Repository.MemberRepository;
import com.eanswer.dadaiksan.constant.Authority;
import com.eanswer.dadaiksan.kakao.KakaoToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

@PropertySource("classpath:application.properties")
@Slf4j
@Component
public class LoginService {
//    private final LoginMapper loginMapper;
//
//    public LoginService(LoginMapper loginMapper) {
//        this.loginMapper = loginMapper;
//    }

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${kakao.password}")
    String password;

    //인증코드로 token요청하기
    public KakaoToken requestToken(String code) {

        String strUrl = "https://kauth.kakao.com/oauth/token"; //request를 보낼 주소
        KakaoToken kakaoToken = new KakaoToken(); //response를 받을 객체

        try {
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //url Http 연결 생성

            //POST 요청
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);//outputStreamm으로 post 데이터를 넘김

            //파라미터 세팅
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();

            sb.append("grant_type=authorization_code");

            //앱 REST API KEY
            sb.append("&client_id=539c5b89abd17f53932f14046ae0a45f");
            sb.append("&client_secret=30KHngEOxUqQcdzOB9mm3ARbSxW9M6gB");

            //redirect uri
            sb.append("&redirect_uri=http://localhost:3000/kakao/login");



            //인증코드
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();//실제 요청을 보내는 부분

            //실제 요청을 보내는 부분, 결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            log.info("responsecode(200이면성공): {}", responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            log.info("response body: {}", result);


            //Jackson으로 json 파싱할 것임
            ObjectMapper mapper = new ObjectMapper();
            //kakaoToken에 result를 KakaoToken.class 형식으로 변환하여 저장
            kakaoToken = mapper.readValue(result, KakaoToken.class);

            //api호출용 access token
            String access_Token = kakaoToken.getAccess_token();
            //access 토큰 만료되면 refresh token사용(유효기간 더 김)
            String refresh_Token = kakaoToken.getRefresh_token();


            log.info("access_token = {}", access_Token);
            log.info("refresh_token = {}", refresh_Token);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("카카오토큰생성완료>>>{}", kakaoToken);
        return kakaoToken;
    }

    public Member requestUser(String accessToken){
        log.info("requestUser 시작");
        String strUrl = "https://kapi.kakao.com/v2/user/me"; //request를 보낼 주소
        Member kakaoMember = new Member(); //response를 받을 객체
        MemberRequestDto kakaoRequestDto = new MemberRequestDto();

        try{
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //url Http 연결 생성

            //POST 요청
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);//outputStreamm으로 post 데이터를 넘김

            //전송할 header 작성, 인자로 받은 access_token전송
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);


            //실제 요청을 보내는 부분, 결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            log.info("requestUser의 responsecode(200이면성공): {}",responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            br.close();

            log.info("response body: {}",result);


            //Jackson으로 json 파싱할 것임
            ObjectMapper mapper = new ObjectMapper();
            //결과 json을 HashMap 형태로 변환하여 resultMap에 담음
            HashMap<String,Object> resultMap = mapper.readValue(result, HashMap.class);
            //json 파싱하여 id 가져오기
            Long id = Long.valueOf(String.valueOf(resultMap.get("id")));

            //결과json 안에 properties key는 json Object를 value로 가짐
            HashMap<String,Object> properties = (HashMap<String, Object>) resultMap.get("properties");
            String nickname = (String)properties.get("nickname");

            //결과json 안에 kakao_account key는 json Object를 value로 가짐
            HashMap<String,Object> kakao_account = (HashMap<String, Object>) resultMap.get("kakao_account");
            String email=null;//이메일은 동의해야 알 수 있음
            if(kakao_account.containsKey("email")){//동의하면 email이 kakao_account에 key로 존재함
                email=(String)kakao_account.get("email");
            }

            //유저정보 세팅
            kakaoRequestDto.setNickName(nickname);
            kakaoRequestDto.setEmail(email);
            kakaoRequestDto.setPassword(password);


            if (!memberRepository.existsByEmail(kakaoRequestDto.getEmail())) {
                kakaoMember = kakaoRequestDto.toMember(passwordEncoder);
                memberRepository.save(kakaoMember);
            }
            else {
                kakaoMember = memberRepository.findByEmail(email).orElseThrow();
            }

            log.info("resultMap= {}",resultMap);
            log.info("properties= {}",properties);
            System.out.println("여기가 끝");

        }catch (IOException e) {
            e.printStackTrace();
        }
        return kakaoMember;
    }

    public boolean logout(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        // headers.setBearerAuth(accessToken); // 액세스 토큰을 Authorization 헤더에 넣어 보냅니다.
        String kakaoLogoutUrl = "https://kapi.kakao.com/v1/user/logout";
        // RestTemplate을 사용하여 HTTP 요청을 보냅니다.
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<?> entity = new HttpEntity<>(headers);
        // 카카오 API 호출 결과를 받습니다.
        ResponseEntity<String> response = restTemplate.exchange(
                kakaoLogoutUrl,
                HttpMethod.POST,
                entity,
                String.class
        );
        System.out.println(response);
        if (response != null) {
            System.out.println("끝");
            return true;
        } else {
            System.out.println("끝");
            return false;
        }
    }
}