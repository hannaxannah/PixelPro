package com.example.PixelPro.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="calendar")
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Calendar {
    @Id
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
