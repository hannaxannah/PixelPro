package com.example.PixelPro.service;

import com.example.PixelPro.entity.Atapproval;
import com.example.PixelPro.entity.Gapproval;
import com.example.PixelPro.repository.AtapprovalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtapprovalService {
    private final AtapprovalRepository atapprovalRepository;

    public void save(Atapproval atapproval) {
        atapprovalRepository.save(atapproval);
    }
}
