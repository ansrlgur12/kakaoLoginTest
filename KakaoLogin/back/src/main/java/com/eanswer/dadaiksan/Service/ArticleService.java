package com.eanswer.dadaiksan.Service;

import com.eanswer.dadaiksan.Dto.ArticleDto;
import com.eanswer.dadaiksan.Entity.Article;
import com.eanswer.dadaiksan.Entity.Member;
import com.eanswer.dadaiksan.Repository.ArticleRepository;
import com.eanswer.dadaiksan.Repository.LikesRepository;
import com.eanswer.dadaiksan.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
  @Autowired
  private ArticleRepository articleRepository;
  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private AuthService authService;
  @Autowired
  private LikesRepository likesRepository;


  public List<ArticleDto> getAllArticle() {
    List<ArticleDto> articleDtos = new ArrayList<>();
    List<Article> articles = articleRepository.findAll();

    for (Article article : articles) {
      ArticleDto articleDto = new ArticleDto();
      Long likeCount = likesRepository.countByArticle(article);

      articleDto.setId(article.getId());
      articleDto.setArticleType(article.getArticleType());
      articleDto.setContents(article.getContents());
      articleDto.setImgUrl(article.getImgUrl());
      articleDto.setRegDate(article.getRegDate());
      articleDto.setStatus(article.isStatus());
      articleDto.setTitle(article.getTitle());
      articleDto.setUpdateDate(article.getUpdateDate());
      articleDto.setVidUrl(article.getVidUrl());
      articleDto.setViewCount(article.getViewCount());
      articleDto.setLikeCounts(likeCount);

      articleDtos.add(articleDto);
    }
    return articleDtos;
  }

  public ArticleDto readArticle(Long id,HttpServletRequest request,UserDetails userDetails){
    ArticleDto articleDto1 = new ArticleDto();
    Member member = authService.validateTokenAndGetUser(request,userDetails);

    if(member != null){
      String memberNickName = articleRepository.findByArticleAndLikes(id,member.getId());
      articleDto1.setNickName(memberNickName);
    }

    Article article = articleRepository.findById(id).orElseThrow(() -> new RuntimeException("찾는 게시물이 없습니다."));
    Long likeCount = likesRepository.countByArticle(article);

    articleDto1.setId(article.getId());
    articleDto1.setViewCount(article.getViewCount());
    articleDto1.setTitle(article.getTitle());
    articleDto1.setArticleType(article.getArticleType());
    articleDto1.setContents(article.getContents());
    articleDto1.setImgUrl(article.getImgUrl());
    articleDto1.setVidUrl(article.getVidUrl());
    articleDto1.setStatus(article.isStatus());
    articleDto1.setRegDate(article.getRegDate());
    articleDto1.setUpdateDate(article.getUpdateDate());
    articleDto1.setLikeCounts(likeCount);

    return articleDto1;
  }

  public boolean newArticle(ArticleDto articleDto, HttpServletRequest request, UserDetails userDetails) throws ParseException {

    Member member = authService.validateTokenAndGetUser(request,userDetails);

    Article article = new Article();

    article.setTitle(articleDto.getTitle());
    article.setArticleType(articleDto.getArticleType());
    article.setContents(articleDto.getContents());
    article.setImgUrl(articleDto.getImgUrl());
    article.setVidUrl(articleDto.getVidUrl());
    article.setRegDate(LocalDateTime.now());
    Article saveArticle = articleRepository.save(article);
    System.out.println(saveArticle);
    return true;
  }

  @Transactional
  public boolean updateArticle(Long id, ArticleDto articleDto, HttpServletRequest request, UserDetails userDetails) throws ParseException{
    Member member = authService.validateTokenAndGetUser(request,userDetails);

    Article article = articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다."));

    article.setTitle(articleDto.getTitle());
    article.setArticleType(articleDto.getArticleType());
    article.setContents(articleDto.getContents());
    article.setImgUrl(articleDto.getImgUrl());
    article.setVidUrl(articleDto.getVidUrl());
    article.setUpdateDate(LocalDateTime.now());
    Article saveArticle = articleRepository.save(article);
    return saveArticle != null;
  }

  @Transactional
  public boolean deleteArticle(Long id, HttpServletRequest request, UserDetails userDetails){

    Member member = authService.validateTokenAndGetUser(request,userDetails);

    Article article = articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다."));
    articleRepository.delete(article);
    return true;
  }

  @Transactional
  public boolean viewCountInc(Long id, HttpServletRequest request, HttpServletResponse response){
    Cookie oldCookie = null;
    Cookie[] cookies = request.getCookies();
    int result = 0;

    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("postView")) {
          oldCookie = cookie;
        }
      }
    }

    if (oldCookie != null) {
      if (!oldCookie.getValue().contains("[" + id.toString() + "]")) {
        result = articleRepository.viewCountUp(id);
        oldCookie.setValue(oldCookie.getValue() + "_[" + id + "]");
        oldCookie.setPath("/");
        oldCookie.setMaxAge(60 * 60 * 24);
        response.addCookie(oldCookie);
      }
    } else {
      result = articleRepository.viewCountUp(id);
      Cookie newCookie = new Cookie("postView","[" + id + "]");
      newCookie.setPath("/");
      newCookie.setMaxAge(60 * 60 * 24);
      response.addCookie(newCookie);
    }

    return result > 0;
  }
}
