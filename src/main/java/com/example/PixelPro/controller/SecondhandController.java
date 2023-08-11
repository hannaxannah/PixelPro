package com.example.PixelPro.controller;

import com.example.PixelPro.Bean.SecondhandBean;
import com.example.PixelPro.entity.Member;
import com.example.PixelPro.entity.SecondhandCommentEntity;
import com.example.PixelPro.entity.SecondhandEntity;
import com.example.PixelPro.service.SecondhandCommentService;
import com.example.PixelPro.service.SecondhandService;
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
public class SecondhandController {

    /* 커뮤니티 - 중고장터 */

    private final SecondhandService secondhandService;
    private final SecondhandCommentService secondhandCommentService;

    //list 글 목록
    @GetMapping(value = "community/secondhand/list")
    public String gotoSecondhandBoard(
            @RequestParam(value="shcategory", required=false) String shcategory,
            @RequestParam(value="keyword", required=false) String keyword,
            @RequestParam(value="pageNumber", required=false) Integer pageNumber, Model model,
            HttpSession session, HttpServletResponse response) throws IOException {

        Member member = (Member)session.getAttribute("loginInfo");
        if(member == null){
            session.setAttribute("destination", "redirect:/community/secondhand/list");

            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().print("<script>alert('로그인이 필요합니다.');location.href='/login'</script>");
            response.getWriter().flush();
        }

        Map<String, String> map = new HashMap<>();
        map.put("shcategory", shcategory);
        map.put("keyword", "%"+keyword+"%");

        if(pageNumber == null) {
            pageNumber = 1;
        }

        Page<SecondhandEntity> secondhandBoardList = secondhandService.getListsBySearch(map, pageNumber-1);
        long totalPages = secondhandBoardList.getTotalPages();

        List<SecondhandEntity> lists = secondhandBoardList.getContent();

        model.addAttribute("lists", lists);
        model.addAttribute("pageNumber",pageNumber);
        model.addAttribute("totalPages", totalPages);

        return "secondhand/list";
    }

    //write 글 작성
    @GetMapping(value = "community/secondhand/write")
    public String gotoWriteSecondhandBoard(Model model, HttpSession session){
        //아이디 정보 넘겨오기
        Integer mbnum = (Integer)session.getAttribute("mbnum");

        //아이디 정보 보내기
        SecondhandBean secondhandBean = new SecondhandBean();
        secondhandBean.setMbnum(mbnum);
        model.addAttribute("secondhandBean", secondhandBean);
        return "secondhand/write";
    }

    @PostMapping(value = "community/secondhand/write")
    public String WriteSecondhandBoard(@Valid SecondhandBean secondhandBean,
                                 BindingResult bindingResult, Model model) throws IOException {

        if (bindingResult.hasErrors()) {
            model.addAttribute("secondhandBean", secondhandBean);
            return  "secondhand/write";
        }

        secondhandBean.setShcount(0);

        // 첨부파일 처리
        if(secondhandBean.getUploadFilename() != null) {
            MultipartFile multipartFile = secondhandBean.getUploadFilename();

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

            secondhandBean.setShfile(originalFilename);
            secondhandBean.setStorefilename(storeFilename);
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        secondhandBean.setShdate(now.format(dateTimeFormatter));

        SecondhandEntity secondhandEntity = SecondhandEntity.insertSecondHandBoard(secondhandBean);
        secondhandService.saveSecondhand(secondhandEntity);

        return "redirect:/community/secondhand/list";
    }

    //detail 글 상세 내용
    @GetMapping(value = "community/secondhand/detail")
    public String detailSecondhandBoard(Model model, @RequestParam("shnum") int shnum,
                                        HttpSession session){
        //회원번호 가져오기,
        Integer mbnum = (Integer)session.getAttribute("mbnum");

        //번호가 shnum인 data 가져오기
        SecondhandEntity secondhandEntity = secondhandService.findByShnum(shnum);

        //조회수 + 1
        secondhandEntity.setShcount(secondhandEntity.getShcount()+1);
        secondhandService.saveSecondhand(secondhandEntity);

        //댓글 전체 가져오기
        List<SecondhandCommentEntity> commentlists = secondhandCommentService.getAllCommentLists(shnum);
        System.out.println("commentlists.size:"+commentlists.size());

        model.addAttribute("mbnum", mbnum);
        model.addAttribute("secondhand", secondhandEntity);
        model.addAttribute("comment", commentlists);

        return "secondhand/detail";
    }

    //update 글 수정
    @GetMapping(value = "community/secondhand/update")
    public String gotoUpdateFreeBoard(@RequestParam("shnum") int shnum, Model model){
        //번호가 fnum인 data 가져오기
        SecondhandEntity secondhandEntity = secondhandService.findByShnum(shnum);
        model.addAttribute("secondhandBean", secondhandEntity);
        return "secondhand/update";
    }

    @PostMapping(value = "community/secondhand/update")
    public String updateFreeBoard(@Valid SecondhandBean secondhandBean,
                                  BindingResult bindingResult, Model model) throws IOException {

        if (bindingResult.hasErrors()) {
            model.addAttribute("secondhandBean", secondhandBean);
            return  "secondhand/update";
        }

        // 첨부파일 처리
        if(secondhandBean.getUploadFilename() != null) {
            MultipartFile multipartFile = secondhandBean.getUploadFilename();

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

            secondhandBean.setShfile(originalFilename);
            secondhandBean.setStorefilename(storeFilename);
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        secondhandBean.setShdate(now.format(dateTimeFormatter));

        SecondhandEntity secondhandEntity = SecondhandEntity.insertSecondHandBoard(secondhandBean);
        secondhandService.saveSecondhand(secondhandEntity);

        return "redirect:/community/secondhand/list";
    }

    //delete 글 삭제
    @GetMapping(value = "community/secondhand/delete")
    public String delete(@RequestParam("shnum") int shnum){
        secondhandService.deleteByShnum(shnum);
        return "redirect:/community/secondhand/list";
    }
}
