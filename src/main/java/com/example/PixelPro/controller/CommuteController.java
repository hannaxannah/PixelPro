package com.example.PixelPro.controller;

import com.example.PixelPro.Bean.CommuteBean;
import com.example.PixelPro.entity.Atapproval;
import com.example.PixelPro.entity.Commute;
import com.example.PixelPro.entity.Member;
import com.example.PixelPro.service.AttendanceService;
import com.example.PixelPro.service.CommuteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@RequiredArgsConstructor
@Controller
public class CommuteController {
    private final CommuteService commuteService;
    @GetMapping(value = "/commute/attendanceGotoWork")
    public String GotoWork(Model model, HttpSession session) {
        Member member = (Member) session.getAttribute("loginInfo");

        CommuteBean cb = new CommuteBean();
        cb.setMbnum(member.getMbnum());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        String formattedDate = sdf.format(currentDate);
        Timestamp timestamp = Timestamp.valueOf(formattedDate);
        cb.setGotowork(timestamp);

        Commute commute = Commute.createCommute(cb);
        commuteService.save(commute);

        return "/attendance/attendanceCheck";
    }
}
