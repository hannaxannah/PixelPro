package com.example.PixelPro.repository;

import com.example.PixelPro.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ConversationRepository extends JpaRepository<Conversation, Integer> {

    Conversation findByCnum(int cnum);

    @Query(value = "select max(cnum) from conversation", nativeQuery = true)
    int findMaxCnum();
}