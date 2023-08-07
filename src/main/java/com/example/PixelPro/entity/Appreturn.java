package com.example.PixelPro.entity;

import com.example.PixelPro.Bean.AppreturnBean;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.persistence.*;

@Entity(name = "appreturn")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "appreturn")
@ToString
public class Appreturn {
    @Id
    private Integer ganum;
    private String rcontent;
    private Integer rsnum;
    private Integer rnum;

    private static ModelMapper modelMapper = new ModelMapper();
    public static Appreturn createAppreturn(AppreturnBean returnBean) {
        Appreturn returnEntity = modelMapper.map(returnBean, Appreturn.class);
        return returnEntity;
    }
}
