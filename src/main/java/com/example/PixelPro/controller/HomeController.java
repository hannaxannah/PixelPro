package com.example.PixelPro.controller;

import com.example.PixelPro.entity.Member;
import com.example.PixelPro.entity.Notice;
import com.example.PixelPro.service.GapprovalService;
import com.example.PixelPro.service.MemberService;
import com.example.PixelPro.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final NoticeService noticeService;
    private final MemberService memberService;

    @GetMapping("/home")
    public String gotoHome(HttpSession session, HttpServletResponse response, Model model) throws IOException {

        response.setContentType("text/html; charset=UTF-8");
        Member member = (Member)session.getAttribute("loginInfo");
        if(member == null){
            session.setAttribute("destination", "redirect:/mail/inbox");
            response.getWriter().print("<script>alert('로그인이 필요합니다.');location.href='/login'</script>");
            response.getWriter().flush();
        }

        // 공지
        List<Notice> notices = noticeService.findTop10ByOrderByNimportantDescNdateDesc();


        model.addAttribute("notices", notices);

        return "/home";
    }

}
