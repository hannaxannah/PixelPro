package com.example.PixelPro.controller;

import com.example.PixelPro.Bean.GapprovalBean;
import com.example.PixelPro.entity.Gapproval;
import com.example.PixelPro.entity.Member;
import com.example.PixelPro.service.CommuteService;
import com.example.PixelPro.service.GapprovalService;
import com.example.PixelPro.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class GapprovalController {
    private final GapprovalService gapprovalService;
    private final MemberService memberService;

    @GetMapping(value = "/approval/gapprovalList")
    public String select(Model model,HttpSession session) {
        Member member = (Member) session.getAttribute("loginInfo");
        if(member == null){
            return "redirect:/login";
        }
        List<Gapproval> gapprovalList = gapprovalService.findByGwmbnumOrderByGanumDesc(member.getMbnum());
        model.addAttribute("gapprovalList",gapprovalList);
        return "/approval/gapprovalList";
    }

    @GetMapping(value = "/approval/gapprovalToMeList")
    public String selectToMe(Model model,HttpSession session) {
        Member member = (Member) session.getAttribute("loginInfo");
        if(member == null){
            return "redirect:/login";
        }
        List<Gapproval> gapprovalList = gapprovalService.findByGhmbnumOrderByGanumDesc(member.getMbnum());
        model.addAttribute("gapprovalList",gapprovalList);
        return "/approval/gapprovalToMeList";
    }

    @GetMapping(value = "/approval/gapprovalInsert")
    public String gapprovalInsert(Model model, HttpSession session) {
        Member member = (Member) session.getAttribute("loginInfo");
        if(member == null){
            return "redirect:/login";
        }
        String text = "<table style=\"border-collapse: collapse; width: 100%; height: 364.68px;\" border=\"1\"><colgroup><col style=\"width: 50.0429%;\"><col style=\"width: 49.9571%;\"></colgroup>\n" +
                "                    <tbody>\n" +
                "                        <tr style=\"height: 0.5px;\">\n" +
                "                            <td style=\"height: 342.281px; text-align: center;\" colspan=\"2\" rowspan=\"2\">\n" +
                "                                <p><span style=\"font-size: 36pt;\">(제목)</span></p>\n" +
                "                                <hr>\n" +
                "                                <p style=\"text-align: right;\"><span style=\"font-size: 36pt;\"><span style=\"font-size: 18pt;\">(작성자) (직급)</span></span></p>\n" +
                "                                <p style=\"text-align: justify;\"><span style=\"font-size: 12pt;\">(내용)</span></p>\n" +
                "                                <p style=\"text-align: right;\">&nbsp;</p>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                        <tr style=\"height: 341.781px;\"></tr>\n" +
                "                        <tr style=\"height: 22.3984px;\">\n" +
                "                            <td style=\"height: 22.3984px; text-align: right;\" colspan=\"2\"><span style=\"font-size: 12pt;\">년도 월 일 (요일)</span></td>\n" +
                "                        </tr>\n" +
                "                    </tbody>\n" +
                "                </table>";
        GapprovalBean gapprovalBean = new GapprovalBean();
        gapprovalBean.setGcontent(text);

        List<Member> memberList = memberService.findByOrderByDeptAscMblevelAsc();
        model.addAttribute("gapprovalBean", gapprovalBean);
        model.addAttribute("memberList", memberList);
        model.addAttribute("loginInfo",member);
        return "/approval/gapprovalInsert";
    }

    @PostMapping(value = "/approval/gapprovalInsert")
    public String gapprovalInsert(@Valid GapprovalBean gapprovalBean, BindingResult result, Model model, HttpSession session) {
        Member member = (Member) session.getAttribute("loginInfo");
        List<Member> memberList = memberService.findByOrderByDeptAscMblevelAsc();
        model.addAttribute("memberList", memberList);
        if (member == null) {
            return "redirect:/login";
        } else {
            System.out.println("gcontent\n" + gapprovalBean.getGcontent());
            model.addAttribute("loginInfo",member);

            if (result.hasErrors()) {
                System.out.println("에러 발생");
                model.addAttribute("gapprovalBean", gapprovalBean);
                return "/approval/gapprovalInsert";
            } else {
                if (gapprovalBean.getGsign2() != null) {
                    Member member1 = memberService.findByMbnum(gapprovalBean.getGsign1());
                    Member member2 = memberService.findByMbnum(gapprovalBean.getGsign2());

                    if (Integer.parseInt(member1.getMbaccess()) < Integer.parseInt(member2.getMbaccess())) {
                        model.addAttribute("gapprovalBean", gapprovalBean);
                        model.addAttribute("memberList", memberList);
                        model.addAttribute("comparelevel", "2차 승인자가 1차 승인자보다 직급이 높아야 합니다.");
                        return "/approval/gapprovalInsert";
                    }
                }
                System.out.println(member.getMbsign());
                String signcontent = "<td style=\"border-width: 1px; height: 66.3984px;\"><img width='100%' src='/sign/" + member.getMbsign() + "'></td>";
                System.out.println(signcontent);
                gapprovalBean.setGwmbnum(member.getMbnum());
                gapprovalBean.setGstatus("1차 승인 요청");
                gapprovalBean.setSigncontent(signcontent);
                gapprovalBean.setGhmbnum(gapprovalBean.getGsign1());

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar cal = Calendar.getInstance();
                System.out.println("sdf.format(cal.getTime()) : " + sdf.format(cal.getTime()));
                gapprovalBean.setGdate(sdf.format(cal.getTime()));
                Gapproval gapproval = Gapproval.createGapproval(gapprovalBean);
                gapprovalService.save(gapproval);
                return "redirect:/approval/gapprovalList";

            }
        }
    }


    @GetMapping(value = "/approval/gapprovalDetail")
    public String gapprovalDetail(@RequestParam("ganum") Integer ganum, Model model) {
        Gapproval gapproval = gapprovalService.findByGanum(ganum);
        Member member = memberService.findByMbnum(gapproval.getGwmbnum());
        Member member1 = memberService.findByMbnum(gapproval.getGsign1());
        if(gapproval.getGsign2() != null){
            Member member2 = memberService.findByMbnum(gapproval.getGsign2());
            model.addAttribute("member2",member2);
        }
        model.addAttribute("gapproval", gapproval);
        model.addAttribute("member",member);
        model.addAttribute("member1",member1);
        return "/approval/gapprovalDetail";
    }

}
