package com.example.PixelPro.controller;

import com.example.PixelPro.entity.Atapproval;
import com.example.PixelPro.entity.Member;
import com.example.PixelPro.service.AtapprovalService;
import com.example.PixelPro.service.AttendanceService;
import com.example.PixelPro.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class AttendanceController {
    private final AttendanceService attendanceService;
    private final MemberService memberService;
    @GetMapping(value = "/attendance/attendanceCheck")
    public String check(Model model, HttpSession session) {
        Member member = (Member) session.getAttribute("loginInfo");
        if(member == null){
            return "redirect:/login";
        }
        return "/attendance/attendanceCheck";
    }
    @GetMapping(value = "/attendance/attendanceGList")
    public String selectG(Model model, HttpSession session) {
        Member member = (Member) session.getAttribute("loginInfo");
        if(member == null){
            return "redirect:/login";
        }
        return "/attendance/attendanceGList";
    }

    @GetMapping(value = "/attendance/attendanceIList")
    public String selectI(Model model, HttpSession session) {
        Member member = (Member) session.getAttribute("loginInfo");
        if(member == null){
            return "redirect:/login";
        }
        return "/attendance/attendanceIList";
    }
}
