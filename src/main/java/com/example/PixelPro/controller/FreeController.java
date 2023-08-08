package com.example.PixelPro.controller;

import com.example.PixelPro.Bean.FreeBean;
import com.example.PixelPro.entity.FreeCommentEntity;
import com.example.PixelPro.entity.FreeEntity;
import com.example.PixelPro.service.FreeCommentService;
import com.example.PixelPro.service.FreeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class FreeController {

    /* 커뮤니티 - 익명게시판 */

    private final FreeService freeService;
    private final FreeCommentService freeCommentService;

    //list 글 목록
    @GetMapping(value = "community/free/list")
    public String gotoFreeBoard(
            @RequestParam(value="fcategory", required=false) String fcategory,
            @RequestParam(value="keyword", required=false) String keyword,
            @RequestParam(value="pageNumber", required=false) String pageNumber, Model model){

        System.out.println(fcategory+"/"+keyword);

        Map<String, String> map = new HashMap<>();
        map.put("fcategory", fcategory);
        map.put("keyword", "%"+keyword+"%");

        if(pageNumber == null) {
            pageNumber = "1";
        }

        Page<FreeEntity> freeBoardList = freeService.getListsBySearch(map, Integer.valueOf(pageNumber)-1);
        long totalCount = freeBoardList.getTotalElements();
        long totalPages = freeBoardList.getTotalPages();

        List<FreeEntity> lists = freeBoardList.getContent();

        Map<Integer, Integer> comments = new HashMap<>();
        for(FreeEntity freeEntity : lists) {
            List<FreeCommentEntity> commentlists = freeCommentService.getAllCommentLists(freeEntity.getFnum());
            comments.put(freeEntity.getFnum(), commentlists.size());
        }

        model.addAttribute("lists", lists);
        model.addAttribute("comments", comments);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalCount", totalCount);

        return "free/list";
    }

    //write 글 작성
    @GetMapping(value = "community/free/write")
    public String gotoWriteFreeBoard(Model model){
        //아이디 정보 넘겨오기
        //아이디 정보 보내기
        model.addAttribute("freeBean", new FreeBean());
        return "free/write";
    }

    @PostMapping(value = "community/free/write")
    public String writeFreeBoard(@Valid FreeBean freeBean,
                                 BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()) {
            model.addAttribute("freeBean", freeBean);
            return  "free/write";
        }

        //회원번호 임시 저장
        freeBean.setMbnum(12345);

        freeBean.setFcount(0);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        freeBean.setFdate(now.format(dateTimeFormatter));

        FreeEntity freeEntity = FreeEntity.insertFreeBoard(freeBean);
        freeService.saveFree(freeEntity);

        return "redirect:/community/free/list";
    }

    //detail 글 상세 내용
    @GetMapping(value = "community/free/detail")
    public String detailFreeBoard(Model model, @RequestParam("fnum") int fnum){
        //회원번호 가져오기

        //번호가 fnum인 data 가져오기
        FreeEntity freeEntity = freeService.findByFnum(fnum);

        //조회수 + 1
        freeEntity.setFcount(freeEntity.getFcount()+1);
        freeService.saveFree(freeEntity);

        //댓글 전체 가져오기
        List<FreeCommentEntity> commentlists = freeCommentService.getAllCommentLists(fnum);
        System.out.println("commentlists.size:"+commentlists.size());

        model.addAttribute("free", freeEntity);
        model.addAttribute("comment", commentlists);
        return "free/detail";
    }

    //update 글 수정
    @GetMapping(value = "community/free/update")
    public String gotoUpdateFreeBoard(@RequestParam("fnum") int fnum, Model model){
        //번호가 fnum인 data 가져오기
        FreeEntity freeEntity = freeService.findByFnum(fnum);
        model.addAttribute("freeBean", freeEntity);
        return "free/update";
    }

    @PostMapping(value = "community/free/update")
    public String updateFreeBoard(@Valid FreeBean freeBean,
                                 BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()) {
            model.addAttribute("freeBean", freeBean);
            return  "free/update";
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        freeBean.setFdate(now.format(dateTimeFormatter));

        FreeEntity freeEntity = FreeEntity.insertFreeBoard(freeBean);
        freeService.saveFree(freeEntity);

        return "redirect:/community/free/list";
    }

    //delete 글 삭제
    @GetMapping(value = "community/free/delete")
    public String delete(@RequestParam("fnum") int fnum){
        freeService.deleteByFnum(fnum);
        return "redirect:/community/free/list";
    }

}
