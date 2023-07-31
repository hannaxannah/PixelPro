package com.example.PixelPro.service;

import com.example.PixelPro.entity.Conversation;
import com.example.PixelPro.repository.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversationService {
    @Autowired
    ConversationRepository conversationRepository;


    public Conversation getConversationByCnum(int cnum) {
        Conversation conversation = conversationRepository.findByCnum(cnum);
        return conversation;
    }
}
