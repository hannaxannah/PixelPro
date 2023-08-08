package com.example.PixelPro.controller;

import com.example.PixelPro.entity.Calendar;
import com.example.PixelPro.service.CalendarService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/calendar")
public class CalendarController {

    @Autowired
    CalendarService calendarService;

    @GetMapping(value = "/main")
    public String mainPage(HttpServletRequest request){


        return "calendar/calendarPage";
    }

    @GetMapping("/event") //ajax 데이터 전송 URL
    @ResponseBody
    public List<Map<String, Object>> getEvent(){
        List<Map<String, Object>> list =  calendarService.getCalendarAll();
        JSONObject jsonObj = new JSONObject();
        JSONArray jsonArr = new JSONArray();
        HashMap<String, Object> hash = new HashMap<String, Object>();

        for(int i=0; i < list.size(); i++) {
            hash.put("id", list.get(i).get("CLID")); //일정 고유번호
            hash.put("username", String.valueOf(list.get(i).get("CLUSERNAME"))); //일정 작성자
            hash.put("calendar", list.get(i).get("CLCALENDAR")); //일정 카테고리
            hash.put("title", list.get(i).get("CLTITLE")); //일정 제목
            hash.put("description", list.get(i).get("CLDESCRIPTION")); //일정 내용
            hash.put("start", list.get(i).get("CLSTART")); //시작일자
            hash.put("end", list.get(i).get("CLEND")); //종료일자
            hash.put("location", list.get(i).get("CLLOCATION")); //일정 위치
            hash.put("type", list.get(i).get("CLTYPE")); //일정 부서
            hash.put("backgroundColor", list.get(i).get("CLBACKGROUNDCOLOR")); //일정 색깔
            if(list.get(i).get("CLALLDAY").equals("false")){
                hash.put("allday", false); //하루종일X - string타입 false를 boolean타입 false로 변경
            }else{
                hash.put("allday", true); //하루종일O
            }
            /*hash.put("allday", list.get(i).get("CLALLDAY")); //종료일자*/

            jsonObj = new JSONObject(hash); //중괄호 {key:value , key:value, key:value}
            jsonArr.add(jsonObj); // 대괄호 안에 넣어주기[{key:value , key:value, key:value},{key:value , key:value, key:value}]
        }


        return jsonArr;

    }

    @PostMapping("/addEvent") //ajax 데이터 전송 URL
    @ResponseBody
    public String addEvent(@RequestBody List<Map<String, Object>> jsonData) {

        System.out.println(jsonData);

        for (int i = 0; i < jsonData.size(); i++) {
            int username = (Integer) jsonData.get(i).get("username");
            String calendarInfo = (String) jsonData.get(i).get("calendar");
            String title = (String) jsonData.get(i).get("title");
            String description = (String) jsonData.get(i).get("description");
            String startDateString = (String) jsonData.get(i).get("start");
            String endDateString = (String) jsonData.get(i).get("end");
            String location = (String) jsonData.get(i).get("location");
            String type = (String) jsonData.get(i).get("type");
            String backgroundColor = (String) jsonData.get(i).get("backgroundColor");
            String allDay = String.valueOf(jsonData.get(i).get("allDay"));

            Calendar calendar = new Calendar();
                calendar.setClusername(username);
                calendar.setClcalendar(calendarInfo);
                calendar.setCltitle(title);
                calendar.setCldescription(description);
                calendar.setClstart(startDateString);
                calendar.setClend(endDateString);
                calendar.setCllocation(location);
                calendar.setCltype(type);
                calendar.setClbackgroundcolor(backgroundColor);
                calendar.setClallday(allDay);

            calendarService.saveCalendar(calendar);
        }
        return "/calendar/addEvent";
    }

}

