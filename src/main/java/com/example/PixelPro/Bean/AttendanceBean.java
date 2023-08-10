package com.example.PixelPro.Bean;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AttendanceBean { //근태 상태
    private Integer adnum;
    private Integer mbnum;
    private String atcategory;
    private String atdate;
    private String atcontent;
    private String atname;

}
