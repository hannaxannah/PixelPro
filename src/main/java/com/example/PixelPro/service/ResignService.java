package com.example.PixelPro.service;

import com.example.PixelPro.repository.ResignRepository;
import com.example.PixelPro.repository.SalaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResignService {

    private final ResignRepository resignRepository;


}
