package com.example.PixelPro.Bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@ToString
public class FreeCommentBean {

    private Integer fcnum;
    private Integer fnum;
    private Integer mbnum;
    @NotBlank(message = "댓글을 입력하세요")
    private String fcdetail;
    private String fcdate;
    private Integer fclike;
    private Integer fcstep;
    private Integer fclevel;
}
