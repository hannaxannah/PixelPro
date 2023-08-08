package com.example.PixelPro.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CalendarMapper {


    public List<Map<String, Object>> getCalendarAll();
}
