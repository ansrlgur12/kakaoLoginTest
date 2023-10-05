package com.eanswer.dadaiksan.Controller;

import com.eanswer.dadaiksan.Dto.EventDto;
import com.eanswer.dadaiksan.Dto.ShopDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;

@Controller
@RequestMapping(value = "/thymeleaf")
public class ThymeleafController {

    @GetMapping
    public String thymeleafExample01(Model model) {
        model.addAttribute("data", "타임리프 예제 입니다.");
        return "thymeleafEx/thymeleafEx01";
    }

    @GetMapping(value = "/ex02")
    public String thymeleafExample02(Model model) {
        ShopDto shopDto = new ShopDto();
        shopDto.setShopName("테스트상점");
        shopDto.setShopDesc("상점 설명부분");
        shopDto.setCategory("카페");

        model.addAttribute("shopDto", shopDto);
        return "thymeleafEx/thymeleafEx02";
    }

}
