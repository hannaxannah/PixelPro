package com.example.PixelPro.entity;

import com.example.PixelPro.Bean.SalaryBean;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name= "salary")
@Setter
@Getter
@ToString
public class SalaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer snum;
    private String sname;
    private String stitle;
    private String sortation;
    @ColumnDefault("sysdate")
    private String pdate;
    @ColumnDefault("0")
    private Integer allpayment;
    @ColumnDefault("0")
    private Integer cometax;
    @ColumnDefault("0")
    private Integer taxes;
    @ColumnDefault("0")
    private Integer pension;
    @ColumnDefault("0")
    private Integer health;
    @ColumnDefault("0")
    private Integer employment;
    @ColumnDefault("0")
    private Integer actsalary;

    /*ModelMapper안에 map이라는 메서드가 있고 2가지를 넣을 수 있음*/
    private static ModelMapper modelMapper = new ModelMapper();

    public static SalaryEntity insertSalary(SalaryBean salaryBean) {
        return modelMapper.map(salaryBean,SalaryEntity.class);
    }
}
