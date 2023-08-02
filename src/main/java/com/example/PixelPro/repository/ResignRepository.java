package com.example.PixelPro.repository;

import com.example.PixelPro.entity.ResignEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResignRepository extends JpaRepository<ResignEntity, Integer> {
    List<ResignEntity> findByOrderBySevpayDesc();
}
