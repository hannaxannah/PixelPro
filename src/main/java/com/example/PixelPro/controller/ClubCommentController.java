package com.example.PixelPro.controller;

import com.example.PixelPro.service.ClubCommentService;
import com.example.PixelPro.service.ClubService;
import com.example.PixelPro.service.FreeCommentService;
import com.example.PixelPro.service.FreeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ClubCommentController {

    private final ClubService clubService;
    private final ClubCommentService clubCommentService;
}
