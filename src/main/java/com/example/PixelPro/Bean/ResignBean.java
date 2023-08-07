package com.example.PixelPro.Bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Setter
@Getter
@ToString
public class ResignBean {

    private Integer sevpay;
    @NotNull(message = "평균임금은 필수 입력 값입니다.")
    private Integer oneavgpay;
    @NotNull(message = "통상임금 필수 입력 값입니다.")
    private Integer onedaypay;
    @NotNull(message = "급여는 필수 입력 값입니다.")
    private Integer thrpay;
    @NotNull(message = "근무일수는 필수 입력 값입니다.")
    private Integer thrday;
    @NotBlank(message = "지급확인은 필수 입력 값입니다.")
    private String Payment;

}

/*
insert into member values(20210321,'개발팀','bo10820@naver.com','A등급','2023/08/06','팀장','신주영','010-3171-1401','사인0','2020/07/30','3000000','퇴사','1234');
* */