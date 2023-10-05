package com.eanswer.dadaiksan.Controller;

import com.eanswer.dadaiksan.Dto.ArticleDto;
import com.eanswer.dadaiksan.Dto.EventDto;
import com.eanswer.dadaiksan.Dto.ShopDto;
import com.eanswer.dadaiksan.Service.ArticleService;
import com.eanswer.dadaiksan.Service.EventService;
import com.eanswer.dadaiksan.Service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private EventService eventService;

    @Autowired
    private ShopService shopService;

    /**
     * 이벤트
     */
    @PostMapping("/event-write")
    public ResponseEntity<?> newEvent(@RequestBody EventDto eventDto, HttpServletRequest request, @AuthenticationPrincipal UserDetails userDetails) throws ParseException {
        boolean isCreate = eventService.newEvent(eventDto, request, userDetails);

        if (! isCreate) {
            return new ResponseEntity<>("이벤트 등록 실패", HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>("이벤트 등록 성공", HttpStatus.OK);
    }

    @PostMapping("/event-update/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable("id") Long id, @RequestBody EventDto eventDto, HttpServletRequest request, @AuthenticationPrincipal UserDetails userDetails) throws ParseException {
        try{
            boolean isUpdated = eventService.updateEvent(id, eventDto, request, userDetails);
            return new ResponseEntity<>(isUpdated, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/event-delete/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable("id") Long id, HttpServletRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        boolean isDeleted = eventService.deleteEvent(id, request, userDetails);

        if (!isDeleted) {
            return new ResponseEntity<>("이벤트 삭제 실패", HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>("이벤트 삭제 성공", HttpStatus.OK);
    }

    /**
     * 게시글
     */

    @PostMapping("/article-write") // 등록
    public ResponseEntity<?> newArticle(@RequestBody ArticleDto articleDto, HttpServletRequest request,
                                        @AuthenticationPrincipal UserDetails userDetails) throws ParseException{
        boolean isCreate = articleService.newArticle(articleDto,request,userDetails);
        if(!isCreate){
            return new ResponseEntity<>("게시물 저장 실패",HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("새로운 게시판 등록 성공",HttpStatus.OK);
    }

    @PostMapping("/article-update/{id}")
    public ResponseEntity<?> updateArticle(@PathVariable("id") Long id, @RequestBody ArticleDto articleDto, HttpServletRequest request, @AuthenticationPrincipal UserDetails userDetails) throws ParseException{
        try {
            boolean isUpdated = articleService.updateArticle(id, articleDto, request, userDetails);
            return new ResponseEntity<>(isUpdated,HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }catch (IllegalStateException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/article-delete/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable("id") Long id, HttpServletRequest request, @AuthenticationPrincipal UserDetails userDetails) throws ParseException{
        boolean isDeleted = articleService.deleteArticle(id, request, userDetails);
        if(!isDeleted){
            return new ResponseEntity<>("해당 게시물이 없습니다.",HttpStatus.NO_CONTENT);
        }
        return  new ResponseEntity<>("해당 게시물이 삭제 되었습니다.",HttpStatus.OK);
    }

    /**
     * 매장
     */

    @PostMapping("/shop-create")
    public ResponseEntity<?> newShop(@RequestBody ShopDto shopDto, HttpServletRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        boolean isCreate = shopService.newShop(shopDto, request, userDetails);

        if (! isCreate) {
            return new ResponseEntity<>("매장 등록 실패", HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>("매장 등록 성공", HttpStatus.OK);
    }

    @PostMapping("/shop-update/{id}")
    public ResponseEntity<?> updateShop(@PathVariable("id") Long id, @RequestBody ShopDto shopDto, HttpServletRequest request, @AuthenticationPrincipal UserDetails userDetails) throws ParseException{
        try {
            boolean isUpdated = shopService.updateShop(id, shopDto, request, userDetails);
            return new ResponseEntity<>(isUpdated,HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }catch (IllegalStateException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/shop-delete/{id}")
    public ResponseEntity<?> deleteShop(@PathVariable("id") Long id, HttpServletRequest request, @AuthenticationPrincipal UserDetails userDetails) throws ParseException{
        boolean isDeleted = shopService.deleteShop(id, request, userDetails);
        if(!isDeleted){
            return new ResponseEntity<>("해당 매장이 없습니다.",HttpStatus.NO_CONTENT);
        }
        return  new ResponseEntity<>("해당 매장이 삭제 되었습니다.",HttpStatus.OK);
    }




}
