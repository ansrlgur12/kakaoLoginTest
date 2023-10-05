package com.eanswer.dadaiksan.Controller;

import com.eanswer.dadaiksan.Dto.MemberRequestDto;
import com.eanswer.dadaiksan.Dto.MemberResponseDto;
import com.eanswer.dadaiksan.Dto.TokenDto;
import com.eanswer.dadaiksan.Entity.Member;
import com.eanswer.dadaiksan.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private final AuthService authService;
//    private final EmailService emailService;

    /**
     * 회원가입
     */

    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(authService.signup(memberRequestDto));
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(authService.login(memberRequestDto));
    }

    @PostMapping("/refresh-token")
    public TokenDto refreshToken(@RequestParam String refreshToken) {
        TokenDto newAccessToken = authService.refreshToken(refreshToken);
        return newAccessToken;
    }

    /**
     * 이메일이 데이터베이스에 존재하는지 확인
     */
    @GetMapping("/{email}/check-email")
    public ResponseEntity<Boolean> findMemberByEmail(@PathVariable String email) throws Exception {
        boolean isValid = authService.isValidEmail(email);
//        있으면 true 없으면 false
        return ResponseEntity.ok(isValid);
    }
    @GetMapping("/{nickName}/check-nickname")
    public ResponseEntity<Boolean> findMemberByNickName(@PathVariable String nickName) throws Exception {
        boolean isValid = authService.isValidNickName(nickName);
//        있으면 true 없으면 false
        return ResponseEntity.ok(isValid);
    }


}