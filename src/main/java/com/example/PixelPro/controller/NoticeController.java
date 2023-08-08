package com.example.PixelPro.controller;

import com.example.PixelPro.Bean.NoticeBean;
import com.example.PixelPro.entity.Member;
import com.example.PixelPro.entity.Notice;
import com.example.PixelPro.service.MemberService;
import com.example.PixelPro.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
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
                            Model model, @PageableDefault(page=0, size = 5, sort = {"nimportant","nnum"}, direction = Sort.Direction.DESC) Pageable pageable) throws IOException {

        response.setContentType("text/html; charset=UTF-8");
        Member member = (Member)session.getAttribute("loginInfo");
        if(member == null){
            session.setAttribute("destination", "redirect:/notice/list");
            response.getWriter().print("<script>alert('로그인이 필요합니다.');location.href='/login'</script>");
            response.getWriter().flush();
        }

        Page<Notice> list = noticeService.findByOrderByNnumDescNimportantDesc(pageable);

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        model.addAttribute("notice", noticeService.findByOrderByNnumDescNimportantDesc(pageable));
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

        Notice notice = Notice.insertNotice(noticeBean);
        noticeService.saveNotice(notice);

        return "redirect:/notice/list";
    }

    /*상세페이지*/
    @GetMapping(value = "/notice/detail/{nnum}")
    public String detail(@PathVariable("nnum") int nnum, Model model){

        Notice notice = noticeService.getNoticeByNnum(nnum);
        System.out.println(notice.getNnum());
        model.addAttribute("notice",notice);

        /*List<NFile> files = nFileRepository.findAll();
        model.addAttribute("all",files);*/

        return "/notice/detail";
    }

    //첨부파일 다운로드
   /* @GetMapping("/download")
    public ResponseEntity<Resource> download(@ModelAttribute NoticeBean notice) throws IOException {

        Path path = Paths.get(notice.getFilename());
        String contentType = Files.probeContentType(path);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(
                ContentDisposition.builder("attachment")
                        .filename(notice.getFilename(), StandardCharsets.UTF_8)
                        .build());
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);

        Resource resource = new InputStreamResource(Files.newInputStream(path));

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);

    }*/

    /*// 첨부 파일 다운로드
    @GetMapping("/attach/{no}")
    public ResponseEntity<UrlResource> downloadAttach(@PathVariable Long no) throws MalformedURLException {

        NFile file = nFileRepository.findById(no).orElse(null);

        UrlResource resource = new UrlResource("file:" + file.getSavedpath());

        String encodedFileName = UriUtils.encode(file.getOrgnm(), StandardCharsets.UTF_8);

        // 파일 다운로드 대화상자가 뜨도록 하는 헤더를 설정해주는 것
        // Content-Disposition 헤더에 attachment; filename="업로드 파일명" 값을 준다.
        String contentDisposition = "attachment; filename=\"" + encodedFileName + "\"";

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,contentDisposition).body(resource);
    }
*/
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
