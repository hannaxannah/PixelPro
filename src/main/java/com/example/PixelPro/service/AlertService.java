package com.example.PixelPro.service;

import com.example.PixelPro.entity.Alert;
import com.example.PixelPro.repository.AlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertService {
    private final AlertRepository alertRepository;

    public List<Alert> findByMbnumOrderByAlnumDesc(int mbnum){
        return alertRepository.findByMbnumOrderByAlnumDesc(mbnum);
    }

    public void save(Alert alert) {
        alertRepository.save(alert);
    }

    public Alert findByAlnum(int alnum){
        return alertRepository.findByAlnum(alnum);
    }
}
