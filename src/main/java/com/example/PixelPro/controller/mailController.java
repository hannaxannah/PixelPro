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
    public String gotoFullMail(){

        return "mail/fullMail";
    }

    /* 받은 메일함 */
    @GetMapping("/mail/inbox")
    public String gotoInbox(){

        return "mail/inbox";
    }

    /* 보낸 메일함 */
    @GetMapping("/mail/sentMail")
    public String gotoSentMail(){

        return "mail/sentMail";
    }

    /* 임시 보관함 */
    @GetMapping("/mail/draftBox")
    public String gotoDraftBox(){

        return "mail/draftBox";
    }

    /* 내게 쓴 메일함 */
    @GetMapping("/mail/mailToMe")
    public String gotoMailToMel(){

        return "mail/mailToMe";
    }

    /* 휴지통 */
    @GetMapping("/mail/trashCan")
    public String gotoTrashCan(){

        return "mail/trashCan";
    }

}
