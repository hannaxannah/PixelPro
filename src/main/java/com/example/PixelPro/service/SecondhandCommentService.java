package com.example.PixelPro.service;

import com.example.PixelPro.entity.FreeCommentEntity;
import com.example.PixelPro.entity.SecondhandCommentEntity;
import com.example.PixelPro.repository.SecondhandCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SecondhandCommentService {

    @Autowired
    private final SecondhandCommentRepository secondhandCommentRepository;

    public void saveSecondhandComment(SecondhandCommentEntity secondhandCommentEntity) {
        secondhandCommentRepository.save(secondhandCommentEntity);
    }

    public List<SecondhandCommentEntity> getAllCommentLists(int shnum) {
        List<SecondhandCommentEntity> commentlists = secondhandCommentRepository.findByShnumOrderByShcstepAscShclevelAsc(shnum);
        return commentlists;
    }

    public Integer beforeShcstep(int shnum) {
        SecondhandCommentEntity secondhandCommentEntity = secondhandCommentRepository.findTopByShnumOrderByShcstepDesc(shnum);
        return secondhandCommentEntity.getShcstep();
    }

    public Integer beforeFclevel(int shcstep) {
        SecondhandCommentEntity secondhandCommentEntity = secondhandCommentRepository.findTopByShcstepOrderByShclevelDesc(shcstep);
        return secondhandCommentEntity.getShclevel();
    }
    public SecondhandCommentEntity findByShcnum(int shcnum) {
        SecondhandCommentEntity secondhandCommentEntity = secondhandCommentRepository.findByShcnum(shcnum);
        return secondhandCommentEntity;
    }

    public void deleteByShcnum(int shcnum) {
        secondhandCommentRepository.deleteById(shcnum);
    }

    public Boolean replyexist(int shcnum) {
        SecondhandCommentEntity secondhandCommentEntity = secondhandCommentRepository.findByShcnum(shcnum);
        List<SecondhandCommentEntity> replyLists = secondhandCommentRepository.findByShcstepAndShnum(secondhandCommentEntity.getShcstep(), secondhandCommentEntity.getShnum());
        System.out.println("replyLists.size:"+replyLists.size());
        if(replyLists.size() > 1) {
            return true;
        }
        else {
            return false;
        }
    }
}
