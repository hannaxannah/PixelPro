package com.example.PixelPro.Bean;

import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommuteBean { //출퇴근
    private Integer mbnum;
    private Timestamp gotowork;
    private Timestamp getoff;
}
