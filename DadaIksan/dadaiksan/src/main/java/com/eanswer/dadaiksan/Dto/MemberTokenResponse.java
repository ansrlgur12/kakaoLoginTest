package com.eanswer.dadaiksan.Dto;

import com.eanswer.dadaiksan.Entity.Member;
import com.eanswer.dadaiksan.kakao.KakaoToken;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
@Getter
@Setter

public class MemberTokenResponse {
    private Member member;
    private TokenDto tokenDto;
    private KakaoToken kakaoToken;

    public MemberTokenResponse(Member member, TokenDto tokenDto, KakaoToken kakaoToken) {
        this.member = member;
        this.tokenDto = tokenDto;
        this.kakaoToken = kakaoToken;
    }

    // getter와 setter 메서드
}