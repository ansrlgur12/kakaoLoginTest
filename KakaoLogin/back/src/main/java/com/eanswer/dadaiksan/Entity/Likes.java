package com.eanswer.dadaiksan.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Likes")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Article")
    private Article article;
    
    @OneToOne
    @JoinColumn(name = "Member")
    private Member member;
}
