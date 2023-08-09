package com.example.PixelPro.controller;

import com.example.PixelPro.Bean.ClubBean;
import com.example.PixelPro.entity.ClubComment;
import com.example.PixelPro.entity.Club;
import com.example.PixelPro.service.ClubCommentService;
import com.example.PixelPro.service.ClubService;
import com.example.PixelPro.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;
    private final MemberService memberService;
    private final ClubCommentService clubCommentService;


    /*목록*/
    @GetMapping({"/club/list"})
    public String gotoFreeBoard(
            @RequestParam(value="clcategory", required=false) String clcategory,
            @RequestParam(value="keyword", required=false) String keyword,
            @RequestParam(value="pageNumber", required=false) String pageNumber, Model model){

        System.out.println(clcategory+"/"+keyword);

        Map<String, String> map = new HashMap<>();
        map.put("clcategory", clcategory);
        map.put("keyword", "%"+keyword+"%");

        if(pageNumber == null) {
            pageNumber = "1";
        }

        Page<Club> clubBoardList = clubService.getListsBySearch(map, Integer.valueOf(pageNumber)-1);
        long totalCount = clubBoardList.getTotalElements();
        long totalPages = clubBoardList.getTotalPages();

        List<Club> lists = clubBoardList.getContent();

        Map<Integer, Integer> comments = new HashMap<>();
        for(Club club : lists) {
            List<ClubComment> commentlists = clubCommentService.getAllCommentLists(club.getClnum());
            comments.put(club.getClnum(), commentlists.size());
        }

        model.addAttribute("lists", lists);
        model.addAttribute("comments", comments);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalCount", totalCount);

        return "club/list";
    }


    /*글쓰기 버튼 클릭시*/
    @GetMapping(value="/club/insert")
    public String insertGet(Model model){

        model.addAttribute("clubBean", new ClubBean());
        return "club/insert";
    }

    /*게시판글 등록시*/
    @PostMapping(value="/club/insert")
    public String insertPost(@Valid ClubBean clubBean,
                             BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("clubBean", clubBean);
            return "/club/insert";
        }


        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        clubBean.setCldate(now.format(dateTimeFormatter));

        Club club = Club.insertClub(clubBean);
        clubService.saveClub(club);

        if(!clubBean.getFilename().equals("")){
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

        club.setClview(club.getClview()+1);
        clubService.saveClub(club);

        List<ClubComment> commentlists = clubCommentService.getAllCommentLists(clnum);
        System.out.println("commentlists.size:"+commentlists.size());

        model.addAttribute("club", club);
        model.addAttribute("comment", commentlists);
        return "club/detail";
    }

    /* 파일 다운 처리 */
    @RequestMapping("/download/club/{filename}")
    public ResponseEntity<Resource> fileDownload(@PathVariable("filename") String filename) throws IOException {
        File file = new File("C:/PixelPro/src/main/resources/clubFile/" + filename);
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
    @GetMapping(value = "/club/update")
    public String updateGet(@RequestParam("clnum") int clnum, Model model){

        Club club = clubService.findByClnum(clnum);
        model.addAttribute("clubBean", club);
        return "club/update";
    }


    /*글 수정*/
    @PostMapping(value = "/club/update")
    public String updatePost(@Valid ClubBean clubBean,
                                  BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()) {
            model.addAttribute("clubBean", clubBean);
            return  "club/update";
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        clubBean.setCldate(now.format(dateTimeFormatter));

        Club club = Club.insertClub(clubBean);
        clubService.saveClub(club);

        return "redirect:/club/list";
    }

    /*글 삭제*/
    @GetMapping(value = "/club/delete")
    public String delete(@RequestParam("clnum") int clnum){
        clubService.deleteByClnum(clnum);
        return "redirect:/club/list";
    }




}