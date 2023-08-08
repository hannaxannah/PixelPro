package com.example.PixelPro.service;

import com.example.PixelPro.entity.SecondhandEntity;
import com.example.PixelPro.repository.SecondhandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class SecondhandService {

    @Autowired
    private final SecondhandRepository secondhandRepository;

    public void saveSecondhand(SecondhandEntity secondhandEntity) {
        secondhandRepository.save(secondhandEntity);
    }

    public SecondhandEntity findByShnum(int shnum) {
        SecondhandEntity secondhandEntity = secondhandRepository.findById(shnum).orElseThrow();
        return secondhandEntity;
    }

    public Page<SecondhandEntity> getListsBySearch(Map<String, String> map, int pageNumber) {
        //페이지 정렬 정의
        Pageable pageable = PageRequest.of(0,6*(pageNumber+1), Sort.by(Sort.Direction.DESC, "shnum"));

        //list 처음(1페이지) 접속 시 page=0, size=5 ex)total=23 => 23,22,21,20,19
        //list 2페이지 접속 시 page=1, size=5 ex)total=23 => 18,17,16,15,14
        if ( (map.get("shcategory") == null || map.get("ftitle") == "%null%")
                || (map.get("shcategory") == "" && map.get("ftitle") == "") ) {
            Page<SecondhandEntity> findAll = secondhandRepository.findAll(pageable);
            return findAll;
        } else {
            Page<SecondhandEntity> findAllBySearch = secondhandRepository.findByShcategoryAndShtitleLike(map.get("shcategory"), map.get("keyword"), pageable);
            return findAllBySearch;
        }

    }

    public void deleteByShnum(int shnum) {
        secondhandRepository.deleteById(shnum);
    }
}
