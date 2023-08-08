package com.example.PixelPro.Bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@ToString
public class SecondhandCommentBean {

    private Integer shcnum;
    private Integer shnum;
    private Integer mbnum;
    @NotBlank(message = "댓글을 입력하세요")
    private String shcdetail;
    private String shcdate;
    private Integer shcstep;
    private Integer shclevel;
    private String shcsecret;
}
