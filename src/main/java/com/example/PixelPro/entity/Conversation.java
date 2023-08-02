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
        name = "CONVERSATIONGENERATOR",
        sequenceName = "CONVERSATIONSEQ", // 매핑할 데이터베이스 시퀀스 이름
        initialValue = 3,
        allocationSize = 1)
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "CONVERSATIONGENERATOR")
    private int cnum;
    private String cname;
    private String cdate;

}
