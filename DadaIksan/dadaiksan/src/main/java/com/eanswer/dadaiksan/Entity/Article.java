package com.eanswer.dadaiksan.Entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Article")
public class Article {
    @Id
    @Column(name = "Article_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "view_count", columnDefinition = "int default 0")
    private int viewCount = 0;

    @Column(nullable = false)
    private String articleType;

    @Column(nullable = false)
    private String title;

    private String contents;

    private String imgUrl;

    private String vidUrl;

    @Column(name = "status", columnDefinition = "boolean default true")
    private boolean status = true;

    private LocalDateTime regDate;

    private LocalDateTime updateDate;

    @OneToMany(mappedBy = "article")
    private List<Comment> comments;

    @OneToMany(mappedBy = "article")
    private List<Likes> like;

}
