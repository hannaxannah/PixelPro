package com.example.PixelPro.controller;

import com.example.PixelPro.Bean.AtapprovalBean;
import com.example.PixelPro.Bean.AttendanceBean;
import com.example.PixelPro.entity.Atapproval;
import com.example.PixelPro.entity.Attendance;
import com.example.PixelPro.entity.Member;
import com.example.PixelPro.service.AtapprovalService;
import com.example.PixelPro.service.AttendanceService;
import com.example.PixelPro.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class AtapprovalController {
    private final AtapprovalService atapprovalService;
    private final AttendanceService attendanceService;
    private final MemberService memberService;

    @GetMapping(value = "/approval/atapprovalList")
    public String select(Model model,HttpSession session, HttpServletResponse response) throws IOException{
        response.setContentType("text/html; charset=UTF-8");
        Member member = (Member) session.getAttribute("loginInfo");
        if(member == null){
            session.setAttribute("destination", "redirect:/approval/atapprovalList");
            response.getWriter().print("<script>alert('로그인이 필요합니다.');location.href='/login'</script>");
            response.getWriter().flush();
        }
        List<Atapproval> atapprovalList = atapprovalService.findByAtwmbnumOrderByAtnumDesc(member.getMbnum());
        model.addAttribute("atapprovalList",atapprovalList);
        return "/approval/atapprovalList";
    }
    @GetMapping(value = "/approval/atapprovalToMeList")
    public String selectToMe(Model model,HttpSession session, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        Member member = (Member) session.getAttribute("loginInfo");
        if(member == null){
            session.setAttribute("destination", "redirect:/approval/atapprovalToMeList");
            response.getWriter().print("<script>alert('로그인이 필요합니다.');location.href='/login'</script>");
            response.getWriter().flush();
        }
        List<Atapproval> atapprovalList = atapprovalService.findByAthmbnumOrderByAtnumDesc(member.getMbnum());
        for(Atapproval a : atapprovalList){
            Member mb = memberService.findByMbnum(a.getAtwmbnum());
            if (mb != null) {
                a.setAtstatus(mb.getMbname());
            }
        }
        model.addAttribute("atapprovalList",atapprovalList);
        return "/approval/atapprovalToMeList";
    }
    @GetMapping(value = "/approval/atapprovalInsert")
    public String atapprovalInsert(Model model, HttpSession session, HttpServletResponse response) throws IOException{
        response.setContentType("text/html; charset=UTF-8");
        Member member = (Member) session.getAttribute("loginInfo");
        if(member == null){
            session.setAttribute("destination", "redirect:/approval/atapprovalInsert");
            response.getWriter().print("<script>alert('로그인이 필요합니다.');location.href='/login'</script>");
            response.getWriter().flush();
        }

        List<Member> memberList = memberService.findByOrderByDeptAscMblevelAsc();
        model.addAttribute("atapprovalBean", new AtapprovalBean());
        model.addAttribute("memberList", memberList);
        model.addAttribute("loginInfo",member);
        return "/approval/atapprovalInsert";
    }

    @PostMapping(value = "/approval/atapprovalInsert")
    public String atapprovalInsert(@Valid AtapprovalBean atapprovalBean, BindingResult result, Model model, HttpSession session, @RequestParam("atcontent2") String atcontent2, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        Member member = (Member) session.getAttribute("loginInfo");
        List<Member> memberList = memberService.findByOrderByDeptAscMblevelAsc();
        model.addAttribute("memberList", memberList);

        if(member == null){
            session.setAttribute("destination", "redirect:/approval/atapprovalInsert");
            response.getWriter().print("<script>alert('로그인이 필요합니다.');location.href='/login'</script>");
            response.getWriter().flush();
        }
        else {
            System.out.println("atcontent\n" + atapprovalBean.getAtcontent());
            model.addAttribute("loginInfo",member);

            if (result.hasErrors()) {
                System.out.println("에러 발생");
                model.addAttribute("atapprovalBean", atapprovalBean);
                return "/approval/atapprovalInsert";
            } else {
//                if (atapprovalBean.getAtsign2() != null) {
//                    Member member1 = memberService.findByMbnum(atapprovalBean.getAtsign1());
//                    Member member2 = memberService.findByMbnum(atapprovalBean.getAtsign2());
//
//                    if (Integer.parseInt(member1.getMbaccess()) < Integer.parseInt(member2.getMbaccess())) {
//                        model.addAttribute("atapprovalBean", atapprovalBean);
//                        model.addAttribute("memberList", memberList);
//                        model.addAttribute("comparelevel", "2차 승인자가 1차 승인자보다 직급이 높아야 합니다.");
//                        return "/approval/atapprovalInsert";
//                    }
//                }
                atapprovalBean.setAtwmbnum(member.getMbnum());
                atapprovalBean.setAtstatus("1차 승인 요청");
                atapprovalBean.setAthmbnum(atapprovalBean.getAtsign1());

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar cal = Calendar.getInstance();
                System.out.println("sdf.format(cal.getTime()) : " + sdf.format(cal.getTime()));
                atapprovalBean.setAtdate(sdf.format(cal.getTime()));

                if(atapprovalBean.getAtcontent().equals("기타")){
                    atapprovalBean.setAtcontent("기타 - " + atcontent2);
                }
                Atapproval atapproval = Atapproval.createAtapproval(atapprovalBean);
                atapprovalService.save(atapproval);
                return "redirect:/approval/atapprovalList";

            }
        }
        return "/approval/atapprovalInsert";
    }

    @RequestMapping(value = "/approval/atapprovalSign")
    public String gapprovalSign(@RequestParam(value = "atnum") Integer atnum, HttpSession session, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");

        Atapproval atapproval = atapprovalService.findByAtnum(atnum);
        AtapprovalBean atapprovalBean = AtapprovalBean.createAtapprovalBean(atapproval);
        Member member = (Member) session.getAttribute("loginInfo");
        if(member == null){
            session.setAttribute("destination", "redirect:/approval/atapprovalToMeList");
            response.getWriter().print("<script>alert('로그인이 필요합니다.');location.href='/login'</script>");
            response.getWriter().flush();
        }
        else{
            if(member.getMbnum() == atapproval.getAtsign1()){
                if(atapproval.getAtsign2() != null){
                    atapprovalBean.setAtstatus("2차 승인 요청");
                    atapprovalBean.setAthmbnum(atapproval.getAtsign2());
                }
                else{
                    atapprovalBean.setAtstatus("승인 완료");
                    atapprovalBean.setAthmbnum(-1);

                    AttendanceBean attendanceBean = new AttendanceBean(member.getMbnum(), atapproval.getAtcategory(), atapproval.getReqdate().toString());
                    Attendance attendance = Attendance.createAttendance(attendanceBean);
                    attendanceService.save(attendance);
                }
            } else if (member.getMbnum() == atapproval.getAtsign2()) {
                atapprovalBean.setAtstatus("승인 완료");
                atapprovalBean.setAthmbnum(-1);

                AttendanceBean attendanceBean = new AttendanceBean(member.getMbnum(), atapproval.getAtcategory(), atapproval.getReqdate().toString());
                Attendance attendance = Attendance.createAttendance(attendanceBean);
                attendanceService.save(attendance);
            }

            Atapproval atapprovalSave = Atapproval.createAtapproval(atapprovalBean);
            atapprovalService.save(atapprovalSave);

        }
        return "redirect:/approval/atapprovalToMeList";
    }
}
