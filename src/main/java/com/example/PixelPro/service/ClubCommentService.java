package com.example.PixelPro.service;

import com.example.PixelPro.entity.ClubComment;
import com.example.PixelPro.repository.ClubCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ClubCommentService {

    @Autowired
    private final ClubCommentRepository clubCommentRepository;


    public void saveClubComment(ClubComment clubComment) {
        clubCommentRepository.save(clubComment);
    }

    public List<ClubComment> getAllCommentLists(int clnum) {
        List<ClubComment> commentlists = clubCommentRepository.findByClnumOrderByClstepAscCllevelAsc(clnum);
        return commentlists;
    }

    public Integer beforeClstep(int clnum) {
        ClubComment clubComment = clubCommentRepository.findTopByClnumOrderByClstepDesc(clnum);
        return clubComment.getClstep();
    }

    public Integer beforeCllevel(int clstep) {
        ClubComment clubComment = clubCommentRepository.findTopByClstepOrderByCllevelDesc(clstep);
        return clubComment.getCllevel();
    }
    public ClubComment findByConum(int conum) {
        ClubComment clubComment = clubCommentRepository.findByConum(conum);
        return clubComment;
    }

    public void deleteByConum(int conum) {
        clubCommentRepository.deleteById(conum);
    }
}
