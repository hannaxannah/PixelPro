package com.example.PixelPro.controller;

import com.example.PixelPro.Bean.ClubCommentBean;
import com.example.PixelPro.entity.ClubComment;
import com.example.PixelPro.entity.Club;
import com.example.PixelPro.service.ClubCommentService;
import com.example.PixelPro.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ClubCommentController {


    private final ClubService clubService;
    private final ClubCommentService clubCommentService;


    //댓글 작성
    @PostMapping(value = "/club/writecomment")
    public String writeComment(@RequestParam(value="clnum", required = false) int clnum,
                                        @RequestParam(value="cldetail", required = false) String cldetail,
                                        Model model){

        Club club = clubService.findByClnum(clnum);

        ClubCommentBean clubCommentBean = new ClubCommentBean();

        clubCommentBean.setClnum(clnum);
        clubCommentBean.setMbnum(club.getMbnum());
        clubCommentBean.setCldetail(cldetail);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        clubCommentBean.setCldate(now.format(dateTimeFormatter));

        List<ClubComment> commentlists = clubCommentService.getAllCommentLists(clnum);

        if(commentlists.size() == 0 ) {
            //첫 댓글의 fcstep = 0
            clubCommentBean.setClstep(0);
        }
        else {
            //앞 댓글의 fcstep + 1
            Integer beforeClstep = clubCommentService.beforeClstep(clnum);
            clubCommentBean.setClstep(beforeClstep+1);
        }

        clubCommentBean.setCllevel(0);

        ClubComment clubComment = ClubComment.insertClubComment(clubCommentBean);
        clubCommentService.saveClubComment(clubComment);

        return "redirect:/club/detail?clnum=" + clnum;
    }


    /*답글작성*/
    @PostMapping(value = "/club/replycomment")
    public String replyComment(@RequestParam(value = "clnum", required = false) int clnum,
                                        @RequestParam(value = "conum", required = false) int conum,
                                        @RequestParam(value = "cldetail", required = false) String cldetail,
                                        Model model) {

        ClubComment conumEntity = clubCommentService.findByConum(conum);

        ClubCommentBean clubCommentBean = new ClubCommentBean();
        clubCommentBean.setClnum(clnum);
        clubCommentBean.setMbnum(conumEntity.getMbnum());
        clubCommentBean.setCldetail(cldetail);
        clubCommentBean.setClstep(conumEntity.getClstep());
        clubCommentBean.setCllevel(conumEntity.getCllevel()+1);

        Integer beforeCllevel = clubCommentService.beforeCllevel(conumEntity.getClstep());

        if(beforeCllevel == 0 ) {
            //답글이 안달린 댓글의 fclevel = 0
            clubCommentBean.setCllevel(conumEntity.getCllevel()+1);
        }
        else {
            //댓글의 값이 가장 큰 fclevel + 1
            clubCommentBean.setCllevel(beforeCllevel+1);
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        clubCommentBean.setCldate(now.format(dateTimeFormatter));

        ClubComment clubComment = ClubComment.insertClubComment(clubCommentBean);
        clubCommentService.saveClubComment(clubComment);

        return "redirect:/club/detail?clnum=" + clnum;
    }


    /*댓글 & 답글 삭제*/
    @GetMapping(value = "club/deletecomment")
    public String delete(@RequestParam("conum") int conum,
                         @RequestParam("clnum") int clnum){

        ClubComment conumEntity = clubCommentService.findByConum(conum);
        if(conumEntity.getCllevel() == 0) {
            conumEntity.setCldetail("삭제된 댓글입니다.");
            clubCommentService.saveClubComment(conumEntity);
        }
        else {
            clubCommentService.deleteByConum(conum);
        }

        return "redirect:/club/detail?clnum=" + clnum;
    }
}
