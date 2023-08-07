package com.example.PixelPro.controller;

import com.example.PixelPro.Bean.InboxBean;
import com.example.PixelPro.entity.Inbox;
import com.example.PixelPro.entity.Member;
import com.example.PixelPro.service.MailService;
import com.example.PixelPro.mapper.MailMapper;
import com.example.PixelPro.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MailController {

    private final MailService mailservice;
    private final MemberService memberService;
    private final MailMapper mailmapper;

    /* 전체 메일함 */
    @GetMapping("/mail/fullMail")
    public String gotoFullMail(HttpSession session, HttpServletResponse response, Model model,
                               @RequestParam(value = "orderb", required = false) Integer orderb) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        session.setAttribute("MailBar","fullMail");

        Member member = (Member)session.getAttribute("loginInfo");
        if(member == null){
            session.setAttribute("destination", "redirect:/mail/inbox");
            response.getWriter().print("<script>alert('로그인이 필요합니다.');location.href='/login'</script>");
            response.getWriter().flush();
        }
        String email = member.getEmail();
        String trash = "N";

        List<Inbox> inboxList = mailservice.findByRecipientAndTrashOrderBySenddateDesc(email, trash);

        if(orderb != null){
            inboxList = mailservice.findByRecipientAndTrashOrderBySenddate(email, trash);
        }

        int unreadCount = mailservice.countByRecipientAndStatus(member.getEmail(), "unread");
        session.setAttribute("unreadCount",unreadCount);

        model.addAttribute("inboxList",inboxList);

        return "mail/fullMail";
    }

    /* 받은 메일함 */
    @GetMapping("/mail/inbox")
    public String gotoInbox(HttpSession session, HttpServletResponse response, Model model,
                            @RequestParam(value = "orderb", required = false) Integer orderb) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        session.setAttribute("MailBar","inbox");

        Member member = (Member)session.getAttribute("loginInfo");
        if(member == null){
            session.setAttribute("destination", "redirect:/mail/inbox");
            response.getWriter().print("<script>alert('로그인이 필요합니다.');location.href='/login'</script>");
            response.getWriter().flush();
        }

        List<Inbox> inboxList = mailservice.getSendBoxList(member.getEmail());

        if(orderb != null){
            inboxList = mailservice.getSendBoxListO(member.getEmail());
        }
        model.addAttribute("inboxList",inboxList);

        int unreadCount = mailservice.countByRecipientAndStatus(member.getEmail(), "unread");
        session.setAttribute("unreadCount",unreadCount);

        return "mail/inbox";
    }

    /* 보낸 메일함 */
    @GetMapping("/mail/sentMail")
    public String gotoSentMail(HttpSession session, HttpServletResponse response, Model model,
                               @RequestParam(value = "orderb", required = false) Integer orderb) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        session.setAttribute("MailBar","sentMail");

        Member member = (Member)session.getAttribute("loginInfo");
        if(member == null){
            session.setAttribute("destination", "redirect:/mail/sentMail");
            response.getWriter().print("<script>alert('로그인이 필요합니다.');location.href='/login'</script>");
            response.getWriter().flush();
        }

        List<Inbox> inboxList = mailservice.findByEmailOrderBySenddateDesc(member.getEmail());

        if(orderb != null){
            inboxList = mailservice.findByEmailOrderBySenddate(member.getEmail());
        }
        System.out.println("inboxList : " + inboxList);
        model.addAttribute("inboxList",inboxList);

        int unreadCount = mailservice.countByRecipientAndStatus(member.getEmail(), "unread");
        session.setAttribute("unreadCount",unreadCount);

        return "mail/sentMail";
    }

    /* 임시 보관함 */
    @GetMapping("/mail/draftBox")
    public String gotoDraftBox(HttpSession session, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        session.setAttribute("MailBar","draftBox");

        Member member = (Member)session.getAttribute("loginInfo");
        if(member == null){
            session.setAttribute("destination", "redirect:/mail/draftBox");
            response.getWriter().print("<script>alert('로그인이 필요합니다.');location.href='/login'</script>");
            response.getWriter().flush();
        }

        int unreadCount = mailservice.countByRecipientAndStatus(member.getEmail(), "unread");
        session.setAttribute("unreadCount",unreadCount);

        return "mail/draftBox";
    }

    /* 내게 쓴 메일함 */
    @GetMapping("/mail/mailToMe")
    public String gotoMailToMel(HttpSession session, HttpServletResponse response, Model model,
                                @RequestParam(value = "orderb", required = false) Integer orderb) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        session.setAttribute("MailBar","mailToMe");

        Member member = (Member)session.getAttribute("loginInfo");
        if(member == null){
            session.setAttribute("destination", "redirect:/mail/mailToMe");
            response.getWriter().print("<script>alert('로그인이 필요합니다.');location.href='/login'</script>");
            response.getWriter().flush();
        }
        List<Inbox> inboxList = mailservice.getToMeList(member.getEmail());

        if(orderb != null){
            inboxList = mailservice.getToMeListAsc(member.getEmail());
        }

        int unreadCount = mailservice.countByRecipientAndStatus(member.getEmail(), "unread");
        session.setAttribute("unreadCount",unreadCount);

        model.addAttribute("inboxList",inboxList);

        return "mail/mailToMe";
    }

    /* 휴지통 */
    @GetMapping("/mail/trashCan")
    public String gotoTrashCan(HttpSession session, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        session.setAttribute("MailBar","trashCan");

        Member member = (Member)session.getAttribute("loginInfo");
        if(member == null){
            session.setAttribute("destination", "redirect:/mail/trashCan");
            response.getWriter().print("<script>alert('로그인이 필요합니다.');location.href='/login'</script>");
            response.getWriter().flush();
        }

        return "mail/trashCan";
    }
    
    /* 안읽음 */
    @GetMapping("/mail/unreadMail")
    public String gotoUnreadMail(HttpSession session, HttpServletResponse response, Model model,
                                 @RequestParam(value = "orderb", required = false) Integer orderb) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        session.setAttribute("MailBar","unreadMail");

        Member member = (Member)session.getAttribute("loginInfo");
        if(member == null){
            session.setAttribute("destination", "redirect:/mail/unreadMail");
            response.getWriter().print("<script>alert('로그인이 필요합니다.');location.href='/login'</script>");
            response.getWriter().flush();
        }

        List<Inbox> inboxList = mailservice.findByRecipientAndStatusOrderBySenddateDesc(member.getEmail(), "unread");

        if(orderb != null){
            inboxList = mailservice.findByRecipientAndStatusOrderBySenddate(member.getEmail(), "unread");
        }

        model.addAttribute("inboxList",inboxList);

        int unreadCount = mailservice.countByRecipientAndStatus(member.getEmail(), "unread");
        session.setAttribute("unreadCount",unreadCount);
        System.out.println("unreadCount : " + unreadCount);

        return "mail/unreadMail";
    }

    /* 중요 */
    @GetMapping("/mail/scrapMail")
    public String gotoScrapMail(HttpSession session, HttpServletResponse response, Model model,
                                @RequestParam(value = "orderb", required = false) Integer orderb) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        session.setAttribute("MailBar","scrapMail");

        Member member = (Member)session.getAttribute("loginInfo");
        if(member == null){
            session.setAttribute("destination", "redirect:/mail/scrapMail");
            response.getWriter().print("<script>alert('로그인이 필요합니다.');location.href='/login'</script>");
            response.getWriter().flush();
        }

        List<Inbox> inboxList = mailservice.findByRecipientAndImpoOrderBySenddateDesc(member.getEmail(), 1);

        if(orderb != null){
            inboxList = mailservice.findByRecipientAndImpoOrderBySenddate(member.getEmail(), 1);
        }

        model.addAttribute("inboxList",inboxList);

        return "mail/scrapMail";
    }

    /* 첨부 */
    @GetMapping("/mail/clipMail")
    public String gotoClipMail(HttpSession session, HttpServletResponse response, Model model,
                               @RequestParam(value = "orderb", required = false) Integer orderb) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        session.setAttribute("MailBar","clipMail");

        Member member = (Member)session.getAttribute("loginInfo");
        if(member == null){
            session.setAttribute("destination", "redirect:/mail/clipMail");
            response.getWriter().print("<script>alert('로그인이 필요합니다.');location.href='/login'</script>");
            response.getWriter().flush();
        }

        List<Inbox> inboxList = mailservice.findByRecipientAndAttachIsNotNullOrderBySenddateDesc(member.getEmail());

        if(orderb != null){
            inboxList = mailservice.findByRecipientAndAttachIsNotNullOrderBySenddate(member.getEmail());
        }

        model.addAttribute("inboxList",inboxList);

        return "mail/clipMail";
    }

    /* 메일 쓰기 */
    @GetMapping("/mail/send")
    public String gotoSend(HttpSession session, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        session.setAttribute("MailBar","send");

        Member member = (Member)session.getAttribute("loginInfo");
        if(member == null){
            session.setAttribute("destination", "redirect:/mail/send");
            response.getWriter().print("<script>alert('로그인이 필요합니다.');location.href='/login'</script>");
            response.getWriter().flush();
        }

        return "mail/send";
    }


    @PostMapping("/mail/send")
    public String sendMail(InboxBean inboxBean, Model model) {
        // 메일번호,  첨부파일, 휴지통 N, 보낸날짜, 상태, 중요메일, 메일그룹, 메일순서
        System.out.println("title" + inboxBean.getItitle());
        if(inboxBean.getItitle().equals("")){
            inboxBean.setItitle("(제목 없음)");
        }

        System.out.println("orgi : " + inboxBean.getOriginal());
        System.out.println("upl : " + inboxBean.getUpload());
        System.out.println("att : " + inboxBean.getAttach());
        if(inboxBean.getOriginal() == null){
            inboxBean.setOriginal("");
        }
        if(!inboxBean.getOriginal().equals("") && inboxBean.getAttach().equals("")){
            inboxBean.setAttach(inboxBean.getOriginal());
        }

        mailservice.save(Inbox.changEntity(inboxBean));

        int inum = mailservice.maxInum();
        System.out.println("inum : " + inum);
        System.out.println("senddate : " + inboxBean.getSenddate());

        inboxBean.setInum(inum);
        inboxBean.setIref(inum);
        Inbox inboxEntity = Inbox.changEntity(inboxBean);
        mailservice.save(inboxEntity);


        if(inboxBean.getAttach().equals("") && inboxBean.getOriginal().equals("")){
            String uploadPath = "C:\\PixelPro\\src\\main\\resources\\mailAttachFile";
            File destination = new File(uploadPath + File.separator + inboxBean.getUpload().getOriginalFilename());
            MultipartFile multi =  inboxBean.getUpload();
            try {
                multi.transferTo(destination);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e){

            }
        }
        model.addAttribute("inum",inum);
        return "/mail/success";
    }

    @PostMapping("/mail/upload")
    @ResponseBody
    public String update(int inum){
        mailservice.saveDate(inum);
        return "success";
    }

    /* 내게 쓰기 form */
    @GetMapping("/mail/sendToMe")
    public String gotoSendToMe(HttpSession session, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        session.setAttribute("MailBar","sendToMe");

        Member member = (Member)session.getAttribute("loginInfo");
        if(member == null){
            session.setAttribute("destination", "redirect:/mail/sendToMe");
            response.getWriter().print("<script>alert('로그인이 필요합니다.');location.href='/login'</script>");
            response.getWriter().flush();
        }

        return "mail/sendToMe";
    }

    // 내게쓰기
    @PostMapping("/mail/sendToMe")
    public String sendToMeMail(InboxBean inboxBean, Model model) {
        // 메일번호,  첨부파일, 휴지통 N, 보낸날짜, 상태, 중요메일, 메일그룹, 메일순서
        System.out.println("title" + inboxBean.getItitle());
        if(inboxBean.getItitle().equals("")){
            inboxBean.setItitle("(제목 없음)");
        }
        mailservice.save(Inbox.changEntity(inboxBean));

        int inum = mailservice.maxInum();
        System.out.println("inum : " + inum);
        System.out.println("senddate : " + inboxBean.getSenddate());

        inboxBean.setInum(inum);
        inboxBean.setIref(inum);
        Inbox inboxEntity = Inbox.changEntity(inboxBean);
        mailservice.save(inboxEntity);

        if(inboxBean.getAttach() != ""){
            String uploadPath = "C:\\PixelPro\\src\\main\\resources\\mailAttachFile";
            File destination = new File(uploadPath + File.separator + inboxBean.getUpload().getOriginalFilename());
            MultipartFile multi =  inboxBean.getUpload();
            try {
                multi.transferTo(destination);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e){

            }
        }
        model.addAttribute("inum",inum);
        return "/mail/success";
    }

    /* 메일 상세보기 */
    @GetMapping("/mail/detailMail")
    public String gotoDetail(@RequestParam("inum") int inum, Model model, HttpSession session){

        Inbox inbox = mailservice.findByInum(inum);
        Member member = (Member)session.getAttribute("loginInfo");

        // 받은 사람과 로그인자가 동일할때 읽음처리
        // 이미 읽은 기록 있으면 업데이트 노노
        if(inbox.getRecipient().equals(member.getEmail()) && inbox.getReaddate() == null){
            mailmapper.updateInbox(inum);
        }

        // 보낸사람
        Member fromMember = memberService.findByEmail(inbox.getEmail());
        // 받은사람
        Member toMember = memberService.findByEmail(inbox.getRecipient());

        model.addAttribute("inbox", inbox);
        model.addAttribute("fromMember", fromMember);
        model.addAttribute("toMember", toMember);

        int unreadCount = mailservice.countByRecipientAndStatus(member.getEmail(), "unread");
        session.setAttribute("unreadCount",unreadCount);

        return "mail/detailMail";

    }

    /* 파일 다운 처리 */
    @RequestMapping("/download/mail/{attach}")
    public ResponseEntity<Resource> fileDownload(@PathVariable("attach") String attach) throws IOException {
        File file = new File("C:/PixelPro/src/main/resources/mailAttachFile/" + attach);
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

    /* 읽음 -> 안읽음 처리*/
    @PostMapping("/mail/unread")
    public String doUnread(@RequestParam("inum") int inum, HttpSession session){
        Member member = (Member)session.getAttribute("loginInfo");
        mailservice.updateUnread(inum);

        int unreadCount = mailservice.countByRecipientAndStatus(member.getEmail(), "unread");
        session.setAttribute("unreadCount",unreadCount);

        return "unread";
    }

    /* 읽음 -> 안읽음 처리*/
    @PostMapping("/mail/read")
    public String doRead(@RequestParam("inum") int inum, HttpSession session){

        mailmapper.updateInbox(inum);

        return "read";
    }

    /* 즐겨찾기 처리 */
    @PostMapping("/mail/impo")
    @ResponseBody
    public String doImpo(@RequestParam("inum") int inum){
        Inbox inbox = mailservice.findByInum(inum);
        String result = "";

        if(inbox.getImpo() == 0){
            inbox.setImpo(1);
            result = "O";
        }else {
            inbox.setImpo(0);
            result = "X";
        }
        mailservice.save(inbox);

        return result;
    }
    
    // 메일삭제
    @GetMapping("/mail/deleteOne")
    public String delOne(@RequestParam("inum") int inum, HttpSession session){
        mailservice.delOne(inum);

        String gotoPage = (String)session.getAttribute("MailBar");
        return "redirect:/mail/" + gotoPage;
    }
    
    // 메일 선택삭제
    @PostMapping("/mail/deleteAll")
    public String delOne(@RequestParam("row") int[] row, HttpSession session){
        for(int i=0; i<row.length; i++){
            mailservice.delOne(row[i]);
        }
        String gotoPage = (String)session.getAttribute("MailBar");
        return "redirect:/mail/" + gotoPage;
    }

    /* 주소록 */
    @GetMapping("/mail/openAddBook")
    public String openAddBook(Model model){
        List<Member> memberList = memberService.findByOrderByDeptAscMblevelAsc();
        model.addAttribute("memberList",memberList);
        return "mail/openAddBook";
    }

    /* 전달 */
    @GetMapping("/mail/relay")
    public String relay(@RequestParam("inum") int inum, Model model){
        Inbox inbox = mailservice.findByInum(inum);
        model.addAttribute("inbox", inbox);
        return "mail/relay";
    }

}
