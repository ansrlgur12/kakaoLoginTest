package com.eanswer.dadaiksan.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "event")
public class Event {
    @Id
    @Column(name = "event_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // identity 수정
    private Long id;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date finDate;

    @Column(nullable = false)
    private String eventName;

    @Column(nullable = false)
    private String eventContents;

    @Column(nullable = false)
    private LocalDateTime regDate;

    private String eventImg;

    @OneToMany(mappedBy = "event")
    private List<Comment> comments;

    private LocalDateTime updateDate;
}




/// localhost:8111/admin/event-update
/// localhost:8111/admin/event-delete
/// comment-write

/// localhost:8111/admin/article-update