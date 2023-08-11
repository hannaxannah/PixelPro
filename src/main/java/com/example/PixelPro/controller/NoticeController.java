package com.example.PixelPro.controller;

import com.example.PixelPro.Bean.NoticeBean;
import com.example.PixelPro.entity.Member;
import com.example.PixelPro.entity.Notice;
import com.example.PixelPro.service.MemberService;
import com.example.PixelPro.service.NoticeService;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;
    private final MemberService memberService;

    /*목록*/
    @GetMapping({"/notice/list"})
    public String selectAll(HttpSession session, HttpServletResponse response,
                            Model model, @PageableDefault(page=0, size = 5, sort = {"nimportant","ndate"}, direction = Sort.Direction.DESC) Pageable pageable) throws IOException {

        response.setContentType("text/html; charset=UTF-8");
        Member member = (Member)session.getAttribute("loginInfo");
        if(member == null){
            session.setAttribute("destination", "redirect:/notice/list");
            response.getWriter().print("<script>alert('로그인이 필요합니다.');location.href='/login'</script>");
            response.getWriter().flush();
        }



        Page<Notice> list = noticeService.findByOrderByNimportantDescNdateDesc(pageable);

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        model.addAttribute("notice", noticeService.findByOrderByNimportantDescNdateDesc(pageable));

        return "/notice/list";
    }


    /* 글쓰기버튼 클릭시 */
    @GetMapping(value="/notice/insert")
    public String insertGet(Model model){
        model.addAttribute("noticeBean",new NoticeBean());
        return "/notice/insert";
    }

    /*공지사항 등록시*/
    @PostMapping(value="/notice/insert")
    public String insertPost(@Valid NoticeBean noticeBean,
                             BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("noticeBean", noticeBean);
            return "/notice/insert";
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        noticeBean.setNdate(now.format(dateTimeFormatter));

        Notice notice = Notice.insertNotice(noticeBean);
        noticeService.saveNotice(notice);

        if(!noticeBean.getFilename().equals("")){
            String uploadPath = "C:\\PixelPro\\src\\main\\resources\\noticeFile";
            File destination = new File(uploadPath + File.separator + noticeBean.getUpload().getOriginalFilename());
            MultipartFile multi =  noticeBean.getUpload();
            try {
                multi.transferTo(destination);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e){

            }
        }

        return "redirect:/notice/list";
    }

    /*상세페이지*/
    @GetMapping(value = "/notice/detail/{nnum}")
    public String detail(@PathVariable("nnum") int nnum, Model model){

        Notice notice = noticeService.getNoticeByNnum(nnum);
        System.out.println(notice.getNnum());
        model.addAttribute("notice",notice);


        return "/notice/detail";
    }

    /* 파일 다운 처리 */
    @RequestMapping("/download/notice/{filename}")
    public ResponseEntity<Resource> fileDownload(@PathVariable("filename") String filename) throws IOException {
        File file = new File("C:/PixelPro/src/main/resources/noticeFile/" + filename);
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


    @PostMapping(value = "/notice/delete")
    public String delete(@RequestParam("nnum") int nnum){
        noticeService.noticeDelete(nnum);
        return "redirect:/notice/list";
    }

    @GetMapping(value = "/notice/update/{nnum}")
    public String updateGet(@PathVariable("nnum") int nnum, Model model){
        Notice notice = noticeService.findByNnum(nnum);
        model.addAttribute("noticeBean",notice);

        return "/notice/update";
    }

    @PostMapping(value = "/notice/update")
    public String updatePost(@Valid NoticeBean notice, BindingResult bindingResult,
                             @RequestParam("nnum") int nnum, Model model){

        if (bindingResult.hasErrors()) {
            model.addAttribute("noticeBean", notice);
            return "/notice/update";
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        notice.setNdate(now.format(dateTimeFormatter));


        if(!notice.getFilename().equals("")){
            String uploadPath = "C:\\PixelPro\\src\\main\\resources\\noticeFile";
            File destination = new File(uploadPath + File.separator + notice.getUpload().getOriginalFilename());
            MultipartFile multi =  notice.getUpload();
            try {
                multi.transferTo(destination);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e){

            }
        }

        noticeService.saveNotice(Notice.insertNotice(notice));
        return "redirect:/notice/list";
    }


    @PostMapping(value = "/notice/checkDelete")
    public String chkDelete(@RequestParam("rowcheck") int[] rowcheck){
        List<Integer> delList = new ArrayList<Integer>();
        for(int i=0; i<rowcheck.length; i++){
            System.out.println("num : " + rowcheck[i]);
            delList.add(rowcheck[i]);
        }
        noticeService.deleteAllByNnum(delList);
        return "redirect:/notice/list";
    }

}
