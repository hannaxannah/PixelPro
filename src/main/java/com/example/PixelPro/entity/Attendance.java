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
@SequenceGenerator(
        name = "attendancegenerator",
        sequenceName = "attendancegenerator", // 매핑할 데이터베이스 시퀀스 이름
        initialValue = 1,
        allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "attendance")
@ToString
public class Attendance {

    //@GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attendancegenerator")
    private Integer atnum;
    private Integer mbnum;
    private String atcategory;
    private Date atdate;
    private String atcontent;
    private String atname;

    private static ModelMapper modelMapper = new ModelMapper();
    public static Attendance createAttendance(AttendanceBean attendanceBean) {
        Attendance attendance = modelMapper.map(attendanceBean, Attendance.class);
        return attendance;
    }
}
