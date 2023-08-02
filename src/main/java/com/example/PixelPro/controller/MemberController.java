package com.example.PixelPro.controller;



import com.example.PixelPro.Bean.MemberBean;
import com.example.PixelPro.entity.Member;
import com.example.PixelPro.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class MemberController {
    @Autowired
    private final MemberService memberService;

    @GetMapping("/member/regist")
    public String registGet(Model model){

        return "member/regist";
    }

    @PostMapping("/member/regist")
    public String registPost(MemberBean memberBean, Model model){
        if (memberBean.getMblevel().equals("팀장")){
            memberBean.setMbaccess("admin");
        }else{
            memberBean.setMbaccess("normal");
        }
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
    public String login(@RequestParam("id") String id, @RequestParam("pw") String pw, HttpSession session){
        Member member = memberService.findByEmail(id);
        if(member == null){
            System.out.println("로그인실패");
            return "redirect:/login";
        }
        else{
            session.setAttribute("loginInfo",member);
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

}
