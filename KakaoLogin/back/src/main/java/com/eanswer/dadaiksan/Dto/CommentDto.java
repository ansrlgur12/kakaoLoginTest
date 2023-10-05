package com.eanswer.dadaiksan.Dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
    private Long id;
    private String memberName;
    private String imgUrl;
    private String contents;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;
}
