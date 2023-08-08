package com.example.PixelPro.service;

import com.example.PixelPro.entity.Calendar;
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
    private final CalendarRepository calendarRepository;

    @Autowired
    private CalendarMapper calendarMapper;

    public List<Map<String, Object>> getCalendarAll() {
        return calendarMapper.getCalendarAll();
    }

    public void saveCalendar(Calendar calendar) {
        calendarRepository.save(calendar);
    }
}
