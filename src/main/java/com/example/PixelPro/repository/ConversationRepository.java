package com.example.PixelPro.repository;

import com.example.PixelPro.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversation, Integer> {

    Conversation findByCnum(int cnum);
}