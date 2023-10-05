package com.eanswer.dadaiksan.Service;

import com.eanswer.dadaiksan.Dto.QnaDto;
import com.eanswer.dadaiksan.Entity.Qna;
import com.eanswer.dadaiksan.Repository.MemberRepository;
import com.eanswer.dadaiksan.Repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
@PropertySource("classpath:application.properties")
@Slf4j
@RequiredArgsConstructor
@Service
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    private final QnaRepository qnaRepository;
    private QnaDto qnaDto;

    @Value("${spring.mail.nickName}")
    private String id;
    public Boolean sendSimpleMessage(QnaDto qnaDto, String email) throws Exception {
        MimeMessage message = createMessage(qnaDto, email);
        try{
            javaMailSender.send(message); // 메일 발송
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        Qna qna = new Qna();
        qna.setTitle(qnaDto.getTitle());
        qna.setContents(qnaDto.getContents());
        qna.setImgUrl(qnaDto.getImgUrl());
        qna.setRegDate(LocalDateTime.now());
        qnaRepository.save(qna);

        return true;
    }

    public MimeMessage createMessage(QnaDto qnaDto, String email) throws MessagingException, UnsupportedEncodingException {
        log.info("보내는 대상 : "+ email);
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, email); // to 보내는 대상
        message.setSubject("[다다영등] 문의요청"); //메일 제목

        String title = qnaDto.getTitle();
        String content = qnaDto.getContents();
        String rcvMail = qnaDto.getRcvMail();

        String msg="";
        msg += "<h3 style=\"font-size: 20px; padding-right: 30px; padding-left: 30px;\">다다영등 문의</h3>";
        msg += "<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\"> 제목 : ";
        msg += title;
        msg += "</p>";
        msg += "<div style=\"padding-right: 30px; padding-left: 30px; margin: 32px 0 40px;\"><table style=\"border-collapse: collapse; border: 0; height: auto; table-layout: fixed; word-wrap: break-word; border-radius: 6px;\"><tbody><tr><td style=\" font-size: 15px;\">";
        msg += content;
        msg += "</td></tr></tbody></table></div>";
        msg += "<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\"> 회신메일 주소 : ";
        msg += rcvMail;
        msg += "</p>";

        message.setText(msg, "utf-8", "html"); //내용, charset타입, subtype
        message.setFrom(new InternetAddress(id, rcvMail)); //보내는 사람의 메일 주소, 보내는 사람 이름

        return message;
    }
}
