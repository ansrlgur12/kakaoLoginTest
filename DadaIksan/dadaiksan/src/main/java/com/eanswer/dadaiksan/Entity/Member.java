package com.eanswer.dadaiksan.Entity;

import com.eanswer.dadaiksan.constant.Authority;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Member {

    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;  // unique 속성 부여 후, 이메일은 실장님 이메일로 관리자 권한 부여

    private String password;

    @Column(unique = true)
    private String nickName;

    @Column(name = "status", columnDefinition = "boolean default true")
    private boolean status = true;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Builder
    public Member(String nickname, String email, String password, Authority authority) {
        this.nickName = nickname;
        this.email = email;
        this.password = password;
        this.authority = authority;
    }
}
