package com.example.PixelPro.service;

import com.example.PixelPro.entity.Commute;
import com.example.PixelPro.repository.CommuteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommuteService {
    private final CommuteRepository commuteRepository;

    public void save(Commute commute) {
        commuteRepository.save(commute);
    }

    public List<Commute> findByMbnum(int mbnum) {
        return commuteRepository.findByMbnum(mbnum);
    }


    public Commute findTop1ByMbnumOrderByCmnumDesc(int mbnum) {
        return commuteRepository.findTop1ByMbnumOrderByCmnumDesc(mbnum);
    }
}
