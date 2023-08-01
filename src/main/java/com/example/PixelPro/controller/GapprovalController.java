package com.example.PixelPro.controller;

import com.example.PixelPro.Bean.GapprovalBean;
import com.example.PixelPro.service.CommuteService;
import com.example.PixelPro.service.GapprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class GapprovalController {
    private final GapprovalService gapprovalService;

    @GetMapping(value="/approval/gapprovalList")
    public String select(Model model) {
        return "/approval/gapprovalList";
    }

    @GetMapping(value = "/approval/gapprovalInsert")
    public String gapprovalInsert(Model model){
        model.addAttribute("gapprovalBean",new GapprovalBean());
        return "/approval/gapprovalInsert";
    }
}
