package com.example.PixelPro.controller;

import com.example.PixelPro.Bean.PayStubBean;
import com.example.PixelPro.Bean.ResignBean;
import com.example.PixelPro.Bean.SalaryBean;
import com.example.PixelPro.entity.Member;
import com.example.PixelPro.entity.ResignEntity;
import com.example.PixelPro.entity.SalaryEntity;
import com.example.PixelPro.service.MemberService;
import com.example.PixelPro.service.PayStubService;
import com.example.PixelPro.service.ResignService;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ResignController {

    private final ResignService resignService;
    private final MemberService memberService;

   /* 퇴직금(개인) 추가 폼*/
    @GetMapping(value = "resignInsert")
    public String insertGet(Model model){
        model.addAttribute("resignBean",new ResignBean());
        return "/resign/servpayInsert";
    }

    /*개인 퇴직금 목록*/
    @GetMapping(value = "resignOneList")
    public String selectOne(Model model){

        return "/resign/servpayList";
    }

    /*전체 퇴직금 목록*/
    @GetMapping(value = "resignList")
    public String selectAll(Model model){

        return "/resign/servpayAllList";
    }

    /*퇴직명세서*/
    @GetMapping(value = "retirement")
    public String insertGetRetire(Model model){
        return "/resign/retirestatement";
    }

    /*csv*/
    @GetMapping("oneservListCvs")
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


//    수정 폼
//    @GetMapping(value = "resign/update/{snum}")
//    public String updateGet(@PathVariable("snum") int snum, Model model){
//        System.out.println("snum:"+snum);
//        SalaryEntity salary = salaryService.getSalaryBySnum(snum);
//        System.out.println(salary.getSnum());
//        System.out.println(salary.getStitle());
//
//        model.addAttribute("salaryBean",salary);
//        return "/servpayUpdate";
//    }


}
