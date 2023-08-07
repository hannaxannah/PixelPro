package com.example.PixelPro.service;

import com.example.PixelPro.entity.Gapproval;
import com.example.PixelPro.repository.GapprovalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GapprovalService {
    private final GapprovalRepository gapprovalRepository;

    public void save(Gapproval gapproval) {
        gapprovalRepository.save(gapproval);
    }

    public Gapproval findByGanum(Integer ganum) {
        return gapprovalRepository.findByGanum(ganum);
    }

    public List<Gapproval> findByGwmbnumOrderByGanumDesc(int mbnum) {
        return gapprovalRepository.findByGwmbnumOrderByGanumDesc(mbnum);
    }

    public List<Gapproval> findByGhmbnumOrderByGanumDesc(int mbnum) {
        return gapprovalRepository.findByGhmbnumOrderByGanumDesc(mbnum);
    }
}
