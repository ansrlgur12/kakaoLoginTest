package com.eanswer.dadaiksan.Repository;

import com.eanswer.dadaiksan.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByNickName(String nickName);
    Member findById(Member member);
}
