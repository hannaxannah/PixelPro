package com.example.PixelPro.Bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@ToString
public class SecondhandBean {

    private Integer shnum; //글번호
    private Integer mbnum; //회원번호
    @NotBlank(message = "카테고리를 선택하세요")
    private String shcategory; //카테고리
    @NotEmpty(message = "제목을 입력하세요")
    private String shtitle; //제목
    @NotEmpty(message = "내용을 입력하세요")
    private String shdetail; //내용
    @NotEmpty(message = "가격을 입력하세요")
    private String shprice; //가격
    private String shlocation; //거래 희망 장소
    private MultipartFile uploadFilename; //파일
    private String shdate; //작성날짜
    private Integer shcount; //조회수

    private String shfile;  //작성자가 업로드한 파일명
    private String storefilename;   //서버 내부에서 관리하는 파일명
}
