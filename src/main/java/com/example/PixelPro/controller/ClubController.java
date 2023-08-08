package com.example.PixelPro.controller;

import com.example.PixelPro.entity.*;
import com.example.PixelPro.service.ClubCommentService;
import com.example.PixelPro.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;
    private final ClubCommentService clubCommentService;
    /*목록*/
    @GetMapping({"/club/list"})
    public String selectAll(HttpSession session, HttpServletResponse response,
                            Model model, @PageableDefault(page=0, size = 5, sort = "clnum", direction = Sort.Direction.DESC) Pageable pageable) throws IOException {

        response.setContentType("text/html; charset=UTF-8");
        Member member = (Member)session.getAttribute("loginInfo");
        if(member == null){
            session.setAttribute("destination", "redirect:/notice/list");
            response.getWriter().print("<script>alert('로그인이 필요합니다.');location.href='/login'</script>");
            response.getWriter().flush();
        }

        Page<Club> list = clubService.findByOrderByClnumDesc(pageable);

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        model.addAttribute("notice", clubService.findByOrderByClnumDesc(pageable));

        return "/club/list";
    }



}
