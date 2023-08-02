package com.example.PixelPro.Bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class MemberBean {
    private int mbnum;

    private String mbname; //이름

    private String email; //이메일

    private String mbaccess; //권한

    private String mblevel; //직책

    private String password; // 비밀번호

    private String mbsign; // 직인,사인

    private String mbphone; // 전호번호

    private String mbStartDate; // 입사일

    private String mbEndDate; // 퇴사일

    private String dept; // 부서

    private String msalary; // 급여

    private String mstate; // 근무상태(재직,퇴사)


}
