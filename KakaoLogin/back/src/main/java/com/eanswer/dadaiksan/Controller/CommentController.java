package com.eanswer.dadaiksan.Controller;

import com.eanswer.dadaiksan.Dto.ArticleDto;
import com.eanswer.dadaiksan.Dto.CommentDto;
import com.eanswer.dadaiksan.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/write/{id}/{type}") // 등록
    public ResponseEntity<?> newComment(@PathVariable Long id, @PathVariable String type, @RequestBody CommentDto commentDto, HttpServletRequest request,
                                        @AuthenticationPrincipal UserDetails userDetails) throws ParseException {
        boolean isCreate = commentService.newComment(id, type, commentDto,request,userDetails);
        if(!isCreate){
            return new ResponseEntity<>("댓글 저장 실패", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("새로운 댓글 등록 성공",HttpStatus.OK);
    }

    @GetMapping("/read-all/{id}/{type}") // 조회
    public ResponseEntity readComment(@PathVariable Long id, @PathVariable String type){
        List<CommentDto> comments = commentService.getAllComments(id, type);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id, @RequestBody CommentDto commentDto, HttpServletRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            boolean isUpdated = commentService.updateComment(id, commentDto, request, userDetails);
            return new ResponseEntity<>(isUpdated,HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }catch (IllegalStateException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id, HttpServletRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        boolean isDeleted = commentService.deleteComment(id, request, userDetails);
        if(!isDeleted){
            return new ResponseEntity<>("해당 게시물이 없습니다.",HttpStatus.NO_CONTENT);
        }
        return  new ResponseEntity<>("해당 게시물이 삭제 되었습니다.",HttpStatus.OK);
    }
}
