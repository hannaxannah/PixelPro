package com.example.PixelPro.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.PixelPro.repository.MailRepository;

@Service
@RequiredArgsConstructor
public class MailService {
    private final MailRepository mailrepository;
}
