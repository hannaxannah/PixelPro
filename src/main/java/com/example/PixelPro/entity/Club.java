package com.example.PixelPro.entity;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name="club")
@Setter
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Club {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Integer clnum;
private int mbnum;
private String clcategory;
private String cltitle;
private String cldetail;
private String clwriter;
private String cldate;
private int clview;

}
