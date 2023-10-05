package com.eanswer.dadaiksan.Dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LikesDto {
    private Long id;
    private Long memberId;
    private Long articleId;
}
