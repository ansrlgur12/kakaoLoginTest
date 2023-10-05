package com.eanswer.dadaiksan.Repository;

import com.eanswer.dadaiksan.Entity.Article;
import com.eanswer.dadaiksan.Entity.Likes;
import com.eanswer.dadaiksan.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikesRepository extends JpaRepository<Likes,Long> {

  void deleteByMemberAndArticle(Member member, Article article);
  boolean existsByMemberAndArticle(Member member, Article article);

  Long countByArticle(Article article);
}
