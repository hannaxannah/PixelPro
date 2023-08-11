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
    private MultipartFile uploadFilename; //파일
    private String fdate; //작성날짜
    private Integer fcount; //조회수

    private String ffile;  //작성자가 업로드한 파일명
    private String storefilename;   //서버 내부에서 관리하는 파일명

}
