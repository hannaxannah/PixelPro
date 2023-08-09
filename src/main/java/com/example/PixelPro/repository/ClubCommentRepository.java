package com.example.PixelPro.repository;


import com.example.PixelPro.entity.ClubComment;
import com.example.PixelPro.entity.FreeCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubCommentRepository extends JpaRepository<ClubComment,Integer> {


    ClubComment findTopByClnumOrderByClstepDesc(int clnum);

    ClubComment findByConum(int conum);

    List<ClubComment> findByClnumOrderByClstepAscCllevelAsc(int clnum);

    ClubComment findTopByClstepOrderByCllevelDesc(int clstep);
}
