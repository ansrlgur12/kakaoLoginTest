package com.eanswer.dadaiksan.Service;

import com.eanswer.dadaiksan.Dto.LikesDto;
import com.eanswer.dadaiksan.Entity.Article;
import com.eanswer.dadaiksan.Entity.Likes;
import com.eanswer.dadaiksan.Entity.Member;
import com.eanswer.dadaiksan.Repository.ArticleRepository;
import com.eanswer.dadaiksan.Repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class LikesService {
  @Autowired
  private AuthService authService;
  @Autowired
  private LikesRepository likesRepository;
  @Autowired
  private ArticleRepository articleRepository;

  @Transactional
  public boolean likesArticle(Long id, HttpServletRequest request, UserDetails userDetails){
    Member member = authService.validateTokenAndGetUser(request,userDetails);
    Article article = articleRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글이 없습니다."));
    boolean likesExist = likesRepository.existsByMemberAndArticle(member,article);
    if(likesExist){
      return false;
    }

    Likes likes = new Likes();
    likes.setArticle(article);
    likes.setMember(member);
    likesRepository.save(likes);

    return true;
  }
  @Transactional
  public void unLikesArticle(Long id, HttpServletRequest request, UserDetails userDetails){
    Member member = authService.validateTokenAndGetUser(request,userDetails);
    Article article =  articleRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글이 없습니다."));
    likesRepository.deleteByMemberAndArticle(member,article);
  }

}
