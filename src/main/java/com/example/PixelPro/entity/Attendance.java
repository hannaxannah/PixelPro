package com.example.PixelPro.entity;

import com.example.PixelPro.Bean.AtapprovalBean;
import com.example.PixelPro.Bean.AttendanceBean;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.sql.Date;

@Entity(name = "attendance")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "attendance")
@ToString
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer mbnum;
    private String atcategory;
    private Date atdate;

    private static ModelMapper modelMapper = new ModelMapper();
    public static Attendance createAttendance(AttendanceBean attendanceBean) {
        Attendance attendance = modelMapper.map(attendanceBean, Attendance.class);
        return attendance;
    }
}
