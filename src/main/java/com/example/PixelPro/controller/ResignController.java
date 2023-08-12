package com.example.PixelPro.controller;

import com.example.PixelPro.Bean.PayStubBean;
import com.example.PixelPro.Bean.ResignBean;
import com.example.PixelPro.Bean.SalaryBean;
import com.example.PixelPro.entity.Member;
import com.example.PixelPro.entity.PayStubEntity;
import com.example.PixelPro.entity.ResignEntity;
import com.example.PixelPro.entity.SalaryEntity;
import com.example.PixelPro.service.MemberService;
import com.example.PixelPro.service.PayStubService;
import com.example.PixelPro.service.ResignService;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ResignController {

    private final ResignService resignService;
    private final MemberService memberService;

    /* 퇴직금(개인) 추가 폼*/
    @GetMapping(value = "resignInsert/{mbnum}")
    public String insertGet(@PathVariable("mbnum") int mbnum,Model model){
        model.addAttribute("resignBean",new ResignBean());
        model.addAttribute("mbnum", mbnum);
        return "/resign/servpayInsert";
    }

    /*개인퇴직금 목록 -> insert -> 전체 퇴직 목록*/
    @PostMapping(value = "resignInsert/{mbnum}")
    public String insertPost(@Valid ResignBean resignBean, @PathVariable("mbnum") int mbnum, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("resignBean", resignBean);
            model.addAttribute("mbnum", mbnum);
            System.out.println("에러발생함(insert실패)");
            return "/resign/servpayInsert";
        }

        ResignEntity resign = ResignEntity.insertResign(resignBean);
        resignService.saveResign(resign);
        System.out.println("insert 성공");
        return "/resign/servpayAllList";
    }

    /*개인 퇴직금 목록*/
    @GetMapping(value = "resign/oneList")
    public String selectOne(HttpSession session, HttpServletResponse response, Model model) throws IOException  {
        //System.out.println("mbnum: " + mbnum);

        response.setContentType("text/html; charset=UTF-8");
        Member member = (Member)session.getAttribute("loginInfo");
        if(member == null){
            session.setAttribute("destination", "redirect:/mail/inbox");
            response.getWriter().print("<script>alert('로그인이 필요합니다.');location.href='/login'</script>");
            response.getWriter().flush();
        }


        List<Member> memberBean = memberService.findByOrderByMbnumDesc();
        //ResignEntity resign = resignService.getResignByMbnum(mbnum);

//        if (resign.getSevpay()== null) {
//            return "/resign/servpayList/" + mbnum;
//        }

        model.addAttribute("member", memberBean);
        return "resign/servpayList";

    }

    /*전체 퇴직금 목록*/
    @GetMapping(value = "resign/resignList")
    public String selectAll(HttpSession session, HttpServletResponse response, Model model) throws IOException  {

        response.setContentType("text/html; charset=UTF-8");
        Member member = (Member)session.getAttribute("loginInfo");
        if(member == null){
            session.setAttribute("destination", "redirect:/mail/inbox");
            response.getWriter().print("<script>alert('로그인이 필요합니다.');location.href='/login'</script>");
            response.getWriter().flush();
        }


        List<ResignEntity> resign = resignService.findByOrderBySevpayDesc();

        model.addAttribute("resign", resign);
        return "/resign/servpayAllList";
    }

    /*퇴직명세서*/
    @GetMapping(value = "resign/retireState/{sevpay}")
    public String retireStatement(@PathVariable("sevpay") int sevpay, HttpSession session,
                                  HttpServletResponse response, Model model) throws IOException  {

        response.setContentType("text/html; charset=UTF-8");
        Member member = (Member)session.getAttribute("loginInfo");

        System.out.println("sevpay:" + sevpay);
        ResignEntity resign = resignService.getResignByServpay(sevpay);

        if (resign.getSevpay()== null) {
            return "/resign/paystubInsert/" + sevpay;
        }

//        System.out.println("퇴직명세서 멤버번호"+member.getMbnum());
//        System.out.println("퇴직명세서 이름"+member.getMbname());

        System.out.println("resign Sevpay: " + resign.getSevpay());
        System.out.println("Payment: " + resign.getPayment());
        System.out.println("Thrday: " + resign.getThrday());
        System.out.println("Oneavgpay: " + resign.getOneavgpay());

        model.addAttribute("resignBean", resign);
        model.addAttribute("member", member);
        return "/resign/retirestatement";
    }

    /*csv*/
    @GetMapping("resign/oneservListCvs")
    public void oneListCvs(HttpServletResponse response) throws IOException {
        // 파일 이름 설정
        String encodedFileName = URLEncoder.encode("개인퇴직금.csv", StandardCharsets.UTF_8);
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + encodedFileName);

        List<Member> member = memberService.findByOrderByMbnumDesc();

        // CSV 데이터 생성 (여기서는 가상의 데이터를 생성하겠습니다)
        List<String[]> csvData = new ArrayList<>();
        csvData.add(new String[]{"사원번호", "성명", "부서", "직급", "입사일","근무상태","퇴사일"});
        for (Member m : member) {
            csvData.add(
                    new String[]{String.valueOf(m.getMbnum()), m.getMbname(), m.getDept(), m.getMblevel().toString(),
                            m.getMbstartdate().toString(),
                            m.getMstate().toString(),
                            m.getMbenddate().toString()}
            );
        }

        // CSV 파일 작성
        OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream(),
                StandardCharsets.UTF_8);
        writer.write("\uFEFF");

        CSVWriter csvWriter = new CSVWriter(writer);
        csvWriter.writeAll(csvData);
        csvWriter.close();
        writer.close();
    }

    /*선택 삭제*/
    @PostMapping(value = "resign/checkDelete")
    public String chkDelete(int[] rowcheck) {
        List<Integer> delList = new ArrayList<Integer>();
        for (int i = 0; i < rowcheck.length; i++) {
            System.out.println("servpay : " + rowcheck[i]);
            delList.add(rowcheck[i]);
        }
        resignService.deleteAllByMbnum(delList);
        return "resign/servpayList";
    }


}
