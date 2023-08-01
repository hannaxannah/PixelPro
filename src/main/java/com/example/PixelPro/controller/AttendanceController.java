package com.example.PixelPro.controller;

import com.example.PixelPro.service.AtapprovalService;
import com.example.PixelPro.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class AttendanceController {
    private final AttendanceService attendanceService;
}
