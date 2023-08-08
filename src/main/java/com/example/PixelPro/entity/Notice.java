package com.example.PixelPro.entity;


import com.example.PixelPro.Bean.NoticeBean;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

@Entity
@Table(name="notice")
@Setter
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer nnum;
    private String mbname;
    private String ntitle;
    private String ndetail;
    private String filename;
    private String filepath;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ColumnDefault("sysdate")
    private String ndate;

    @ColumnDefault("0")
    private int nimportant;

    private static ModelMapper modelMapper = new ModelMapper();
    public static Notice insertNotice(NoticeBean noticeBean) {
        return modelMapper.map(noticeBean, Notice.class);

    }

    @Builder
    public Notice(Integer nnum, String mbname,
                  String ntitle, String ndetail, String filename, String filepath,
                  int nimportant) {
        this.nnum = nnum;
        this.mbname = mbname;
        this.ntitle = ntitle;
        this.ndetail = ndetail;
        this.filename = filename;
        this.filepath = filepath;
        this.nimportant = nimportant;
    }

}