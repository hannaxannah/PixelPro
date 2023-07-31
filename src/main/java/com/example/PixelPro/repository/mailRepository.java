package com.example.PixelPro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.PixelPro.entity.inbox;

public interface mailRepository extends JpaRepository<inbox, Integer> {
    
}
