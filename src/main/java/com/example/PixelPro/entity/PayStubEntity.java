package com.example.PixelPro.entity;

import com.example.PixelPro.Bean.PayStubBean;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name= "paystub")
@Setter
@Getter
@ToString
public class PayStubEntity {
    @Id
    private Integer snum;
    private String aitem;
    private String ptype;
    private Integer wrecord;
    private Integer allowance;
    private Integer tsum;
    private String putitem;

    /*ModelMapper안에 map이라는 메서드가 있고 2가지를 넣을 수 있음*/
    private static ModelMapper modelMapper = new ModelMapper();

    public static PayStubEntity insertPaystub(PayStubBean paystubBean) {
        return modelMapper.map(paystubBean,PayStubEntity.class);
    }
}
