package com.example.PixelPro.service;

import com.example.PixelPro.entity.ResignEntity;
import com.example.PixelPro.entity.SalaryEntity;
import com.example.PixelPro.repository.ResignRepository;
import com.example.PixelPro.repository.SalaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResignService {

    private final ResignRepository resignRepository;

    public void saveResign(ResignEntity resign) {
        resignRepository.save(resign);
    }


    public ResignEntity getResignByServpay(int sevpay) {
        ResignEntity resign = resignRepository.findBySevpay(sevpay);
        return resign;
    }


    public void deleteAllByMbnum(List<Integer> delList) {
        resignRepository.deleteAllById(delList);
    }

    public List<ResignEntity> findByOrderBySevpayDesc() {
        List<ResignEntity> resign = resignRepository.findByOrderBySevpayDesc();
        return resign;
    }
}
