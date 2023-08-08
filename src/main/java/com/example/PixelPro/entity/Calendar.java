package com.example.PixelPro.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="calendar")
@Setter
@Getter
@ToString
@SequenceGenerator(
        name = "calendarseq",
        sequenceName = "calendarseq", // 매핑할 데이터베이스 시퀀스 이름
        initialValue = 1,
        allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
public class Calendar {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="calendarseq") // 시퀀스처리
    private Integer clid;
    private Integer clusername;
    private String clcalendar;
    private String cltitle;
    private String cldescription;
    private String clstart;
    private String clend;
    private String cllocation;
    private String cltype;
    private String clbackgroundcolor;
    private String clallday;

}
