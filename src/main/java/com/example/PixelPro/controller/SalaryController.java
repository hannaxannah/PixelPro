package com.example.PixelPro.controller;

import com.example.PixelPro.Bean.SalaryBean;
import com.example.PixelPro.entity.SalaryEntity;
import com.example.PixelPro.service.SalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class SalaryController {

    private final SalaryService salaryService;

    /*전체 급여목록*/
    @GetMapping(value = "selectList")
    public String selectAll(Model model){

        return "/salaryList";
    }

    /*개인 급여목록*/
    @GetMapping(value = "oneList")
    public String oneList(Model model){
        List<SalaryEntity> salary = salaryService.findByOrderBySnumDesc();
        model.addAttribute("salary",salary);
        return "/salaryOneList";
    }

    /* 목록 - 다중삭제 */
    @PostMapping(value = "salary/checkDelete")
    public String chkDelete(int[] rowcheck){
        List<Integer> delList = new ArrayList<Integer>();
        for(int i=0; i<rowcheck.length; i++){
            System.out.println("snum : " + rowcheck[i]);
            delList.add(rowcheck[i]);
        }
        salaryService.deleteAllBySnum(delList);
        return "redirect:/oneList";
    }

    /*삭제하기*/
    @GetMapping(value = "salary/delete")
    public String salaryDelete(int snum){
        System.out.println("snum: "+snum);
        salaryService.salaryDelete(snum);
        return "redirect:/oneList"; //삭제하면 다시 list 화면으로 감
    }

    /*급여추가 폼*/
    @GetMapping(value = "insert")
    public String insertGet(Model model){
        model.addAttribute("salaryBean",new SalaryBean());
        return "/salaryInsert";
    }

    /*추가하기*/
    @PostMapping(value="insert")
    public String insertPost(@Valid SalaryBean salaryBean, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("salaryBean", salaryBean);
            System.out.println("에러발생함(insert실패)");
            return "/salaryInsert";
        }

        SalaryEntity salary = SalaryEntity.insertSalary(salaryBean);
        salaryService.saveSalary(salary);
        System.out.println("insert 성공");
        return "redirect:/";
    }

    /*수정폼*/
    @GetMapping(value = "salary/update/{snum}")
    public String updateGet(@PathVariable("snum") int snum, Model model){
        System.out.println("snum:"+snum);
        SalaryEntity salary = salaryService.getSalaryBySnum(snum);
        System.out.println(salary.getSnum());
        System.out.println(salary.getStitle());

        model.addAttribute("salaryBean",salary);
        return "/salaryUpdate";
    }

    /*수정하기*/
    @PostMapping(value="/salary/update")
    public String updatePost(@Valid SalaryBean salaryBean, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("salaryBean",salaryBean);
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
    public String payStub(@PathVariable("snum") int snum, Model model){
        System.out.println("snum:"+snum);
        SalaryEntity salary = salaryService.getSalaryBySnum(snum);
        System.out.println("snum: "+salary.getSnum());
        System.out.println("Cometax: "+salary.getCometax());
        System.out.println("Taxes: "+salary.getTaxes());
        System.out.println("Pension: "+salary.getPension());
        System.out.println("Health: "+salary.getHealth());
        System.out.println("Employment: "+salary.getEmployment());
        System.out.println("Actsalary: "+salary.getActsalary());

        model.addAttribute("salaryBean",salary);
        return "/paystub";
    }

}
