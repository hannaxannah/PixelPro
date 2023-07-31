package com.example.PixelPro.service;

import com.example.PixelPro.entity.Message;
import com.example.PixelPro.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;

    public Message getRecentMessage(int cnum) {
        Message message = messageRepository.findRecentMessage(cnum);
        return message;
    }

    public List<Message> getMessageList(int cnum) {
        List<Message> messageList = messageRepository.findAllByCnumOrderByMtimeAsc(cnum);
        return messageList;
    }

    public void save(Message message) {
        messageRepository.save(message);
    }
}
