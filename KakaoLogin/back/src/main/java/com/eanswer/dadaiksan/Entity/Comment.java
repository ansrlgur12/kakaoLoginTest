package com.eanswer.dadaiksan.Entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "Comment")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imgUrl;

    private String contents;

    private LocalDateTime regDate;

    private LocalDateTime updateDate;

    @ManyToOne
    @JoinColumn(name = "Article")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "Member")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "Event")
    private Event event;
}
