package com.example.PixelPro.Bean;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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

    private String signcontent;

    private Integer gwmbnum; //서류 작성자

    @NotNull(message = "1차 승인자는 필수입니다.")
    private Integer gsign1; //ex)작성자(사원) - 상사- 팀장
    private Integer gsign2;

    private int ghmbnum; //현재 서류 처리자

    private String gfile;

    private String gstatus;

    private String gdate;
}
