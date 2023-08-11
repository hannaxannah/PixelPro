package com.example.PixelPro.Bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class ClubBean {
    private Integer clnum;
    private Integer mbnum;
    @NotBlank(message = "카테고리를 선택하세요.")
    private String clcategory;
    @NotEmpty(message = "제목은 필수입력입니다.")
    private String cltitle;
    @NotEmpty(message = "내용은 필수입력입니다.")
    private String cldetail;
    private String clwriter; //게시글작성자
    private String cfilename;
    private String cfilepath;
    private String cldate;
    private MultipartFile upload;

    public MultipartFile getUpload() {
        return upload;
    }

    public void setUpload(MultipartFile upload) {
        this.upload = upload;
        String fileName = upload.getOriginalFilename();

        System.out.println("fileName : " + fileName);

        this.cfilename = fileName;
    }

}
