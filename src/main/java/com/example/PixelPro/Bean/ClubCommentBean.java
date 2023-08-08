package com.example.PixelPro.Bean;

import javax.validation.constraints.NotBlank;

public class ClubCommentBean {

    private Integer conum;
    private Integer clnum;
    private Integer mbnum;
    @NotBlank(message = "댓글을 입력하세요.")
    private String cldetail;
    private String cldate;
    private Integer cllike;
    private Integer clstep;
    private Integer cllevel;
}
