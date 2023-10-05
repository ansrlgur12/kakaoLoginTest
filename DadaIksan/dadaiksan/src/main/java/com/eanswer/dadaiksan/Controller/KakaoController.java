package com.eanswer.dadaiksan.Controller;

import com.eanswer.dadaiksan.Dto.MemberRequestDto;
import com.eanswer.dadaiksan.Dto.MemberTokenResponse;
import com.eanswer.dadaiksan.Dto.TokenDto;
import com.eanswer.dadaiksan.Dto.TotalDto;
import com.eanswer.dadaiksan.Entity.Member;
import com.eanswer.dadaiksan.Service.AuthService;
import com.eanswer.dadaiksan.Service.LoginService;
import com.eanswer.dadaiksan.kakao.KakaoToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@PropertySource("classpath:application.properties")
@RestController
@RequiredArgsConstructor
@Transactional
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/kakao")
public class KakaoController {

    @Value("${kakao.password}")
    String password;

    @Autowired
    private LoginService loginService;

    @Autowired
    private AuthService authService;
    @PostMapping("/callback")
    public ResponseEntity<?> kakaoCallback(@RequestBody Map<String, String> tokenData) {
        String code = tokenData.get("code");

        // 카카오 토큰 요청
        KakaoToken kakaoToken = loginService.requestToken(code);
        log.info("kakaoToken = {}", kakaoToken);

        // 유저정보 요청
        Member member = loginService.requestUser(kakaoToken.getAccess_token());
        log.info("member = {}",member);

        MemberRequestDto memberRequestDto = new MemberRequestDto();
        memberRequestDto.setEmail(member.getEmail());
        memberRequestDto.setPassword(password);
        memberRequestDto.setNickName(member.getNickName());

        TokenDto tokenDto = authService.login(memberRequestDto);
        return ResponseEntity.ok().body(new MemberTokenResponse(member, tokenDto, kakaoToken));

    }

    @PostMapping("/logout")
    public Boolean kakaologout(@RequestBody Map<String, String> accesstoken) {
        System.out.println("로그아웃 컨트롤러 진입");
        String token = accesstoken.get("token");
        System.out.println(token);
        return loginService.logout(token);
    }
}
