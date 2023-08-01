package com.example.PixelPro.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Msgstatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int msnum; //메시지 상태 번호
    private int mnum; //메시지 번호
    private int cpnum; //참여자 번호
    private String isread; //메시지 상태
    private int cnum; //대화번호
}
