package com.example.PixelPro.service;

import com.example.PixelPro.entity.PayStubEntity;
import com.example.PixelPro.repository.PayStubRepository;
import com.example.PixelPro.repository.SalaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PayStubService {

    private final PayStubRepository paystubRepository;

    public void savePayStub(PayStubEntity paystub) {
        paystubRepository.save(paystub);
    }

}
