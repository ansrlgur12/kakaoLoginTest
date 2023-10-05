package com.eanswer.dadaiksan.Controller;

import com.eanswer.dadaiksan.Service.LikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/likes")
public class LikesController {

  @Autowired
  private LikesService likesService;

  @PostMapping("/{id}")
  public ResponseEntity<?> LikesArticle(@PathVariable("id") Long id, HttpServletRequest request, @AuthenticationPrincipal UserDetails userDetails) {
    boolean isSuccess = likesService.likesArticle(id, request, userDetails);
    if(!isSuccess){
      return new ResponseEntity<>("좋아요 수정 실패", HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>("좋아요 수정 완료", HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> unLikesArticle(@PathVariable("id") Long id, HttpServletRequest request, @AuthenticationPrincipal UserDetails userDetails){
    likesService.unLikesArticle(id,request,userDetails);
    return new ResponseEntity<>("좋아요 수정 완료", HttpStatus.OK);
  }
}
