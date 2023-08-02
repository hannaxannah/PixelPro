package com.example.PixelPro.service;

import com.example.PixelPro.entity.Gapproval;
import com.example.PixelPro.repository.GapprovalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GapprovalService {
    private final GapprovalRepository gapprovalRepository;

    public void save(Gapproval gapproval) {
        gapprovalRepository.save(gapproval);
    }
}
