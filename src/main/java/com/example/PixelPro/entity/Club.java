package com.example.PixelPro.entity;

import com.example.PixelPro.Bean.ClubBean;
import lombok.*;
import org.modelmapper.ModelMapper;
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
private String mbname;
private String clcategory;
private String cltitle;
private String cldetail;
private String clwriter;
private String cldate;
private int clview;

    private static ModelMapper modelMapper = new ModelMapper();
    public static Club insertClub(ClubBean clubBean) {
        return modelMapper.map(clubBean, Club.class);
    }
}
