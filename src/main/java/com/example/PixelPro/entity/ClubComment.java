package com.example.PixelPro.entity;

import com.example.PixelPro.Bean.ClubCommentBean;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="clubcomment")
@Setter
@Getter
@ToString
public class ClubComment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer conum;
    private Integer clnum;
    private String mbname;
    private String cldetail;
    private String cldate;
    private Integer cllike;
    private Integer clstep;
    private Integer cllevel;

    private static ModelMapper modelMapper = new ModelMapper();

    public static ClubComment insertClubComment(ClubCommentBean clubCommentBean) {
        ClubComment clubComment = modelMapper.map(clubCommentBean, ClubComment.class);
        return clubComment;
    }
}
