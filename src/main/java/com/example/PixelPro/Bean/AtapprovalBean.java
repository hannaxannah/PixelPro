package com.example.PixelPro.Bean;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AtapprovalBean { //근태 결재
    private int atnum;

    @NotBlank(message = "내용은 필수 입력 사항입니다.")
    private String atcontent;

    private Integer atwmbnum; //서류 작성자

    @NotNull(message = "1차 승인자를 선택해 주세요.")
    private Integer atsign1; //ex)작성자(사원) - 상사- 팀장

    private Integer atsign2;

    private Integer athmbnum; //현재 서류 처리자

    private String atstatus;

    @NotBlank(message = "유형을 선택해 주세요.")
    private String atcategory; //연차,반차,월차,휴가,병가

    private String atdate;
}
