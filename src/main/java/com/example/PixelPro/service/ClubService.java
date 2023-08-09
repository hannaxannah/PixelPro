package com.example.PixelPro.service;

import com.example.PixelPro.entity.Club;
import com.example.PixelPro.repository.ClubRepository;
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
public class ClubService {

    @Autowired
    private final ClubRepository clubrepository;

    public void saveClub(Club club) {
        clubrepository.save(club);
    }

    public Club findByClnum(int clnum) {
        Club club = clubrepository.findById(clnum).orElseThrow();
        return club;
    }


    public Page<Club> getListsBySearch(Map<String, String> map, int pageNumber) {
        //페이지 정렬 정의
        Pageable pageable = PageRequest.of(pageNumber,5, Sort.by(Sort.Direction.DESC, "clnum"));


        if ( (map.get("clcategory") == null || map.get("cltitle") == "%null%")
                || (map.get("clcategory") == "" && map.get("cltitle") == "") ) {
            Page<Club> findAll = clubrepository.findAll(pageable);
            return findAll;
        } else {
            Page<Club> findAllBySearch = clubrepository.findByClcategoryAndCltitleLike(map.get("clcategory"), map.get("keyword"), pageable);
            return findAllBySearch;
        }

    }

    public void deleteByClnum(int clnum) {
        clubrepository.deleteById(clnum);
    }

}
