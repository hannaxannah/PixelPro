package com.example.PixelPro.controller;

import com.example.PixelPro.service.AppreturnService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class AppreturnController {
    private final AppreturnService returnService;
}
