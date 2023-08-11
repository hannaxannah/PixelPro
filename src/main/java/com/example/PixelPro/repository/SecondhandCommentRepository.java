package com.example.PixelPro.repository;

import com.example.PixelPro.entity.FreeCommentEntity;
import com.example.PixelPro.entity.SecondhandCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SecondhandCommentRepository extends JpaRepository<SecondhandCommentEntity,Integer> {

    List<SecondhandCommentEntity> findByShnumOrderByShcstepAsc(int shnum);

    List<SecondhandCommentEntity> findByShnumOrderByShcnumAsc(int shnum);

    SecondhandCommentEntity findTopByShnumOrderByShcstepDesc(int shnum);

    SecondhandCommentEntity findByShcnum(int shcnum);

    List<SecondhandCommentEntity> findByShnumOrderByShcstepAscShclevelAsc(int shnum);

    SecondhandCommentEntity findTopByShcstepOrderByShclevelDesc(int shcstep);

    @Query("SELECT s FROM SecondhandCommentEntity s WHERE s.shcstep = :shcstep AND s.shnum = :shnum")
    List<SecondhandCommentEntity> findByShcstepAndShnum(@Param("shcstep") Integer shcstep, @Param("shnum") Integer shnum);
}
