package com.example.PixelPro.service;

import com.example.PixelPro.repository.AtapprovalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtapprovalService {
    private final AtapprovalRepository atapprovalRepository;
}
