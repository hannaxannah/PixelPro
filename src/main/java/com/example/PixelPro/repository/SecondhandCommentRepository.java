package com.example.PixelPro.repository;

import com.example.PixelPro.entity.SecondhandCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SecondhandCommentRepository extends JpaRepository<SecondhandCommentEntity,Integer> {

    List<SecondhandCommentEntity> findByShnumOrderByShcstepAsc(int shnum);

    List<SecondhandCommentEntity> findByShnumOrderByShcnumAsc(int shnum);

    SecondhandCommentEntity findTopByShnumOrderByShcstepDesc(int shnum);

    SecondhandCommentEntity findByShcnum(int shcnum);

    List<SecondhandCommentEntity> findByShnumOrderByShcstepAscShclevelAsc(int shnum);

    SecondhandCommentEntity findTopByShcstepOrderByShclevelDesc(int shcstep);
}
