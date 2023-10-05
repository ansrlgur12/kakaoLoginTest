package com.eanswer.dadaiksan.Controller;

import com.eanswer.dadaiksan.Dto.EventDto;
import com.eanswer.dadaiksan.Service.EventService;
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
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/read-all") // 이벤트 목록
    public ResponseEntity<List<EventDto>> getAllEvents() {
        List<EventDto> events = eventService.getAllEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/read-event/{id}") // 조회
    public ResponseEntity<EventDto> readEvent(@PathVariable Long id) {
        EventDto eventDto = eventService.readEvent(id);
        return new ResponseEntity<>(eventDto, HttpStatus.OK);
    }

}
