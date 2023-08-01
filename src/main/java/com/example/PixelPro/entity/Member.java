package com.example.PixelPro.entity;

import com.example.PixelPro.bean.MemberBean;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import javax.persistence.*;

@Entity
@Table
@Setter
@Getter
@ToString
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //시퀀스
    private int mbnum;

    private String mbname; //이름
    @Column(unique = true)
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

    private static ModelMapper modelMapper = new ModelMapper();
    public static Member insertMember(MemberBean memberBean) {

        return modelMapper.map(memberBean,Member.class);
    }

}
