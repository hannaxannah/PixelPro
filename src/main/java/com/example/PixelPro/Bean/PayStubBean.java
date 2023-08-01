package com.example.PixelPro.Bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@ToString
public class PayStubBean {
    private Integer snum;
    @NotNull(message = "수당항목은 필수 선택입니다.")
    private String aitem; //select option
    @NotNull(message = "지급유형은 필수 선택입니다.")
    private String ptype; //select option
    @NotNull(message = "근무기록은 필수 입력 값입니다.")
    private Integer wrecord;
    @NotNull(message = "수당금액은 필수 입력 값입니다.")
    private Integer allowance;
    @NotNull(message = "금액 합계는 필수 입력 값입니다.")
    private Integer tsum;
    @NotBlank(message = "산출항목은 필수 입력 값입니다.")
    private String putitem;


}
