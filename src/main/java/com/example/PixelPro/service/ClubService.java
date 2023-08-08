package com.example.PixelPro.service;

import com.example.PixelPro.entity.Club;
import com.example.PixelPro.entity.FreeEntity;
import com.example.PixelPro.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;

    public Page<Club> findByOrderByClnumDesc(Pageable pageable) {

        List<Club> clubs = clubRepository.findByOrderByClnumDesc();
        return clubRepository.findAll(pageable);
    }


}
