package com.example.PixelPro.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
@Entity
@Table(name= "severancepay")
@Setter
@Getter
@ToString
public class ResignEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer sevpay;
    private Integer oneavgpay;
    private Integer onedaypay;
    private Integer thrpay;
    private Integer thrday;
    private Integer details;
    private String Payment;
}
