package com.example.PixelPro.entity;

import com.example.PixelPro.Bean.FreeCommentBean;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import javax.persistence.*;

@Entity
@Table(name="freecomment")
@Setter
@Getter
@ToString
public class FreeCommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer fcnum;
    private Integer fnum;
    private Integer mbnum;
    @Column(length = 10000)
    private String fcdetail;
    private String fcdate;
    private Integer fclike;
    private Integer fcstep;
    private Integer fclevel;

    private static ModelMapper modelMapper = new ModelMapper();

    public static FreeCommentEntity insertFreeBoardComment(FreeCommentBean freeCommentBean) {
        FreeCommentEntity freeCommentEntity = modelMapper.map(freeCommentBean, FreeCommentEntity.class);
        return freeCommentEntity;
    }
}
