package com.example.PixelPro.entity;

import com.example.PixelPro.Bean.ClubBean;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name="club")
@Setter
@Getter
@ToString
public class Club {

@Id
@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer clnum;
    private String mbname;
    private String clcategory;
    private String cltitle;
    private String cldetail;
    private String clwriter;
    private String filename;
    private String cldate;

    private static ModelMapper modelMapper = new ModelMapper();
    public static Club insertClub(ClubBean clubBean) {
        Club club = modelMapper.map(clubBean, Club.class);
        return club;
    }
}
