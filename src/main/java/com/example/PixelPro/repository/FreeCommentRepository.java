package com.example.PixelPro.repository;

import com.example.PixelPro.entity.FreeCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FreeCommentRepository  extends JpaRepository<FreeCommentEntity,Integer> {

    List<FreeCommentEntity> findByFnumOrderByFcstepAsc(int fnum);

    List<FreeCommentEntity> findByFnumOrderByFcnumAsc(int fnum);

    FreeCommentEntity findTopByFnumOrderByFcstepDesc(int fnum);

    FreeCommentEntity findByFcnum(int fcnum);

    List<FreeCommentEntity> findByFnumOrderByFcstepAscFclevelAsc(int fnum);

    FreeCommentEntity findTopByFcstepOrderByFclevelDesc(int fcstep);
}
