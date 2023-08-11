package com.example.PixelPro.repository;

import com.example.PixelPro.entity.FreeCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FreeCommentRepository  extends JpaRepository<FreeCommentEntity,Integer> {

    List<FreeCommentEntity> findByFnumOrderByFcstepAsc(int fnum);

    List<FreeCommentEntity> findByFnumOrderByFcnumAsc(int fnum);

    FreeCommentEntity findTopByFnumOrderByFcstepDesc(int fnum);

    FreeCommentEntity findByFcnum(int fcnum);

    List<FreeCommentEntity> findByFnumOrderByFcstepAscFclevelAsc(int fnum);

    FreeCommentEntity findTopByFcstepOrderByFclevelDesc(int fcstep);

    @Query("SELECT f FROM FreeCommentEntity f WHERE f.fcstep = :fcstep AND f.fnum = :fnum")
    List<FreeCommentEntity> findByFcstepAndFnum(@Param("fcstep") Integer fcstep, @Param("fnum") Integer fnum);
}
