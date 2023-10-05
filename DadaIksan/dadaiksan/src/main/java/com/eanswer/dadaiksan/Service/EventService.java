package com.eanswer.dadaiksan.Service;

import com.eanswer.dadaiksan.Dto.EventDto;
import com.eanswer.dadaiksan.Entity.Event;
import com.eanswer.dadaiksan.Entity.Member;
import com.eanswer.dadaiksan.Repository.EventRepository;
import com.eanswer.dadaiksan.constant.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    @Autowired
    private EventRepository eventRepository;
    private EventDto eventDto;
    @Autowired
    private AuthService authService;

    public boolean newEvent(EventDto eventDto, HttpServletRequest request, UserDetails userDetails) throws ParseException {

        authService.validateTokenAndGetUser(request, userDetails);

        Event event = new Event();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
        Date startDate = dateFormat.parse(eventDto.getStartDate());
        Date finDate = dateFormat.parse(eventDto.getFinDate());

        event.setStartDate(startDate);
        event.setFinDate(finDate);
        event.setEventName(eventDto.getEventName());
        event.setEventContents(eventDto.getEventContents());
        event.setRegDate(LocalDateTime.now());
        event.setEventImg(eventDto.getEventImg());
        Event saveEvent = eventRepository.save(event);
        return saveEvent != null;
    }
    @Transactional
    public boolean updateEvent(Long id, EventDto eventDto, HttpServletRequest request, UserDetails userDetails) throws ParseException {

        authService.validateTokenAndGetUser(request, userDetails);

        Event event = eventRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 이벤트가 없습니다."));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
        Date startDate = dateFormat.parse(eventDto.getStartDate());
        Date finDate = dateFormat.parse(eventDto.getFinDate());

        event.setStartDate(startDate);
        event.setFinDate(finDate);
        event.setEventName(eventDto.getEventName());
        event.setEventContents(eventDto.getEventContents());
        event.setEventImg(eventDto.getEventImg());
        event.setUpdateDate(LocalDateTime.now());
        Event updateEvent = eventRepository.save(event);

        return updateEvent != null;
    }

    @Transactional
    public boolean deleteEvent(Long id, HttpServletRequest request, UserDetails userDetails) {

        authService.validateTokenAndGetUser(request, userDetails);

        Event event = eventRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 이벤트가 없습니다."));
        eventRepository.delete(event);

        return true;
    }

    public List<EventDto> getAllEvents() {

        List<EventDto> eventDtos = new ArrayList<>();
        List<Event> events = eventRepository.findAll();

        for (Event event : events) {
            EventDto eventDto = new EventDto();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");
            String startDateStr = dateFormat.format(event.getStartDate());
            String finDateStr = dateFormat.format(event.getFinDate());

            eventDto.setId(event.getId());
            eventDto.setEventName(event.getEventName());
            eventDto.setEventContents(event.getEventContents());
            eventDto.setStartDate(startDateStr);
            eventDto.setFinDate(finDateStr);
            eventDto.setRegDate(event.getRegDate());
            eventDto.setEventImg(event.getEventImg());
            eventDto.setUpdateTime(event.getUpdateDate());
            eventDtos.add(eventDto);
        }

        return eventDtos;
    }

    public EventDto readEvent(Long id) {

        Event event = eventRepository.findById(id).orElseThrow(()->new RuntimeException("이벤트가 없습니다."));

        EventDto eventDto = new EventDto();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");
        String startDateStr = dateFormat.format(event.getStartDate());
        String finDateStr = dateFormat.format(event.getFinDate());
        eventDto.setId(event.getId());
        eventDto.setEventName(event.getEventName());
        eventDto.setEventContents(event.getEventContents());
        eventDto.setStartDate(startDateStr);
        eventDto.setFinDate(finDateStr);
        eventDto.setRegDate(event.getRegDate());
        eventDto.setEventImg(event.getEventImg());
        eventDto.setUpdateTime(event.getUpdateDate());

        return eventDto;
    }
}
