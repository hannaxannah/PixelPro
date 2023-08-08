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

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class CommuteController {
    private final CommuteService commuteService;
    @GetMapping(value = "/commute/attendanceGotoWork")
    public String gotoWork(Model model, HttpSession session, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        Member member = (Member) session.getAttribute("loginInfo");

        List<Commute> existDateList = commuteService.findByMbnum(member.getMbnum());

        if(existDateList.isEmpty()){
            //출근 등록 처리
            CommuteBean cb = new CommuteBean();
            cb.setMbnum(member.getMbnum());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currentDate = new Date();
            String formattedDate = sdf.format(currentDate);
            Timestamp timestamp = Timestamp.valueOf(formattedDate);
            cb.setGotowork(timestamp);

            Commute commute = Commute.createCommute(cb);
            commuteService.save(commute);

            String alertMessage = "출근 등록 완료되었습니다.";
            String script = "<script>alert('" + alertMessage + "');</script>";
            response.getWriter().write(script);
            return "/attendance/attendanceCheck";
        }
        for (Commute existDate : existDateList) {
            System.out.println("오늘날자 레코드@@@@@" + existDate.getGotowork());

            if (existDate != null && existDate.getGotowork() != null) {
                LocalDate today = LocalDate.now();
                LocalDate gotoworkDate = existDate.getGotowork().toLocalDateTime().toLocalDate();

                if (today.equals(gotoworkDate)) {
                    String alertMessage = "이미 출근을 등록하셨습니다.";
                    String script = "<script>alert('" + alertMessage + "');</script>";
                    response.getWriter().write(script);
                    return "/attendance/attendanceCheck";
                }
            }

            //출근 등록 처리
            CommuteBean cb = new CommuteBean();
            cb.setMbnum(member.getMbnum());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currentDate = new Date();
            String formattedDate = sdf.format(currentDate);
            Timestamp timestamp = Timestamp.valueOf(formattedDate);
            cb.setGotowork(timestamp);

            Commute commute = Commute.createCommute(cb);
            commuteService.save(commute);

            String alertMessage = "출근 등록 완료되었습니다.";
            String script = "<script>alert('" + alertMessage + "');</script>";
            response.getWriter().write(script);
            return "/attendance/attendanceCheck";
        }


        return "/attendance/attendanceCheck";
    }
    @GetMapping(value = "/commute/attendanceGetOffWork")
    public String getOffWork(Model model, HttpSession session, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        Member member = (Member) session.getAttribute("loginInfo");

        List<Commute> existDateList = commuteService.findByMbnum(member.getMbnum());

        for (Commute existDate : existDateList) {
            if (existDate != null && existDate.getGotowork() != null) {
                // 오늘 날짜와 출근 날짜 비교
                LocalDate today = LocalDate.now();
                LocalDate gotoworkDate = existDate.getGotowork().toLocalDateTime().toLocalDate();

                if (today.equals(gotoworkDate)) {
                    // 출근 등록된 상태
                    if (existDate.getGetoff() == null) {
                        // 아직 퇴근 등록되지 않은 경우
                        CommuteBean cb = new CommuteBean();
                        cb.setMbnum(member.getMbnum());

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date currentDate = new Date();
                        String formattedDate = sdf.format(currentDate);
                        Timestamp timestamp = Timestamp.valueOf(formattedDate);
                        cb.setCmnum(existDate.getCmnum());
                        cb.setGetoff(timestamp);
                        cb.setGotowork(existDate.getGotowork());

                        Commute commute = Commute.createCommute(cb);
                        commuteService.save(commute);

                        String alertMessage = "퇴근 등록 완료되었습니다.";
                        String script = "<script>alert('" + alertMessage + "');</script>";
                        response.getWriter().write(script);
                    } else {
                        // 이미 퇴근 등록한 경우
                        String alertMessage = "이미 퇴근을 등록하셨습니다.";
                        String script = "<script>alert('" + alertMessage + "');</script>";
                        response.getWriter().write(script);
                    }
                } else {
                    // 출근 날짜가 오늘과 다른 경우
                    String alertMessage = "출근을 먼저 등록해 주세요.";
                    String script = "<script>alert('" + alertMessage + "');</script>";
                    response.getWriter().write(script);
                }
            } else {
                // 출근 등록되지 않은 경우
                String alertMessage = "출근을 먼저 등록해 주세요.";
                String script = "<script>alert('" + alertMessage + "');</script>";
                response.getWriter().write(script);
            }
        }

        return "/attendance/attendanceCheck";
    }
}
