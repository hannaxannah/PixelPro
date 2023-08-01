package com.example.PixelPro.controller;

import com.example.PixelPro.Bean.ChatListBean;
import com.example.PixelPro.Bean.MessageBean;
import com.example.PixelPro.entity.Conversation;
import com.example.PixelPro.entity.Member;
import com.example.PixelPro.entity.Message;
import com.example.PixelPro.entity.Participant;
import com.example.PixelPro.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;

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
            chatList.add(singlechat);
        }
        messagingTemplate.convertAndSend("/topic/conversation/" + mbnum, chatList);
    }

    @MessageMapping("/chat/message")
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

        for(int i = 0; i < messages.size(); i++){
            Message message = messages.get(i);
            MessageBean currentMsg = new MessageBean();
            currentMsg.setCnum(message.getCnum());
            currentMsg.setMnum(message.getMnum());
            currentMsg.setMblevel(message.getMblevel());
            currentMsg.setCpnum(message.getCpnum());
            currentMsg.setMcontent(message.getMcontent());
            currentMsg.setMtime(simpleDateFormat.format(new Date(message.getMtime())));
            currentMsg.setSender(message.getSender());
            currentMsg.setLoginName(member.getMbname());
            messageList.add(currentMsg);


        }
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
        //실시간으로 메시지 보내기
        for(Participant p : participantList){
            int pmbnum = p.getMbnum();
            messagingTemplate.convertAndSend("/topic/receiveMessage/" + pmbnum, message);
        }
    }
}
