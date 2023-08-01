package com.example.PixelPro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.PixelPro.entity.Inbox;

public interface MailRepository extends JpaRepository<Inbox, Integer> {
    
}
