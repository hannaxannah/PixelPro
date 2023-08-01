package com.example.PixelPro.controller;

import com.example.PixelPro.service.mailService;
import com.example.PixelPro.mapper.mailMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class mailController {

    private final mailService mailservice;
    private final mailMapper mailmapper;

    /* 전체 메일함 */
    @GetMapping("/mail/fullMail")
    public String gotoFullMail(HttpSession session){
        session.setAttribute("MailBar","fullMail");
        return "mail/fullMail";
    }

    /* 받은 메일함 */
    @GetMapping("/mail/inbox")
    public String gotoInbox(HttpSession session){
        session.setAttribute("MailBar","inbox");
        return "mail/inbox";
    }

    /* 보낸 메일함 */
    @GetMapping("/mail/sentMail")
    public String gotoSentMail(HttpSession session){
        session.setAttribute("MailBar","sentMail");
        return "mail/sentMail";
    }

    /* 임시 보관함 */
    @GetMapping("/mail/draftBox")
    public String gotoDraftBox(HttpSession session){
        session.setAttribute("MailBar","draftBox");
        return "mail/draftBox";
    }

    /* 내게 쓴 메일함 */
    @GetMapping("/mail/mailToMe")
    public String gotoMailToMel(HttpSession session){
        session.setAttribute("MailBar","mailToMe");
        return "mail/mailToMe";
    }

    /* 휴지통 */
    @GetMapping("/mail/trashCan")
    public String gotoTrashCan(HttpSession session){
        session.setAttribute("MailBar","trashCan");
        return "mail/trashCan";
    }
    
    /* 안읽음 */
    @GetMapping("/mail/unreadMail")
    public String gotoUnreadMail(HttpSession session){
        session.setAttribute("MailBar","unreadMail");
        return "mail/unreadMail";
    }

    /* 안읽음 */
    @GetMapping("/mail/scrapMail")
    public String gotoScrapMail(HttpSession session){
        session.setAttribute("MailBar","scrapMail");
        return "mail/scrapMail";
    }

    /* 첨부 */
    @GetMapping("/mail/clipMail")
    public String gotoClipMail(HttpSession session){
        session.setAttribute("MailBar","clipMail");
        return "mail/clipMail";
    }

}
