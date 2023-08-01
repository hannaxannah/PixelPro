package com.example.PixelPro.Bean;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GapprovalBean { //일반 결재
    private int ganum;

    @NotBlank(message = "제목은 필수 입력 사항입니다.")
    private String gsubject;

    @NotBlank(message = "내용은 필수 입력 사항입니다.")
    private String gcontent;

    private int gwmbnum; //서류 작성자

    @NotBlank(message = "승인자를 선택해 주세요.")
    private String gsign; //ex)작성자(사원) - 상사- 팀장

    private int ghmbnum; //현재 서류 처리자

    private String gfile;

    private String gstatus;

    @NotBlank(message = "유형을 선택해 주세요.")
    private String gcategory;

    private String gdate;
}
