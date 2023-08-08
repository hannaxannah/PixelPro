package com.example.PixelPro.repository;

import com.example.PixelPro.entity.SecondhandEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SecondhandRepository extends JpaRepository<SecondhandEntity,Integer> {

    @Query("SELECT s FROM SecondhandEntity s WHERE s.shcategory = :category and s.shtitle LIKE %:keyword% ORDER BY s.shnum DESC")
    Page<SecondhandEntity> findByShcategoryAndShtitleLike(@Param("category") String shcategory,
                                                          @Param("keyword") String keyword, Pageable pageable);

}
