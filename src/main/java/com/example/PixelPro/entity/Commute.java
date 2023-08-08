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
@SequenceGenerator(
        name = "commutegenerator",
        sequenceName = "commutegenerator", // 매핑할 데이터베이스 시퀀스 이름
        initialValue = 1,
        allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "commute")
@ToString
public class Commute {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "commutegenerator")
    private Integer cmnum;

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
