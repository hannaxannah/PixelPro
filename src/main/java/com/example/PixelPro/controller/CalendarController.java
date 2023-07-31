package com.example.PixelPro.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/calendar")
public class CalendarController {

    @GetMapping(value = "/main")
    public String mainPage(){

        return "calendar/calendarPage";
    }

}
