package com.example.PixelPro.controller;

import com.example.PixelPro.Bean.ClubBean;
import com.example.PixelPro.Bean.MemberBean;
import com.example.PixelPro.entity.*;
import com.example.PixelPro.service.ClubCommentService;
import com.example.PixelPro.service.ClubService;
import com.example.PixelPro.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;
    private final MemberService memberService;
    private final ClubCommentService clubCommentService;


    /*목록*/
    @GetMapping({"/club/list"})
    public String gotoFreeBoard(HttpSession session, HttpServletResponse response,
                                Model model, @PageableDefault(page=0, size = 7, sort = {"cldate","clnum"}, direction = Sort.Direction.DESC) Pageable pageable) throws IOException {

        response.setContentType("text/html; charset=UTF-8");
        Member member = (Member)session.getAttribute("loginInfo");
        if(member == null){
            session.setAttribute("destination", "redirect:/club/list");
            response.getWriter().print("<script>alert('로그인이 필요합니다.');location.href='/login'</script>");
            response.getWriter().flush();
        }

        Page<Club> list = clubService.findByOrderByCldateDesc(pageable);

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);


        model.addAttribute("club", clubService.findByOrderByCldateDesc(pageable));


        return "club/list";
    }


    /*글쓰기 버튼 클릭시*/
    @GetMapping(value="/club/insert")
    public String insertGet(Model model,HttpSession session){

        model.addAttribute("clubBean", new ClubBean());
        return "/club/insert";
    }

    /*게시판글 등록시*/
    @PostMapping(value="/club/insert")
    public String insertPost(@Valid ClubBean clubBean,
                             BindingResult bindingResult,Model model) {


        if (bindingResult.hasErrors()) {
            model.addAttribute("clubBean", clubBean);
            return "/club/insert";
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        clubBean.setCldate(now.format(dateTimeFormatter));

        Club club = Club.insertClub(clubBean);
        clubService.saveClub(club);


        if(!clubBean.getCfilename().equals("")){
            String uploadPath = "C:\\PixelPro\\src\\main\\resources\\clubFile";
            File destination = new File(uploadPath + File.separator + clubBean.getUpload().getOriginalFilename());
            MultipartFile multi =  clubBean.getUpload();
            try {
                multi.transferTo(destination);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e){

            }
        }

        return "redirect:/club/list";
    }


    /*글 상세 내용*/
    @GetMapping(value = "/club/detail")
    public String detailClub(Model model, @RequestParam("clnum") int clnum){

        Club club = clubService.findByClnum(clnum);

       /* club.setClview(club.getClview()+1);*/
        clubService.saveClub(club);

        List<ClubComment> commentlists = clubCommentService.getAllCommentLists(clnum);
        System.out.println("commentlists.size:"+commentlists.size());

        model.addAttribute("club", club);
        model.addAttribute("comment", commentlists);
        return "club/detail";
    }

    /* 파일 다운 처리 */
    @RequestMapping("/download/club/{cfilename}")
    public ResponseEntity<Resource> fileDownload(@PathVariable("cfilename") String cfilename) throws IOException {
        File file = new File("C:/PixelPro/src/main/resources/clubFile/" + cfilename);
        Path path = Paths.get("C:/");

        if (!file.exists()) {
            // 파일이 존재하지 않을 경우,
            return ResponseEntity.notFound().build();
        }

        Resource resource = new InputStreamResource(Files.newInputStream(file.toPath()));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(resource);
    }



    /*수정버튼*/
    @GetMapping(value = "/club/update/{clnum}")
    public String updateGet(@PathVariable("clnum") int clnum, Model model){
        Club club = clubService.findByClnum(clnum);
        model.addAttribute("clubBean", club);

        return "/club/update";
    }


    /*글 수정*/
    @PostMapping(value = "/club/update")
    public String updatePost(@Valid ClubBean club, BindingResult bindingResult,
                             @RequestParam("clnum") int clnum, Model model){

        if (bindingResult.hasErrors()) {
            model.addAttribute("clubBean", club);
            return  "/club/update";
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        club.setCldate(now.format(dateTimeFormatter));


        if(!club.getCfilename().equals("")){
            String uploadPath = "C:\\PixelPro\\src\\main\\resources\\clubFile";
            File destination = new File(uploadPath + File.separator + club.getUpload().getOriginalFilename());
            MultipartFile multi =  club.getUpload();
            try {
                multi.transferTo(destination);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e){

            }
        }

        clubService.saveClub(Club.insertClub(club));

        return "redirect:/club/list";
    }

    /*글 삭제*/
    @PostMapping(value = "/club/delete")
    public String delete(@RequestParam("clnum") int clnum){
        clubService.deleteByClnum(clnum);
        return "redirect:/club/list";
    }




}