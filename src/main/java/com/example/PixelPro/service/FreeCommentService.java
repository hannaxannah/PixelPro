package com.example.PixelPro.service;

import com.example.PixelPro.entity.FreeCommentEntity;
import com.example.PixelPro.repository.FreeCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FreeCommentService {

    @Autowired
    private final FreeCommentRepository freeCommentRepository;

    public void saveFreeComment(FreeCommentEntity freeCommentEntity) {
        freeCommentRepository.save(freeCommentEntity);
    }

    public List<FreeCommentEntity> getAllCommentLists(int fnum) {
        List<FreeCommentEntity> commentlists = freeCommentRepository.findByFnumOrderByFcstepAscFclevelAsc(fnum);
        return commentlists;
    }

    public Integer beforeFcstep(int fnum) {
        FreeCommentEntity freeCommentEntity = freeCommentRepository.findTopByFnumOrderByFcstepDesc(fnum);
        return freeCommentEntity.getFcstep();
    }

    public Integer beforeFclevel(int fcstep) {
        FreeCommentEntity freeCommentEntity = freeCommentRepository.findTopByFcstepOrderByFclevelDesc(fcstep);
        return freeCommentEntity.getFclevel();
    }
    public FreeCommentEntity findByFcnum(int fcnum) {
        FreeCommentEntity freeCommentEntity = freeCommentRepository.findByFcnum(fcnum);
        return freeCommentEntity;
    }

    public void deleteByFcnum(int fcnum) {
        freeCommentRepository.deleteById(fcnum);
    }
}
