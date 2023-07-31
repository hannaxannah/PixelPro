package com.example.PixelPro.controller;

import com.example.PixelPro.Bean.PayStubBean;
import com.example.PixelPro.Bean.ResignBean;
import com.example.PixelPro.Bean.SalaryBean;
import com.example.PixelPro.entity.SalaryEntity;
import com.example.PixelPro.service.PayStubService;
import com.example.PixelPro.service.ResignService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ResignController {

    private final ResignService resignService;

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
