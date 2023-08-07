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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;

@Controller
@RequiredArgsConstructor
public class PayStubController {

    private final PayStubService payStubService;

    /* 명세서 내용 추가 폼*/
    @GetMapping(value = "paystubInsert/{snum}")
    public String insertGet(@PathVariable("snum") int snum, Model model) {
        model.addAttribute("paystubBean", new PayStubBean());
        model.addAttribute("snum", snum);
        return "/salary/paystubInsert";
    }

    /*명세서 추가*/
    @PostMapping(value = "paystubInsert/{snum}")
    public @ResponseBody HashMap<String, Object> insertPost(@Valid PayStubBean paystubBean, @PathVariable("snum") int snum) {
        HashMap<String, Object> retMap = new HashMap<>();
        try {
            PayStubEntity paystub = PayStubEntity.insertPaystub(paystubBean);
            payStubService.savePayStub(paystub);
            System.out.println("insert 성공");
            retMap.put("status", "success");
        } catch (Exception e) {
            retMap.put("status", "fail");
        }

        return retMap;
    }
}