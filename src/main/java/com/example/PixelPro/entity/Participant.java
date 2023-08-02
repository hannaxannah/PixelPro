package com.example.PixelPro.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SequenceGenerator(
        name = "PARTICIPANTGENERATOR",
        sequenceName = "PARTICIPANTSEQ", // 매핑할 데이터베이스 시퀀스 이름
        initialValue = 7,
        allocationSize = 1)
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "PARTICIPANTGENERATOR")
    private int cpnum;
    private int mbnum;
    private int cnum;
}
