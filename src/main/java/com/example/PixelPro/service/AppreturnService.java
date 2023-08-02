package com.example.PixelPro.service;

import com.example.PixelPro.repository.AppreturnRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppreturnService {
    private final AppreturnRepository returnRepository;
}
