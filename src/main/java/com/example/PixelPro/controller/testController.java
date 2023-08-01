package com.example.PixelPro.controller;

import com.example.PixelPro.Bean.MemberBean;
import com.example.PixelPro.entity.Member;
import com.example.PixelPro.repository.AtapprovalRepository;
import com.example.PixelPro.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

// 로그인을 위한 임시 컨트롤러입니다.
@Controller
@RequiredArgsConstructor
public class testController {

    private final MemberRepository memberRepository;

    @GetMapping("/login")
    public String login(){
        return "member/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("id") String id, @RequestParam("pw") String pw, HttpSession session){
        Member member = memberRepository.findByEmail(id);
        if(member == null){
            System.out.println("로그인실패");
            return "redirect:/login";
        }
        else{
            session.setAttribute("loginInfo",member);
            return "redirect:/";
        }
    }
}
