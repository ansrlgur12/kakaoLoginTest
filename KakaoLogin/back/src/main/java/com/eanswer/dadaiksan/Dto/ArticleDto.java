package com.eanswer.dadaiksan.Dto;

import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Article")
public class ArticleDto {
    private Long id;
    private int viewCount = 0;
    private Long likeCounts;
    private String articleType;
    private String title;
    private String contents;
    private String imgUrl;
    private String vidUrl;
    private String nickName;
    private boolean status = true;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;
}
