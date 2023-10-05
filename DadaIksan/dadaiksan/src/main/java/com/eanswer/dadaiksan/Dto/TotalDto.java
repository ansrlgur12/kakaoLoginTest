package com.eanswer.dadaiksan.Dto;

import com.eanswer.dadaiksan.Entity.Member;
import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
@Builder
public class TotalDto {

    private Member member;
    private TokenDto tokenDto;

    public TotalDto(Member member, TokenDto tokenDto) {
        this.member = member;
        this.tokenDto = tokenDto;
    }

}
