package com.example.PixelPro.controller;

import com.example.PixelPro.service.ReturnService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ReturnController {
    private final ReturnService returnService;
}
