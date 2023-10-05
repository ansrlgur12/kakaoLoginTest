package com.eanswer.dadaiksan.Controller;

import com.eanswer.dadaiksan.Dto.ArticleDto;
import com.eanswer.dadaiksan.Dto.EventDto;
import com.eanswer.dadaiksan.Dto.ShopDto;
import com.eanswer.dadaiksan.Service.EventService;
import com.eanswer.dadaiksan.Service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @GetMapping("/read-all") // 목록 조회
    public ResponseEntity<List<ShopDto>> getAllShop() {
        List<ShopDto> shops = shopService.getAllShop();
        return new ResponseEntity<>(shops, HttpStatus.OK);
    }

    @GetMapping("/read-shop/{id}") // 조회
    public ResponseEntity<ShopDto> readEvent(@PathVariable Long id) {
        ShopDto shopDto = shopService.readShop(id);
        return new ResponseEntity<>(shopDto, HttpStatus.OK);
    }


}
