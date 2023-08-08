package com.example.PixelPro.Bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class ClubBean {
    private Integer clnum;
    private int mbnum;
    @NotBlank(message = "카테고리를 선택하세요.")
    private String clcategory;
    @NotEmpty(message = "제목은 필수입력입니다.")
    private String cltitle;
    @NotEmpty(message = "내용은 필수입력입니다.")
    private String cldetail;
    private String clwriter;
    private String cldate;
    private int clview;


}
