package com.example.PixelPro.controller;

import com.example.PixelPro.service.CommuteService;
import com.example.PixelPro.service.GapprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class GapprovalController {
    private final GapprovalService gapprovalService;
}
