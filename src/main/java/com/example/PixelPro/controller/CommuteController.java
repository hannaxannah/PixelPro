package com.example.PixelPro.controller;

import com.example.PixelPro.service.AttendanceService;
import com.example.PixelPro.service.CommuteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class CommuteController {
    private final CommuteService commuteService;
}
