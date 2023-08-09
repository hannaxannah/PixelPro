package com.example.PixelPro.Bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
@Setter
@Getter
@ToString
public class ClubCommentBean {

    private Integer conum;
    private Integer clnum;
    private String mbname;
    @NotBlank(message = "댓글을 입력하세요.")
    private String cldetail;
    private String cldate;
    private Integer cllike;
    private Integer clstep;
    private Integer cllevel;
}
