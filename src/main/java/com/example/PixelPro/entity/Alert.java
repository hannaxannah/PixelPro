package com.example.PixelPro.entity;

import com.example.PixelPro.Bean.AlertBean;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import javax.persistence.*;

@Entity
@Table
@Setter
@Getter
@ToString
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //시퀀스
    private int alnum;

    private int mbnum;

    private String alurl;

    private String alcontent;

    @Column(nullable=false, columnDefinition = "date default sysdate")

    private String altime;

    private int alread;


    private static ModelMapper modelMapper = new ModelMapper();
    public static Alert insertAlert(AlertBean alertBean) {

        return modelMapper.map(alertBean,Alert.class);
    }

}
