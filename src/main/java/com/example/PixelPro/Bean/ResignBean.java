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
    @NotNull(message = "계산내역은 필수 입력 값입니다.")
    private Integer details;
    @NotBlank(message = "지급확인은 필수 입력 값입니다.")
    private String Payment;

}
