package com.example.PixelPro.controller;

import com.example.PixelPro.Bean.SecondhandCommentBean;
import com.example.PixelPro.entity.SecondhandCommentEntity;
import com.example.PixelPro.entity.SecondhandEntity;
import com.example.PixelPro.service.SecondhandCommentService;
import com.example.PixelPro.service.SecondhandService;
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
public class SecondhandCommentController {

    private final SecondhandService secondhandService;
    private final SecondhandCommentService secondhandCommentService;

    //댓글 작성
    @PostMapping(value = "community/secondhand/writecomment")
    public String writeFreeBoardComment(@RequestParam(value="shnum", required = false) int shnum,
                                        @RequestParam(value="shcdetail", required = false) String shcdetail,
                                        Model model){

        SecondhandEntity secondhandEntity = secondhandService.findByShnum(shnum);

        SecondhandCommentBean secondhandCommentBean = new SecondhandCommentBean();

        secondhandCommentBean.setShnum(shnum);
        secondhandCommentBean.setMbnum(secondhandEntity.getMbnum());
        secondhandCommentBean.setShcdetail(shcdetail);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        secondhandCommentBean.setShcdate(now.format(dateTimeFormatter));

        List<SecondhandCommentEntity> commentlists = secondhandCommentService.getAllCommentLists(shnum);

        if(commentlists.size() == 0 ) {
            //첫 댓글의 fcstep = 0
            secondhandCommentBean.setShcstep(0);
        }
        else {
            //앞 댓글의 fcstep + 1
            Integer beforeFcstep = secondhandCommentService.beforeShcstep(shnum);
            secondhandCommentBean.setShcstep(beforeFcstep+1);
        }

        secondhandCommentBean.setShclevel(0);

        SecondhandCommentEntity secondhandCommentEntity = SecondhandCommentEntity.insertSecondhandBoardComment(secondhandCommentBean);
        secondhandCommentService.saveSecondhandComment(secondhandCommentEntity);

        return "redirect:/community/secondhand/detail?shnum=" + shnum;
    }

    //답글 작성
    @PostMapping(value = "community/secondhand/replycomment")
    public String replyFreeBoardComment(@RequestParam(value = "shnum", required = false) int shnum,
                                        @RequestParam(value = "shcnum", required = false) int shcnum,
                                        @RequestParam(value = "shcdetail", required = false) String shcdetail,
                                        Model model) {

        SecondhandCommentEntity shcnumEntity = secondhandCommentService.findByShcnum(shcnum);

        SecondhandCommentBean secondhandCommentBean = new SecondhandCommentBean();
        secondhandCommentBean.setShnum(shnum);
        secondhandCommentBean.setMbnum(shcnumEntity.getMbnum());
        secondhandCommentBean.setShcdetail(shcdetail);
        secondhandCommentBean.setShcstep(shcnumEntity.getShcstep());
        secondhandCommentBean.setShclevel(shcnumEntity.getShclevel()+1);

        Integer beforeFclevel = secondhandCommentService.beforeFclevel(shcnumEntity.getShcstep());

        if(beforeFclevel == 0 ) {
            //답글이 안달린 댓글의 shclevel = 0
            secondhandCommentBean.setShclevel(shcnumEntity.getShclevel()+1);
        }
        else {
            //댓글의 값이 가장 큰 shclevel + 1
            secondhandCommentBean.setShclevel(beforeFclevel+1);
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        secondhandCommentBean.setShcdate(now.format(dateTimeFormatter));

        SecondhandCommentEntity secondhandCommentEntity = SecondhandCommentEntity.insertSecondhandBoardComment(secondhandCommentBean);
        secondhandCommentService.saveSecondhandComment(secondhandCommentEntity);

        return "redirect:/community/secondhand/detail?shnum=" + shnum;
    }

    //댓글 수정

    //답글 수정

    //댓글, 답글 삭제
    @GetMapping(value = "community/secondhand/deletecomment")
    public String delete(@RequestParam("shcnum") int shcnum,
                         @RequestParam("shnum") int shnum){

        SecondhandCommentEntity shcnumEntity = secondhandCommentService.findByShcnum(shcnum);
        if(shcnumEntity.getShclevel() == 0) {
            shcnumEntity.setShcdetail("삭제된 댓글입니다.");
            secondhandCommentService.saveSecondhandComment(shcnumEntity);
        }
        else {
            secondhandCommentService.deleteByShcnum(shcnum);
        }

        return "redirect:/community/secondhand/detail?shnum=" + shnum;
    }

}
