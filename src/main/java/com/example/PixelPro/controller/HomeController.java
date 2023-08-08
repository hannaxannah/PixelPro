package com.example.PixelPro.controller;

import com.example.PixelPro.Bean.ChatListBean;
import com.example.PixelPro.entity.*;
import com.example.PixelPro.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    @Autowired
    private final ConversationService conversationService;
    @Autowired
    private final MsgstatusService msgstatusService;
    @Autowired
    private final MessageService messageService;
    @Autowired
    private final ParticipantService participantService;

    private final NoticeService noticeService;
    private final GapprovalService gapprovalService;
    private final MailService mailService;

    private final CommuteService commuteService;

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

        // 결재현황
        List<Gapproval> gapprovalList = gapprovalService.findTop5ByGwmbnumOrderByGanumDesc(member.getMbnum());
        model.addAttribute("gapprovalList",gapprovalList);

        // 안읽은 채팅 수
        int totalCount = 0;
        List<Participant> partList = participantService.getParticipantByMbNum(member.getMbnum());
        for(Participant p : partList){
            int cnum  = p.getCnum();
            int cpnum = p.getCpnum();
            int unreadCount = msgstatusService.getUnreadCount(cnum, cpnum);
            System.out.println("unreadCount : " + unreadCount);
            totalCount += unreadCount;
        }
        model.addAttribute("totalCount",totalCount);
        
        // 안읽은 메일 수
        int countMail = mailService.countByRecipientAndStatus(member.getEmail(), "unread");
        model.addAttribute("countMail", countMail);

        Inbox inbox = mailService.findTop1ByRecipientAndStatusOrderBySenddateDesc(member.getEmail(), "unread");
        model.addAttribute("inbox", inbox);

        // 출근 여부
        Commute com = commuteService.findTop1ByMbnumOrderByCmnumDesc(member.getMbnum());

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String now = sdf.format(d);
        String result = "N";


        if(com != null){
            System.out.println("getGoto : " + com.getGotowork());

            if(!com.getGotowork().equals("")){
                String date = sdf.format(com.getGotowork());
                if(now.equals(date)){
                    result = "Y"; // 오늘출근함
                }
            }
        }

        model.addAttribute("commute", result);

        return "/home";
    }

}
