package com.example.PixelPro.entity;

import com.example.PixelPro.Bean.ResignBean;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
@Entity
@Table(name= "resign")
@Setter
@Getter
@ToString
public class ResignEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer sevpay;
    @ColumnDefault("0")
    private Integer oneavgpay;
    @ColumnDefault("0")
    private Integer onedaypay;
    @ColumnDefault("0")
    private Integer thrpay;
    @ColumnDefault("0")
    private Integer thrday;
    private String Payment;

    /*ModelMapper안에 map이라는 메서드가 있고 2가지를 넣을 수 있음*/
    private static ModelMapper modelMapper = new ModelMapper();

    public static ResignEntity insertResign(ResignBean resignBean) {
        return modelMapper.map(resignBean,ResignEntity.class);
    }
}
