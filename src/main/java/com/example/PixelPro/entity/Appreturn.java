package com.example.PixelPro.entity;

import com.example.PixelPro.Bean.AppreturnBean;
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
public class Appreturn {
    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "appreturnseq", allocationSize=1)
    private Integer ganum;
    private String rcontent;

    private static ModelMapper modelMapper = new ModelMapper();
    public static Appreturn createAttendance(AppreturnBean returnBean) {
        Appreturn returnEntity = modelMapper.map(returnBean, Appreturn.class);
        return returnEntity;
    }
}
