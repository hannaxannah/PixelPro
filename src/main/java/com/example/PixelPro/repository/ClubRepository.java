package com.example.PixelPro.repository;

import com.example.PixelPro.entity.Club;
import com.example.PixelPro.entity.FreeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClubRepository extends JpaRepository<Club, Integer> {

    List<Club> findByOrderByCldateDesc();

    Club findByCltitle(String cltitle);


    @Query("SELECT f FROM Club f WHERE f.clcategory = :category and f.cltitle LIKE %:keyword% ORDER BY f.clnum DESC")
    Page<Club> findByClcategoryAndCltitleLike(@Param("category") String clcategory,
                                                  @Param("keyword") String keyword, Pageable pageable);

    Club findByClnum(int clnum);

    List<Club> findByClwriterOrderByClnumDesc(int mbnum);
}
