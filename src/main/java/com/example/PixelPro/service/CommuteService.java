package com.example.PixelPro.service;

import com.example.PixelPro.entity.Commute;
import com.example.PixelPro.repository.CommuteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommuteService {
    private final CommuteRepository commuteRepository;

    public void save(Commute commute) {
        commuteRepository.save(commute);
    }
}
