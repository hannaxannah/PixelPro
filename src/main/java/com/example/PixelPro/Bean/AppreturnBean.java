package com.example.PixelPro.Bean;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AppreturnBean { //반려
    private Integer ganum;

    @NotBlank(message = "반려 사유를 작성해 주세요.")
    private String rcontent;
    private Integer rsnum;
    private Integer rnum;
}
