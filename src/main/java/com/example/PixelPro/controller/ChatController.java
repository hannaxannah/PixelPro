package com.example.PixelPro.controller;

import com.example.PixelPro.Bean.ChatListBean;
import com.example.PixelPro.Bean.ConversationInfoBean;
import com.example.PixelPro.Bean.MessageBean;
import com.example.PixelPro.entity.*;
import com.example.PixelPro.service.*;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
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
            if(unreadCount > 0){
                chatList.add(0, singlechat); //안읽은 메시지 있으면 처음부터 나오게 하기.
            } else {
                chatList.add(singlechat);
            }
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
        getAllUnread(String.valueOf(mbnum));
        getRecentConversations(String.valueOf(mbnum));
    }

    @MessageMapping("/chat/allUsers")
    public void getAllUsers(String mbnum){
        List<Member> memberList = userAndGroupService.getAllMembersExcludingLogin(Integer.parseInt(mbnum));
        messagingTemplate.convertAndSend("/topic/allUsers", memberList);
    }

    @PostMapping("/chat/createConversation")
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
            if(!mbnum[i].equals("") && !mbnum[i].equals("0") || mbnum[i] == null){
                participant.setMbnum(Integer.parseInt(mbnum[i]));
                participantService.save(participant);
            }
        }
        //메시지 보내기
        String sendData = mbnum[0] + "/" +  cnum + "/" + mcontent;
        sendMessage(sendData);
        //새로고침 하면 다시 요청 안하게 redirect한다.
        return "redirect:/chat";
    }

    @GetMapping("/chat")
    public String goToChat(@RequestParam(value = "cnum", required = false) Integer cnum, Model model){
        if(cnum != null){
            model.addAttribute("cnum", cnum);
        }
        return "/chat/chat";
    }
    @MessageMapping("/chat/conversationInfo")
    public void sendConversationInfo(String sendData){
        String[] data = sendData.split("/");
        int mbnum = Integer.parseInt(data[0]);
        int cnum = Integer.parseInt(data[1]);
        Conversation conversation = conversationService.getConversationByCnum(cnum);
        List<Participant> participantList = participantService.getParticipantsByCnum(cnum);
        List<ConversationInfoBean> cList = new ArrayList<ConversationInfoBean>();
        for(Participant p : participantList){
            int mbnum2 = p.getMbnum();
            Member member = userAndGroupService.getMemberByMbnum(mbnum2);
            String pName = member.getMbname() + " " + member.getMblevel() + "(" + member.getDept() + ")";
            ConversationInfoBean cInfo = new ConversationInfoBean();
            cInfo.setCname(conversation.getCname());
            cInfo.setPname(pName);
            cList.add(cInfo);
        }
        messagingTemplate.convertAndSend("/topic/conversationInfo/" + mbnum, cList);
    }

    @MessageMapping("/chat/deleteMessage")
    public void deleteMessage(String sendData) { //실제 삭제는 아니고 "메시지가 삭제 되었습니다" 라고 변경.
        String[] data = sendData.split("/");
        int mbnum = Integer.parseInt(data[0]);
        int mnum = Integer.parseInt(data[1]);
        messageService.deleteMessage(mnum);
        Message message = messageService.getMessageByMnum(mnum);
        String sendData2 = mbnum + "/" + message.getCnum() + "/" + message.getMcontent();
        messagingTemplate.convertAndSend("/topic/receiveMessage/" + mbnum, message);
    }

    @MessageMapping("/chat/search")
    public void searchConversation(String sendData){
        String[] data = sendData.split("/");
        String search = data[0];
        List<Participant> partList = participantService.getParticipantByMbNum(Integer.parseInt(data[1]));
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
            if(unreadCount > 0){
                chatList.add(0, singlechat); //안읽은 메시지 있으면 처음부터 나오게 하기.
            } else {
                chatList.add(singlechat);
            }
        }
        List<ChatListBean> cList = new ArrayList<ChatListBean>();
        for(ChatListBean chatListBean : chatList){
            String cname = chatListBean.getCname();
            List<Participant> pList = participantService.getParticipantsByCnum(chatListBean.getCnum());
            if(cname.indexOf(search) > -1){
                cList.add(chatListBean);
            }

            for(Participant p : pList){
                int mbnum = p.getMbnum();
                int cnum = p.getCnum();
                Member member = userAndGroupService.getMemberByMbnum(mbnum);
                String name = member.getMbname();
                if(name.contains(search)){
                    boolean flag = true;
                    for(ChatListBean clistBean : cList){
                        if(clistBean.getCnum() == cnum){
                            flag = false;
                            break;
                        }
                    }
                    if(flag == true){
                        cList.add(chatListBean);
                    }
                }
            }
        }
        messagingTemplate.convertAndSend("/topic/conversation/" + data[1], cList);
    }

    @MessageMapping("/chat/convUsers")
    public void getConvUsers(String num){
        int cnum = Integer.parseInt(num);
        List<Participant> participantList = participantService.getParticipantsByCnum(cnum);
        List<Integer> mbnums = new ArrayList<Integer>();
        for(Participant p : participantList){
            mbnums.add(p.getMbnum());
        }
        List<Member> memberList = userAndGroupService.getAllMembersExcludingConvUsers(mbnums);
        messagingTemplate.convertAndSend("/topic/convUsers", memberList);
    }
    @PostMapping("/chat/addUsers")
    public String addUsers(String[] mbnum, String convNum){
        int cnum = Integer.parseInt(convNum);
        for(String s : mbnum){
            Participant participant = new Participant();
            if(!s.equals("") && !s.equals("0") || s == null){
                int memberNum = Integer.parseInt(s);
                participant.setMbnum(memberNum);
                participant.setCnum(cnum);
                participantService.save(participant);
            }
        }
        return "redirect:/chat";
    }

    @MessageMapping("/chat/leaveConvo")
    public void leaveConvo(String sendData){
        String[] data = sendData.split("/");
        int cnum = Integer.parseInt(data[0]);
        int mbnum = Integer.parseInt(data[1]);
        Participant participant = participantService.getParticipantByMbnumAndCnum(mbnum, cnum);
        int cpnum = participant.getCpnum();
        participantService.deleteParticipant(participant);
        index((String.valueOf(mbnum)));
    }

    @MessageMapping("/chat/getTotalUnreadCount")
    public void getAllUnread(String mbnum){
        int mbnumber = Integer.parseInt(mbnum);
        List<Participant> participantList = participantService.getParticipantByMbNum(mbnumber);
        int unreadCount = msgstatusService.getTotalUnread(participantList);
        messagingTemplate.convertAndSend("/topic/getTotalUnread/" + mbnumber, unreadCount);
    }
    @MessageMapping("/chat/getRecentConversations")
    public void getRecentConversations(String mbnum){
        int mbnumber = Integer.parseInt(mbnum);
        int i = 0;
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
            if(unreadCount > 0){
                chatList.add(0, singlechat); //안읽은 메시지 있으면 처음부터 나오게 하기.
            } else {
                chatList.add(singlechat);
            }
            i++;
            if(i == 5){
                break;
            }
        }
        messagingTemplate.convertAndSend("/topic/recentConversations/"+mbnumber, chatList);
    }
}
