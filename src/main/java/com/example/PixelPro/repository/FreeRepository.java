package com.example.PixelPro.repository;

import com.example.PixelPro.entity.FreeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FreeRepository extends JpaRepository<FreeEntity,Integer> {

    List<FreeEntity> findByOrderByFnumDesc();

    Page<FreeEntity> findByOrderByFnumDesc(Pageable pageable);

    String countByFcategory(String string);

    /*
    @Query("SELECT u FROM User u WHERE u.username LIKE %:char% and u.age > :maxAge")
    List<User> findByLetterWithConditions(@Param("char") char letter,
                                          @Param("maxAge") int age);
    */

    @Query("SELECT f FROM FreeEntity f WHERE f.fcategory = :category and f.ftitle LIKE %:keyword% ORDER BY f.fnum DESC")
    List<FreeEntity> findByFcategoryAndFtitleLike(@Param("category") String fcategory,
                                                  @Param("keyword") String keyword);

    @Query("SELECT f FROM FreeEntity f WHERE f.fcategory = :category and f.ftitle LIKE %:keyword% ORDER BY f.fnum DESC")
    Page<FreeEntity> findByFcategoryAndFtitleLike(@Param("category") String fcategory,
                                                  @Param("keyword") String keyword, Pageable pageable);

}

