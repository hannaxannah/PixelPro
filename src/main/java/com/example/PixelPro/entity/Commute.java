package com.example.PixelPro.entity;

import com.example.PixelPro.Bean.AttendanceBean;
import com.example.PixelPro.Bean.CommuteBean;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.sql.Date;

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

    @ColumnDefault("sysdate")
    private Date gotowork;
    private Date getoff;

    private static ModelMapper modelMapper = new ModelMapper();
    public static Commute createAttendance(CommuteBean commuteBean) {
        Commute commute = modelMapper.map(commuteBean, Commute.class);
        return commute;
    }
}
