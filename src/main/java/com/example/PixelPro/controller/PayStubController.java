package com.example.PixelPro.controller;

import com.example.PixelPro.Bean.PayStubBean;
import com.example.PixelPro.Bean.SalaryBean;
import com.example.PixelPro.entity.PayStubEntity;
import com.example.PixelPro.entity.SalaryEntity;
import com.example.PixelPro.service.PayStubService;
import com.example.PixelPro.service.SalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class PayStubController {

    private final PayStubService payStubService;

    /* 명세서 내용 추가 폼*/
    @GetMapping(value = "paystubInsert")
    public String insertGet(Model model){
        model.addAttribute("paystubBean",new PayStubBean());
        return "/paystubInsert";
    }

    /*명세서 추가*/
    @PostMapping(value="paystubInsert")
    public String insertPost(@Valid PayStubBean paystubBean, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()){
            model.addAttribute("paystubBean", paystubBean);
            System.out.println("에러발생함(insert실패)");
            return "/paystubInsert";
        }

        PayStubEntity paystub = PayStubEntity.insertPaystub(paystubBean);
        payStubService.savePayStub(paystub);
        System.out.println("insert 성공");
        return "/paystub";
    }

}
