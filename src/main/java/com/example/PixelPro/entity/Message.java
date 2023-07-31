package com.example.PixelPro.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SequenceGenerator(
        name = "MESSAGESEQGENERATOR",
        sequenceName = "MESSAGESEQ", // 매핑할 데이터베이스 시퀀스 이름
        initialValue = 5,
        allocationSize = 1)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "MESSAGESEQGENERATOR")
    private int mnum;
    private int cnum;
    private int cpnum;
    private String sender;
    private String mcontent;
    @Column(nullable = false)
    private String mtime;
    private String mblevel;
}
