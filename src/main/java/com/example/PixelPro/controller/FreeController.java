package com.example.PixelPro.controller;

import com.example.PixelPro.Bean.FreeBean;
import com.example.PixelPro.entity.FreeCommentEntity;
import com.example.PixelPro.entity.FreeEntity;
import com.example.PixelPro.entity.Member;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class FreeController {

    /* 커뮤니티 - 익명게시판 */

    private final FreeService freeService;
    private final FreeCommentService freeCommentService;
    private final String rootPath = System.getProperty("users.pc.IdealProjects.PixelPro");

    //list 글 목록
    @GetMapping(value = "community/free/list")
    public String gotoFreeBoard(
            @RequestParam(value="fcategory", required=false) String fcategory,
            @RequestParam(value="keyword", required=false) String keyword,
            @RequestParam(value="pageNumber", required=false) Integer pageNumber,
            Model model, HttpSession session, HttpServletResponse response) throws IOException {

        Member member = (Member)session.getAttribute("loginInfo");
        if(member == null){
            session.setAttribute("destination", "redirect:/community/free/list");

            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().print("<script>alert('로그인이 필요합니다.');location.href='/login'</script>");
            response.getWriter().flush();
        }

        System.out.println(fcategory+"/"+keyword);

        Map<String, String> map = new HashMap<>();
        map.put("fcategory", fcategory);
        map.put("keyword", "%"+keyword+"%");

        if(pageNumber == null) {
            pageNumber = 1;
        }

        Page<FreeEntity> freeBoardList = freeService.getListsBySearch(map, pageNumber-1);
        int totalCount = Long.valueOf(freeBoardList.getTotalElements()).intValue();
        int totalPages = Long.valueOf(freeBoardList.getTotalPages()).intValue();

        List<FreeEntity> lists = freeBoardList.getContent();

        /*
        //Map<Integer, Integer> comments = new HashMap<>();
        for(FreeEntity freeEntity : lists) {
            //List<FreeCommentEntity> commentlists = freeCommentService.getAllCommentLists(freeEntity.getFnum());
            //comments.put(freeEntity.getFnum(), commentlists.size());
        }
        */

        model.addAttribute("lists", lists);
        //model.addAttribute("comments", comments);
        model.addAttribute("pageNumber",pageNumber);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalCount", totalCount);

        return "free/list";
    }

    //write 글 작성
    @GetMapping(value = "community/free/write")
    public String gotoWriteFreeBoard(Model model, HttpSession session) {
        //아이디 정보 넘겨오기
        Integer mbnum = (Integer)session.getAttribute("mbnum");
        //System.out.println("mbnum:"+mbnum);
        //아이디 정보 보내기
        FreeBean freeBean = new FreeBean();
        freeBean.setMbnum(mbnum);
        model.addAttribute("freeBean", freeBean);
        return "free/write";
    }

    @PostMapping(value = "community/free/write")
    public String writeFreeBoard(@Valid FreeBean freeBean,
                                 BindingResult bindingResult, Model model) throws IOException {

        if (bindingResult.hasErrors()) {
            model.addAttribute("freeBean", freeBean);
            return  "free/write";
        }

        //회원번호 임시 저장
        //freeBean.setMbnum(12345);

        freeBean.setFcount(0);

        // 첨부파일 처리
        if(freeBean.getUploadFilename() != null) {
            MultipartFile multipartFile = freeBean.getUploadFilename();

            String originalFilename = multipartFile.getOriginalFilename();
            System.out.println("fileName:"+originalFilename);

            // 작성자가 업로드한 파일명 -> 서버 내부에서 관리하는 파일명
            // 파일명을 중복되지 않게끔 UUID로 정하고 ".확장자"는 그대로
            int pos = originalFilename.lastIndexOf(".");
            String storeFilename = UUID.randomUUID() + "." + originalFilename.substring(pos + 1);

            // 파일을 저장하는 부분 -> 파일경로 + storeFilename 에 저장
            String uploadPath = "C:\\Users\\pc\\IdeaProjects\\PixelPro\\src\\main\\resources\\static\\freeSecondhandFiles";
            //String uploadPath = "C:PixelPro\\src\\main\\resources\\static\\freeSecondhandFiles";
            File destination = new File(uploadPath + File.separator + storeFilename);
            multipartFile.transferTo(destination);

            freeBean.setFfile(originalFilename);
            freeBean.setStorefilename(storeFilename);
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        freeBean.setFdate(now.format(dateTimeFormatter));

        FreeEntity freeEntity = FreeEntity.insertFreeBoard(freeBean);
        freeService.saveFree(freeEntity);

        return "redirect:/community/free/list";
    }

    //detail 글 상세 내용
    @GetMapping(value = "community/free/detail")
    public String detailFreeBoard(Model model, @RequestParam("fnum") int fnum,
                                  HttpSession session){
        //회원번호 가져오기
        Integer mbnum = (Integer)session.getAttribute("mbnum");

        //번호가 fnum인 data 가져오기
        FreeEntity freeEntity = freeService.findByFnum(fnum);

        //조회수 + 1
        freeEntity.setFcount(freeEntity.getFcount()+1);
        freeService.saveFree(freeEntity);

        //댓글 전체 가져오기
        List<FreeCommentEntity> commentlists = freeCommentService.getAllCommentLists(fnum);
        System.out.println("commentlists.size:"+commentlists.size());

        model.addAttribute("mbnum", mbnum);
        model.addAttribute("free", freeEntity);
        model.addAttribute("comment", commentlists);
        return "free/detail";
    }

    //update 글 수정
    @GetMapping(value = "community/free/update")
    public String gotoUpdateFreeBoard(@RequestParam("fnum") int fnum, Model model) {
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
