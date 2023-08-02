package com.example.PixelPro.service;

import com.example.PixelPro.entity.ResignEntity;
import com.example.PixelPro.entity.SalaryEntity;
import com.example.PixelPro.repository.ResignRepository;
import com.example.PixelPro.repository.SalaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResignService {

    private final ResignRepository resignRepository;


    public List<ResignEntity> findByOrderBySevpayDesc() {
        List<ResignEntity> resign = resignRepository.findByOrderBySevpayDesc();
        return resign;
    }
}
