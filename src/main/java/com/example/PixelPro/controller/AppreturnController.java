package com.example.PixelPro.controller;

import com.example.PixelPro.Bean.AppreturnBean;
import com.example.PixelPro.Bean.GapprovalBean;
import com.example.PixelPro.entity.Appreturn;
import com.example.PixelPro.entity.Gapproval;
import com.example.PixelPro.entity.Member;
import com.example.PixelPro.service.AppreturnService;
import com.example.PixelPro.service.GapprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class AppreturnController {
    private final AppreturnService appreturnService;
    private final GapprovalService gapprovalService;
    @GetMapping(value = "/approval/gapprovalAppreturn")
    public String gapprovalAppreturn(@RequestParam("ganum") Integer ganum, Model model, HttpSession session, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        Member member = (Member) session.getAttribute("loginInfo");
        if(member == null){
            session.setAttribute("destination", "redirect:/approval/gapprovalDetail?ganum=" + ganum);
            response.getWriter().print("<script>alert('로그인이 필요합니다.');location.href='/login'</script>");
            response.getWriter().flush();
        }

        AppreturnBean appreturnBean = new AppreturnBean();
        appreturnBean.setGanum(ganum);
        model.addAttribute("appreturnBean", appreturnBean);
        return "/approval/gapprovalAppreturn";
    }

    @PostMapping(value = "/approval/gapprovalAppreturn")
    public String gapprovalAppreturn(@Valid AppreturnBean appreturnBean, BindingResult result, Model model, HttpSession session, HttpServletResponse response) throws IOException{
        response.setContentType("text/html; charset=UTF-8");
        Member member = (Member) session.getAttribute("loginInfo");
        if(member == null){
            session.setAttribute("destination", "redirect:/approval/gapprovalDetail?ganum=" + appreturnBean.getGanum());
            response.getWriter().print("<script>alert('로그인이 필요합니다.');location.href='/login'</script>");
            response.getWriter().flush();
            return "/approval/gapprovalAppreturn";
        }
        else {

            if (result.hasErrors()) {
                System.out.println("에러 발생");
                model.addAttribute("appreturnBean", appreturnBean);
                return "/approval/gapprovalAppreturn";
            } else {

                System.out.println("appreturnBean : " +appreturnBean.getGanum());
                Gapproval gapproval = gapprovalService.findByGanum(appreturnBean.getGanum());
                appreturnBean.setRsnum(member.getMbnum());
                appreturnBean.setRnum(gapproval.getGwmbnum());

                Appreturn appreturn = Appreturn.createAppreturn(appreturnBean);
                appreturnService.save(appreturn);

                GapprovalBean gapprovalBean = GapprovalBean.createGapprovalBean(gapproval);
                gapprovalBean.setGstatus("반려");
                gapprovalBean.setGhmbnum(-1);

                Gapproval gapprovalSave = Gapproval.createGapproval(gapprovalBean);
                gapprovalService.save(gapprovalSave);

                response.getWriter().print("<script>alert('반려 되었습니다.');opener.parent.location.href='/approval/gapprovalToMeList';window.close();</script>");
                response.getWriter().flush();

                return "";

            }
        }
    }
}
