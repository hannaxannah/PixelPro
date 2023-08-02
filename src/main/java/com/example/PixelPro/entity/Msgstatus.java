package com.example.PixelPro.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SequenceGenerator(
        name = "MSGSTATUSGENERATOR",
        sequenceName = "MSGSTATUSSEQ", // 매핑할 데이터베이스 시퀀스 이름
        initialValue = 13,
        allocationSize = 1)
public class Msgstatus {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "MSGSTATUSGENERATOR")
    private int msnum; //메시지 상태 번호
    private int mnum; //메시지 번호
    private int cpnum; //참여자 번호
    private String isread; //메시지 상태
    private int cnum; //대화번호
}
