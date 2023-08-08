package com.example.PixelPro.entity;

import com.example.PixelPro.Bean.SecondhandCommentBean;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import javax.persistence.*;

@Entity
@Table(name="secondhandcomment")
@Setter
@Getter
@ToString
public class SecondhandCommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer shcnum;
    private Integer shnum;
    private Integer mbnum;
    @Column(length = 10000)
    private String shcdetail;
    private String shcdate;
    private Integer shcstep;
    private Integer shclevel;
    private String shcsecret;

    private static ModelMapper modelMapper = new ModelMapper();

    public static SecondhandCommentEntity insertSecondhandBoardComment(SecondhandCommentBean secondhandCommentBean) {
        SecondhandCommentEntity secondhandCommentEntity = modelMapper.map(secondhandCommentBean, SecondhandCommentEntity.class);
        return secondhandCommentEntity;
    }
}
