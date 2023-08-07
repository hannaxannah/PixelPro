package com.example.PixelPro.service;

import com.example.PixelPro.entity.Appreturn;
import com.example.PixelPro.entity.Gapproval;
import com.example.PixelPro.repository.AppreturnRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppreturnService {
    private final AppreturnRepository returnRepository;

    public void save(Appreturn appreturn) {
        returnRepository.save(appreturn);
    }
}
