package com.example.PixelPro.controller;

import com.example.PixelPro.entity.Atapproval;
import com.example.PixelPro.entity.Attendance;
import com.example.PixelPro.entity.Member;
import com.example.PixelPro.service.AtapprovalService;
import com.example.PixelPro.service.AttendanceService;
import com.example.PixelPro.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        /*
        int offNum = 0;
        int useNum = 0;
        if(member.getMblevel().equals('8')){
            offNum = 15;
        }
        List<Attendance> myattendanceList = attendanceService.findByMbnum(member.getMbnum());

        String atcategory = null;
        int yeon = attendanceService.countByAtcategory('연차');
        for(Attendance a : myattendanceList){
           use
            useNum +=
        }
        model.addAttribute("offNum",offNum);
        model.addAttribute("useNum",useNum);*/
        String atcategory = "";
        BigDecimal count = BigDecimal.valueOf(0);

        List<Object[]> myattendanceList = attendanceService.countsByMbnumAndAtcategory(member.getMbnum());
        for (Object[] row : myattendanceList) {
            atcategory = (String) row[0];
            count = (BigDecimal) row[1];

            System.out.println("ATCATEGORY: " + atcategory + ", COUNT: " + count);
        }
        model.addAttribute("atcategory",atcategory);
        model.addAttribute("count",count);
        model.addAttribute("myattendanceList",myattendanceList);

        BigDecimal offNum = BigDecimal.valueOf(0);

        if(member.getMblevel().equals("대리")){
            offNum = BigDecimal.valueOf(15);
        }
        model.addAttribute("offNum",offNum);
        return "/attendance/attendanceCheck";
    }
    @GetMapping(value = "/attendance/attendanceGList")
    public String selectG(Model model, HttpSession session) {
        Member member = (Member) session.getAttribute("loginInfo");
        if(member == null){
            return "redirect:/login";
        }
        List<Member> teamList = memberService.findByMbnumTeam(member.getDept());
        for(Member m : teamList){
            System.out.println("팀 번호들 ########: "+m.getMbname());
        }
        List<Attendance> teamattendanceList = new ArrayList<>();
        List<Attendance> myattendanceList = new ArrayList<>();

        for(Member team : teamList){
            List<Attendance> teamAttendance = attendanceService.findByMbnum(team.getMbnum());
            teamattendanceList.addAll(teamAttendance);
        }
        myattendanceList = attendanceService.findByMbnum(member.getMbnum());

        model.addAttribute("teamattendanceList",teamattendanceList);
        model.addAttribute("myattendanceList",myattendanceList);
        return "/attendance/attendanceGList";
    }

    @GetMapping(value = "/attendance/attendanceIList")
    public String selectI(Model model, HttpSession session) {
        Member member = (Member) session.getAttribute("loginInfo");
        if(member == null){
            return "redirect:/login";
        }
        List<Member> teamList = memberService.findByMbnumTeam(member.getDept());
        for(Member m : teamList){
            System.out.println("팀 번호들 ########: "+m.getMbname());
        }
        List<Attendance> teamattendanceList = new ArrayList<>();
        List<Attendance> myattendanceList = new ArrayList<>();

        for(Member team : teamList){
            List<Attendance> teamAttendance = attendanceService.findByMbnum(team.getMbnum());
            teamattendanceList.addAll(teamAttendance);
        }
        myattendanceList = attendanceService.findByMbnum(member.getMbnum());

        List<Attendance> entireattendanceList = attendanceService.findAll();

        model.addAttribute("teamattendanceList",teamattendanceList);
        model.addAttribute("myattendanceList",myattendanceList);
        model.addAttribute("entireattendanceList",entireattendanceList);
        return "/attendance/attendanceIList";
    }
}
