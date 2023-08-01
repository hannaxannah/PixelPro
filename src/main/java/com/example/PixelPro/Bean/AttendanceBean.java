package com.example.PixelPro.Bean;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AttendanceBean { //근태 상태
    private int mbnum;
    private String atcategory;
    private String atdate;
}
