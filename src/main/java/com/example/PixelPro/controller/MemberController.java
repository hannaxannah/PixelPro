package com.example.PixelPro.controller;



import com.example.PixelPro.Bean.MemberBean;
import com.example.PixelPro.entity.Commute;
import com.example.PixelPro.entity.Member;
import com.example.PixelPro.service.CommuteService;
import com.example.PixelPro.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    @Autowired
    private final MemberService memberService;
    private final CommuteService commuteService;

    @GetMapping("/member/regist")
    public String registGet(Model model){
        String[] mblevel = {"사장", "부사장", "부장",  "차장", "과장", "팀장", "대리", "주임", "사원"};
        model.addAttribute("mblevel", mblevel);
        model.addAttribute("member", new MemberBean());
        return "member/regist";
    }

    @PostMapping("/member/regist")
    public String registPost(MemberBean memberBean, Model model, @RequestParam MultipartFile mbsignfile) throws IOException {
        String[] mblevel = {"사장", "부사장", "부장",  "차장", "과장", "팀장", "대리", "주임", "사원"};
        for (int i=0; i<mblevel.length; i++){
            if (mblevel[i].equals(memberBean.getMblevel())){
                memberBean.setMbaccess(String.valueOf(i));
            }
        }
        memberBean.setMstate("재직");
        System.out.println("파일:" + mbsignfile.getOriginalFilename());
        memberBean.setMbsign(mbsignfile.getOriginalFilename());
        mbsignfile.transferTo(new File(mbsignfile.getOriginalFilename()));
        Member member = Member.insertMember(memberBean);
        memberService.save(member);
        return "redirect:/";
    }

    @GetMapping("/member/emailcheck")
    public ResponseEntity<?> checkEmailDuplication(@RequestParam String email) {
        System.out.println(email);
        boolean isDuplicate = memberService.isEmailDuplicate(email);

        if (isDuplicate) {
            return ResponseEntity.ok("{\"exists\": true}");
        } else {
            return ResponseEntity.ok("{\"exists\": false}");
        }

    }

    @GetMapping("/login")
    public String login(){
        return "member/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("id") String id, @RequestParam("pw") String pw, HttpSession session, Model model,
                        HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Member member = memberService.findByEmail(id);
        
        // 로그인 페이지 이동 세션 설정
        /*String destination = (String)session.getAttribute("destination");*/

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();


        if(member == null){
            out.println("<script>alert('등록된 이메일이 아닙니다.');location.href='/login';</script>");
            out.close(); // 오류 메시지 전달
            return "member/login";
        } else if (!member.getPassword().equals(pw)) {
            out.println("<script>alert('비밀번호가 틀렸습니다..');location.href='/login';</script>");
            out.close(); // 오류 메시지 전달

            return "member/login";
        } else{

            session.setAttribute("loginInfo",member);
            session.setAttribute("mbnum", member.getMbnum());

            List<Commute> existDateList = commuteService.findByMbnum(member.getMbnum());
            if (existDateList.isEmpty()) {
                out.println("<script>if (confirm('출근 등록 하시겠습니까?')) { location.href='/commute/attendanceGotoWork'; }</script>");
                out.close();
            } else {
                LocalDate today = LocalDate.now();
                for (Commute existDate : existDateList) {
                    LocalDate gotoworkDate = existDate.getGotowork().toLocalDateTime().toLocalDate();
                    if (today.equals(gotoworkDate) == false) {
                        out.println("<script>if (confirm('출근 등록 하시겠습니까?')) { location.href='/commute/attendanceGotoWork'; }</script>");
                        out.close();
                    }
                }
            }
            // 로그인 페이지 이동 세션 설정
            /*if(destination != null){
                return destination;
            }*/

            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

}
