package com.example.PixelPro.entity;

import com.example.PixelPro.Bean.CommuteBean;
import com.example.PixelPro.Bean.ReturnBean;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.persistence.*;

@Entity(name = "return")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "return")
@ToString
public class Return {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int ganum;
    private String rcontent;

    private static ModelMapper modelMapper = new ModelMapper();
    public static Return createAttendance(ReturnBean returnBean) {
        Return returnEntity = modelMapper.map(returnBean, Return.class);
        return returnEntity;
    }
}
