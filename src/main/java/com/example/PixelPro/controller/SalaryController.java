package com.example.PixelPro.controller;

import com.example.PixelPro.Bean.SalaryBean;
import com.example.PixelPro.entity.SalaryEntity;
import com.example.PixelPro.service.SalaryService;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class SalaryController {

    private final SalaryService salaryService;

    /*전체 급여목록*/
    @GetMapping(value = "selectList")
    public String selectAll(Model model) {
//      List<SalaryEntity> salary = salaryService.getSummarySalaryUsers();
        List<Map<String, Long>> salary = salaryService.getSummarySalaryUsers();
        model.addAttribute("salary", salary);
        return "/salary/salaryList";
    }

    /*개인 급여목록*/
    @GetMapping(value = "oneList")
    public String oneList(Model model) {
        List<SalaryEntity> salary = salaryService.findByOrderBySnumDesc();
        model.addAttribute("salary", salary);
        return "salary/salaryOneList";
    }

    /*csv*/
    @GetMapping("oneListCvs")
    public void oneListCvs(HttpServletResponse response) throws IOException {
        // 파일 이름 설정
        String encodedFileName = URLEncoder.encode("개인급여.csv", StandardCharsets.UTF_8);
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + encodedFileName);

        List<SalaryEntity> salary = salaryService.findByOrderBySnumDesc();

        // CSV 데이터 생성 (여기서는 가상의 데이터를 생성하겠습니다)
        List<String[]> csvData = new ArrayList<>();
        csvData.add(new String[]{"번호", "구분", "사원명", "지급총액", "소득세", "지방소득세", "국민연금", "건강보험", "고용보험", "실급여"});
        for (SalaryEntity s : salary) {
            csvData.add(
                    new String[]{String.valueOf(s.getSnum()), s.getSortation(), s.getSname(), s.getAllpayment().toString(),
                            s.getCometax().toString(),
                            s.getTaxes().toString(),
                            s.getPension().toString(),
                            s.getHealth().toString(),
                            s.getEmployment().toString(),
                            s.getActsalary().toString()});
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


    /* 목록 - 다중삭제 */
    @PostMapping(value = "salary/checkDelete")
    public String chkDelete(int[] rowcheck) {
        List<Integer> delList = new ArrayList<Integer>();
        for (int i = 0; i < rowcheck.length; i++) {
            System.out.println("snum : " + rowcheck[i]);
            delList.add(rowcheck[i]);
        }
        salaryService.deleteAllBySnum(delList);
        return "redirect:/oneList";
    }

    /*삭제하기*/
    @GetMapping(value = "salary/delete")
    public String salaryDelete(int snum) {
        System.out.println("snum: " + snum);
        salaryService.salaryDelete(snum);
        return "redirect:/oneList"; //삭제하면 다시 list 화면으로 감
    }

    /*급여추가 폼*/
    @GetMapping(value = "insert")
    public String insertGet(Model model) {
        model.addAttribute("salaryBean", new SalaryBean());
        return "/salary/salaryInsert";
    }

    /*추가하기*/
    @PostMapping(value = "insert")
    public String insertPost(@Valid SalaryBean salaryBean, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("salaryBean", salaryBean);
            System.out.println("에러발생함(insert실패)");
            return "/salary/salaryInsert";
        }

        SalaryEntity salary = SalaryEntity.insertSalary(salaryBean);
        salaryService.saveSalary(salary);
        System.out.println("insert 성공");
        return "redirect:/oneList";
    }

    /*수정폼*/
    @GetMapping(value = "salary/update/{snum}")
    public String updateGet(@PathVariable("snum") int snum, Model model) {
        System.out.println("snum:" + snum);
        SalaryEntity salary = salaryService.getSalaryBySnum(snum);
        System.out.println(salary.getSnum());
        System.out.println(salary.getStitle());

        model.addAttribute("salaryBean", salary);
        return "/salaryUpdate";
    }

    /*수정하기*/
    @PostMapping(value = "/salary/update")
    public String updatePost(@Valid SalaryBean salaryBean, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("salaryBean", salaryBean);
            System.out.println("update 실패");
            return "/salaryUpdate";
        }

        SalaryEntity salary = SalaryEntity.insertSalary(salaryBean);
        salaryService.saveSalary(salary);
        System.out.println("update 성공");
        return "redirect:/";
    }


    /*급여명세서*/
    @GetMapping(value = "salary/payOneStub/{snum}")
    public String payStub(@PathVariable("snum") int snum, Model model) {
        System.out.println("snum:" + snum);
        SalaryEntity salary = salaryService.getSalaryBySnum(snum);
        if (salary.getPayStub()== null) {
            return "redirect:/paystubInsert/" + snum;
        }

        System.out.println("snum: " + salary.getSnum());
        System.out.println("Cometax: " + salary.getCometax());
        System.out.println("Taxes: " + salary.getTaxes());
        System.out.println("Pension: " + salary.getPension());
        System.out.println("Health: " + salary.getHealth());
        System.out.println("Employment: " + salary.getEmployment());
        System.out.println("Actsalary: " + salary.getActsalary());

        model.addAttribute("salaryBean", salary);
        model.addAttribute("paystub", salary.getPayStub());
        return "/salary/paystub";
    }
}
