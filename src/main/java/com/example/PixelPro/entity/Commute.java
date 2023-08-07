package com.example.PixelPro.entity;

import com.example.PixelPro.Bean.AttendanceBean;
import com.example.PixelPro.Bean.CommuteBean;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity(name = "commute")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "commute")
@ToString
public class Commute {
    @Id
    private Integer mbnum;

    @Column(columnDefinition = "TIMESTAMP")
    private Timestamp gotowork;
    @Column(columnDefinition = "TIMESTAMP",nullable = true)
    private Timestamp  getoff;

    private static ModelMapper modelMapper = new ModelMapper();
    public static Commute createCommute(CommuteBean commuteBean) {
        Commute commute = modelMapper.map(commuteBean, Commute.class);
        return commute;
    }
}
