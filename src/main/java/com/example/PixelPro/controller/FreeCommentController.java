package com.example.PixelPro.controller;

import com.example.PixelPro.Bean.FreeCommentBean;
import com.example.PixelPro.entity.FreeCommentEntity;
import com.example.PixelPro.entity.FreeEntity;
import com.example.PixelPro.service.FreeCommentService;
import com.example.PixelPro.service.FreeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class FreeCommentController {

    private final FreeService freeService;
    private final FreeCommentService freeCommentService;

    //댓글 작성
    @PostMapping(value = "community/free/writecomment")
    public String writeFreeBoardComment(@RequestParam(value="fnum", required = false) int fnum,
                                 @RequestParam(value="fcdetail", required = false) String fcdetail,
                                 Model model, HttpSession session){

        FreeEntity freeEntity = freeService.findByFnum(fnum);

        FreeCommentBean freeCommentBean = new FreeCommentBean();

        freeCommentBean.setFnum(fnum);

        Integer mbnum = (Integer)session.getAttribute("mbnum");
        freeCommentBean.setMbnum(mbnum);
        freeCommentBean.setFcdetail(fcdetail);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        freeCommentBean.setFcdate(now.format(dateTimeFormatter));

        List<FreeCommentEntity> commentlists = freeCommentService.getAllCommentLists(fnum);

        if(commentlists.size() == 0 ) {
            //첫 댓글의 fcstep = 0
            freeCommentBean.setFcstep(0);
        }
        else {
            //앞 댓글의 fcstep + 1
            Integer beforeFcstep = freeCommentService.beforeFcstep(fnum);
            freeCommentBean.setFcstep(beforeFcstep+1);
        }

        freeCommentBean.setFclevel(0);

        FreeCommentEntity freeCommentEntity = FreeCommentEntity.insertFreeBoardComment(freeCommentBean);
        freeCommentService.saveFreeComment(freeCommentEntity);

        return "redirect:/community/free/detail?fnum=" + fnum;
    }

    //답글 작성
    @PostMapping(value = "community/free/replycomment")
    public String replyFreeBoardComment(@RequestParam(value = "fnum", required = false) int fnum,
                                        @RequestParam(value = "fcnum", required = false) int fcnum,
                                        @RequestParam(value = "fcdetail", required = false) String fcdetail,
                                        Model model, HttpSession session) {

        FreeCommentEntity fcnumEntity = freeCommentService.findByFcnum(fcnum);

        FreeCommentBean freeCommentBean = new FreeCommentBean();
        freeCommentBean.setFnum(fnum);

        Integer mbnum = (Integer)session.getAttribute("mbnum");
        freeCommentBean.setMbnum(mbnum);
        freeCommentBean.setFcdetail(fcdetail);
        freeCommentBean.setFcstep(fcnumEntity.getFcstep());
        freeCommentBean.setFclevel(fcnumEntity.getFclevel()+1);

        Integer beforeFclevel = freeCommentService.beforeFclevel(fcnumEntity.getFcstep());

        if(beforeFclevel == 0 ) {
            //답글이 안달린 댓글의 fclevel = 0
            freeCommentBean.setFclevel(fcnumEntity.getFclevel()+1);
        }
        else {
            //댓글의 값이 가장 큰 fclevel + 1
            freeCommentBean.setFclevel(beforeFclevel+1);
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        freeCommentBean.setFcdate(now.format(dateTimeFormatter));

        FreeCommentEntity freeCommentEntity = FreeCommentEntity.insertFreeBoardComment(freeCommentBean);
        freeCommentService.saveFreeComment(freeCommentEntity);

        return "redirect:/community/free/detail?fnum=" + fnum;
    }

    //댓글, 답글 수정
    @PostMapping(value = "community/free/updatecomment")
    public String updateFreeBoardComment(@RequestParam(value="fnum", required = false) int fnum,
                                         @RequestParam(value="fcnum", required = false) int fcnum,
                                         @RequestParam(value="fcdetail", required = false) String fcdetail,
                                         Model model, HttpSession session){

        FreeCommentEntity fcnumEntity = freeCommentService.findByFcnum(fcnum);
        FreeCommentBean freeCommentBean = new FreeCommentBean();

        freeCommentBean.setFnum(fnum);
        freeCommentBean.setFcnum(fcnum);

        Integer mbnum = (Integer)session.getAttribute("mbnum");
        freeCommentBean.setMbnum(mbnum);
        freeCommentBean.setFcdetail(fcdetail);
        freeCommentBean.setFcstep(fcnumEntity.getFcstep());
        freeCommentBean.setFclevel(fcnumEntity.getFclevel());

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        freeCommentBean.setFcdate(now.format(dateTimeFormatter));

        FreeCommentEntity freeCommentEntity = FreeCommentEntity.insertFreeBoardComment(freeCommentBean);
        freeCommentService.saveFreeComment(freeCommentEntity);

        return "redirect:/community/free/detail?fnum=" + fnum;
    }

    //댓글, 답글 삭제
    @GetMapping(value = "community/free/deletecomment")
    public String delete(@RequestParam("fcnum") int fcnum,
                         @RequestParam("fnum") int fnum){

        FreeCommentEntity fcnumEntity = freeCommentService.findByFcnum(fcnum);
        Boolean replyExistance = freeCommentService.replyexist(fcnum);
        if((fcnumEntity.getFclevel() == 0) && replyExistance) {
            fcnumEntity.setFcdetail("삭제된 댓글입니다.");
            freeCommentService.saveFreeComment(fcnumEntity);
        }
        else {
            freeCommentService.deleteByFcnum(fcnum);
        }

        return "redirect:/community/free/detail?fnum=" + fnum;
    }

}
