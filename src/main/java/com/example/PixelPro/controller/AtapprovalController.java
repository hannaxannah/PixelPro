package com.example.PixelPro.controller;

import com.example.PixelPro.service.AtapprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class AtapprovalController {
    private final AtapprovalService atapprovalService;
}
