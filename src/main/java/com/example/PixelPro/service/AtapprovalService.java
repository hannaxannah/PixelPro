package com.example.PixelPro.service;

import com.example.PixelPro.entity.Atapproval;
import com.example.PixelPro.repository.AtapprovalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AtapprovalService {
    private final AtapprovalRepository atapprovalRepository;

    public void save(Atapproval atapproval) {
        atapprovalRepository.save(atapproval);
    }

    public List<Atapproval> findByAtwmbnumOrderByAtnumDesc(int mbnum) {
        return atapprovalRepository.findByAtwmbnumOrderByAtnumDesc(mbnum);
    }

    public List<Atapproval> findByAthmbnumOrderByAtnumDesc(int mbnum) {
        return atapprovalRepository.findByAthmbnumOrderByAtnumDesc(mbnum);
    }

    public Atapproval findByAtnum(Integer atnum) {
        return atapprovalRepository.findByAtnum(atnum);
    }
}
