package com.example.PixelPro.service;

import com.example.PixelPro.mapper.CalendarMapper;
import com.example.PixelPro.repository.CalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CalendarService {

    @Autowired
    CalendarRepository calendarRepository;

    @Autowired
    private CalendarMapper calendarMapper;

    public List<Map<String, Object>> getCalendarAll() {
        System.out.println("서비스 "+calendarMapper.getCalendarAll());
        return calendarMapper.getCalendarAll();
    }
}
