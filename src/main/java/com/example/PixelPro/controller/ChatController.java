package com.example.PixelPro.controller;

import com.example.PixelPro.Bean.ChatListBean;
import com.example.PixelPro.Bean.MessageBean;
import com.example.PixelPro.entity.*;
import com.example.PixelPro.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@CrossOrigin
public class ChatController {
    @Autowired
    HttpSession httpSession;
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;
    @Autowired
    MessageService messageService;
    @Autowired
    UserAndGroupService userAndGroupService;
    @Autowired
    ConversationService conversationService;
    @Autowired
    MsgstatusService msgstatusService;
    @Autowired
    ParticipantService participantService;


    @MessageMapping("/chat/conversation")
    public void index(String mbnum){
        System.out.println("TEST: " + mbnum);
        //int mbnum = member.getMbnum(); //현재 로그인 된 회원 번호.
        List<Participant> partList = participantService.getParticipantByMbNum(Integer.parseInt(mbnum));
        List<ChatListBean> chatList = new ArrayList<ChatListBean>();
        for(Participant p : partList){
            int cnum  = p.getCnum();
            int cpnum = p.getCpnum();
            Conversation conversation = conversationService.getConversationByCnum(cnum);
            Message message = messageService.getRecentMessage(cnum);
            int unreadCount = msgstatusService.getUnreadCount(cnum, cpnum);
            ChatListBean singlechat = new ChatListBean();
            singlechat.setCname(conversation.getCname()); //대화 이름
            singlechat.setRecentMsg(message.getMcontent()); //제일 최근 메시지 내용
            singlechat.setRecentSenderName(message.getSender()); //제일 최근 메시지 이름
            singlechat.setUnreadCount(unreadCount); //안 읽은 메시지 수.
            singlechat.setCnum(conversation.getCnum());
            chatList.add(singlechat);
        }
        messagingTemplate.convertAndSend("/topic/conversation/" + mbnum, chatList);
    }

    @MessageMapping("/chat/message") //메시지 목록 가져오는 메서드
    public void message(String sendData){
        String[] data = sendData.split("/");
        int mbnum = Integer.parseInt(data[0]);
        int cnum = Integer.parseInt(data[1]);
        System.out.println("mbnum : " + mbnum);
        System.out.println("cnum: " + cnum);
        Member member = userAndGroupService.getMemberByMbnum(mbnum);
        List<Message> messages = messageService.getMessageList(cnum);
        System.out.println("size: " + messages.size());
        //로그인된 직원을 넣기 위해 다시 빈으로 넣는다. 엔티티는 현재 로그인된 이름 없음
        List<MessageBean> messageList = new ArrayList<MessageBean>();
        String pattern = "dd-MM-yyyy hh:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        //다 읽은 표시.
        Participant participant = participantService.getParticipantByMbnumAndCnum(mbnum, cnum);
        int cpnum = participant.getCpnum();
        msgstatusService.update("read", cpnum, cnum);
        for(int i = 0; i < messages.size(); i++){
            Message message = messages.get(i);
            MessageBean currentMsg = new MessageBean();
            currentMsg.setCnum(message.getCnum());
            currentMsg.setMnum(message.getMnum());
            currentMsg.setMblevel(message.getMblevel());
            currentMsg.setCpnum(message.getCpnum());
            currentMsg.setMcontent(message.getMcontent());
            currentMsg.setMtime(message.getMtime());
            currentMsg.setSender(message.getSender());
            currentMsg.setLoginName(member.getMbname());
            messageList.add(currentMsg);
        }


        //대화 목록 신호 보낸다.
        index((String.valueOf(mbnum)));
        messagingTemplate.convertAndSend("/topic/message/"+ mbnum, messageList);
    }

    @MessageMapping("/chat/sendMessage")
    public void sendMessage(String sendData){
        String[] data = sendData.split("/");
        int mbnum = Integer.parseInt(data[0]);
        int cnum = Integer.parseInt(data[1]);
        String mcontent = data[2];
        Member member = userAndGroupService.getMemberByMbnum(mbnum);
        Participant participant = participantService.getParticipantByMbnumAndCnum(mbnum, cnum);
        List<Participant> participantList = participantService.getParticipantsByCnum(cnum);
        Message message = new Message();
        message.setCnum(cnum);
        message.setCpnum(participant.getCpnum());
        message.setSender(member.getMbname());
        message.setMblevel(member.getMblevel());
        message.setMcontent(mcontent);
        Date date = new Date();
        String pattern = "YY/MM/dd hh:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        message.setMtime(simpleDateFormat.format(date));
        //메시지 DB입력
        messageService.save(message);
        Message newMessage = messageService.getRecentMessage(cnum);
        //실시간으로 메시지 보내기 + 읽은/안읽은 상태 삽입.
        for(Participant p : participantList){
            int pmbnum = p.getMbnum();
            Msgstatus msgstatus = new Msgstatus();
            if(pmbnum == mbnum){
                msgstatus.setIsread("read");
            } else {
                msgstatus.setIsread("unread");
            }
            msgstatus.setCnum(cnum);
            msgstatus.setMnum(newMessage.getMnum());
            msgstatus.setCpnum(p.getCpnum());
            msgstatusService.save(msgstatus);
            messagingTemplate.convertAndSend("/topic/receiveMessage/" + pmbnum, message);
        }
    }

    @MessageMapping("/chat/allUsers")
    public void getAllUsers(String mbnum){
        List<Member> memberList = userAndGroupService.getAllMembersExcludingLogin(Integer.parseInt(mbnum));
        messagingTemplate.convertAndSend("/topic/allUsers", memberList);
    }

    @GetMapping("/chat/createConversation")
    public String addConversation(String[] mbnum, String cname, String mcontent){
        //대화 생성
        Conversation conversation = new Conversation();
        List<Participant> participantList = new ArrayList<Participant>();
        String pattern = "YY/MM/dd hh:mm";
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        conversation.setCdate(simpleDateFormat.format(date));
        conversation.setCname(cname);
        conversationService.save(conversation); //대화 DB 삽입
        int cnum = conversationService.getMaxCnum(); //방금 생성한 대화의 번호
        //대화의 참여자 DB 삽입
        for(int i = 0; i < mbnum.length; i++){
            Participant participant = new Participant();
            System.out.println("mbnum" + i + ": " + mbnum[i]);
            participant.setCnum(cnum);
            if(!mbnum[i].equals("")){
                participant.setMbnum(Integer.parseInt(mbnum[i]));
            }
            participantService.save(participant);
        }
        //메시지 보내기
        String sendData = mbnum[0] + "/" +  cnum + "/" + mcontent;
        sendMessage(sendData);

        return "/chat/chat";
    }
}
