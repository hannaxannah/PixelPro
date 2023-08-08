package com.example.PixelPro.controller;

import com.example.PixelPro.Bean.AlertBean;
import com.example.PixelPro.entity.Alert;
import com.example.PixelPro.entity.Member;
import com.example.PixelPro.service.AlertService;
import com.example.PixelPro.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class AlertController {
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private final AlertService alertService;

    @Autowired
    private final MemberService memberService;

    @MessageMapping("/alert/list")
    public void index(String mbnum){
        List<Alert> alist = new ArrayList<Alert>();
        alist = alertService.findByMbnumOrderByAlnumDesc(Integer.parseInt(mbnum));
        messagingTemplate.convertAndSend("/topic/alert/" + mbnum, alist);
    }

    @MessageMapping("/alert/unread")
    public void unreadIndex(String mbnum){
        List<Alert> alist = new ArrayList<Alert>();
        alist = alertService.findByMbnumOrderByAlnumDesc(Integer.parseInt(mbnum));
        List<Alert> unReadList = new ArrayList<Alert>();
        for(Alert a : alist){
            if(a.getAlread() == 0){
                unReadList.add(a);
            }
        }
        messagingTemplate.convertAndSend("/topic/unread/" + mbnum, unReadList);
    }

    @MessageMapping("/alert/unreadCount")
    public void unreadCount(String mbnum){
        List<Alert> alist = new ArrayList<Alert>();
        alist = alertService.findByMbnumOrderByAlnumDesc(Integer.parseInt(mbnum));
        List<Alert> unReadList = new ArrayList<Alert>();
        for(Alert a : alist){
            if(a.getAlread() == 0){
                unReadList.add(a);
            }
        }
        int unreadCount = unReadList.size();
        messagingTemplate.convertAndSend("/topic/unreadCount/" + mbnum, unreadCount);
    }


    @MessageMapping("/alert/insertEmail")
    public void insert(String sendData){
        String[] data = sendData.split("_");
        String rec = data[0];
        String send = data[1];
        String alcontent = data[2];
        String alurl = data[3];
        System.out.println("내용: " + rec + send + alcontent + alurl);

        Member recMember = memberService.findByEmail(rec);
        Member sendMember = memberService.findByEmail(send);
        Alert alert = new Alert();
        alert.setMbnum(recMember.getMbnum());
        alert.setAlurl(alurl);
        alert.setAlread(0);
        alert.setAlcontent(sendMember.getMbname() + "님의" + " " + alcontent);
        alertService.save(alert);


    }

    @MessageMapping("/alert/sendAll")
    public void sendAll(String sendData){
        String[] data = sendData.split("_");

        String alcontent = data[0];
        String alurl = data[1];

        List<Member> memberList = memberService.findAll();
        for(Member m: memberList){
            Alert alert = new Alert();
            alert.setMbnum(m.getMbnum());
            alert.setAlurl(alurl);
            alert.setAlread(0);
            alert.setAlcontent(alcontent);
            alertService.save(alert);
            System.out.println("아아아아");
        }
    }


    @GetMapping("*/alert/read")
    public String readAlert(@RequestParam String alnum){
        String aln = alnum.replace("\"", "");

        Alert alert = alertService.findByAlnum(Integer.parseInt(aln));
        alert.setAlread(1);
        alertService.save(alert);
        return "redirect:" + alert.getAlurl();
    }

    @GetMapping("/alert/read")
    public String readAlert2(@RequestParam String alnum){
        String aln = alnum.replace("\"", "");

        Alert alert = alertService.findByAlnum(Integer.parseInt(aln));
        alert.setAlread(1);
        alertService.save(alert);
        return "redirect:" + alert.getAlurl();
    }
}
