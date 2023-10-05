package com.eanswer.dadaiksan.Dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QnaDto {
    private Long id;
    private String title;
    private String contents;
    private String imgUrl;
    private Date regDate;
    private String rcvMail;
}
