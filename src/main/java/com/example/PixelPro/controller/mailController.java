package com.example.PixelPro.controller;

import com.example.PixelPro.service.mailService;
import com.example.PixelPro.mapper.mailMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class mailController {

    private final mailService mailservice;
    private final mailMapper mailmapper;

}
