package com.example.PixelPro.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.PixelPro.repository.mailRepository;

@Service
@RequiredArgsConstructor
public class mailService {
    private final mailRepository mailrepository;
}
