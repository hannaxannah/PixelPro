package com.example.PixelPro.service;

import com.example.PixelPro.entity.FreeEntity;
import com.example.PixelPro.repository.FreeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FreeService {

    @Autowired
    private final FreeRepository freeRepository;

    public void saveFree(FreeEntity freeEntity) {
        freeRepository.save(freeEntity);
    }

    public FreeEntity findByFnum(int fnum) {
        FreeEntity freeEntity = freeRepository.findById(fnum).orElseThrow();
        return freeEntity;
    }

    public Page<FreeEntity> getListsBySearch(Map<String, String> map, int pageNumber) {
        //페이지 정렬 정의
        Pageable pageable = PageRequest.of(pageNumber,5, Sort.by(Sort.Direction.DESC, "fnum"));

        //list 처음(1페이지) 접속 시 page=0, size=5 ex)total=23 => 23,22,21,20,19
        //list 2페이지 접속 시 page=1, size=5 ex)total=23 => 18,17,16,15,14
        if ( (map.get("fcategory") == null || map.get("ftitle") == "%null%")
                || (map.get("fcategory") == "" && map.get("ftitle") == "") ) {
            Page<FreeEntity> findAll = freeRepository.findAll(pageable);
            return findAll;
        } else {
            Page<FreeEntity> findAllBySearch = freeRepository.findByFcategoryAndFtitleLike(map.get("fcategory"), map.get("keyword"), pageable);
            return findAllBySearch;
        }

    }

    public void deleteByFnum(int fnum) {
        freeRepository.deleteById(fnum);
    }

}
