package com.eanswer.dadaiksan.Controller;

import com.eanswer.dadaiksan.Dto.QnaDto;
import com.eanswer.dadaiksan.Service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/qna")
public class QnaController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/email")
    @ResponseBody
    public Object findEmailOverlap(@RequestBody QnaDto qnaDto) throws Exception {

        String email = "ansrlgur12@eanswer.co.kr";
        Boolean isTrue = emailService.sendSimpleMessage(qnaDto, email);

        return new ResponseEntity<>(isTrue, HttpStatus.OK);
    }
}

