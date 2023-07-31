package com.example.PixelPro.Bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@ToString
public class SalaryBean {

    private Integer snum;
    @NotBlank(message = "사원명은 필수 입력 값입니다.")
    private String sname;
    @NotBlank(message = "명칭은 필수 입력 값입니다.")
    private String stitle;
    @NotBlank(message = "구분은 필수 입력 값입니다.")
    private String sortation;
    @NotEmpty(message = "지급일은 필수 입력 값입니다.")
    private String pdate;
    @NotNull(message = "지급총액은 필수 입력 값입니다.")
    private Integer allpayment;
    @NotNull(message = "소득세는 필수 입력 값입니다.")
    private Integer cometax;
    @NotNull(message = "지방세는 필수 입력 값입니다.")
    private Integer taxes;
    @NotNull(message = "국민연금은 필수 입력 값입니다.")
    private Integer pension;
    @NotNull(message = "건강보험은 필수 입력 값입니다.")
    private Integer health;
    @NotNull(message = "고용보험은 필수 입력 값입니다.")
    private Integer employment;
    @NotNull(message = "실급여는 필수 입력 값입니다.")
    private Integer actsalary;
}

/*
insert into salary(snum,sortation,sname,allpayment,cometax,taxes,pension,health,employment,actsalary)
values(1,'급여','신주영',3000000,90000,9000,64510,4600,3700,2876000);

insert into salary(snum,sortation,sname,allpayment,cometax,taxes,pension,health,employment,actsalary)
values(2,'급여','김재명',3452000,114000,10000,72270,7600,4300,3092400);
*/