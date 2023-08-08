package com.example.PixelPro.Bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@ToString
public class FreeBean {

    private Integer fnum; //글번호
    private Integer mbnum; //회원번호
    //private Integer fcnum; //카테고리번호
    @NotBlank(message = "카테고리를 선택하세요")
    private String fcategory; //카테고리
    @NotEmpty(message = "제목을 입력하세요")
    private String ftitle; //제목
    @NotEmpty(message = "내용을 입력하세요")
    private String fdetail; //내용
    private String ffile; //파일이름
    private String fdate; //작성날짜
    private Integer fcount; //조회수

}
