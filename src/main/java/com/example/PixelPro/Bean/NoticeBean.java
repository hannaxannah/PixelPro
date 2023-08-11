package com.example.PixelPro.Bean;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class NoticeBean {

    private Integer nnum;
    private String mbname;
    @NotEmpty(message = "제목은 필수입력입니다.")
    private String ntitle;
    @NotEmpty(message = "내용은 필수입력입니다.")
    private String ndetail;
    private String filename;
    private String filepath;
    private String ndate;
    @Column(columnDefinition = "integer default 0", nullable = false)
    private int nimportant;



/*    public Notice toEntity(){
        Notice build = Notice.builder()
                .nnum(nnum)
                .mbname(mbname)
                .ntitle(ntitle)
                .ndetail(ndetail)
                .filename(filename)
                .filepath(filepath)
                .nimportant(nimportant)
                .build();
        return build;
    }*/

    private MultipartFile upload;

    public MultipartFile getUpload() {
        return upload;
    }

    public void setUpload(MultipartFile upload) {
        this.upload = upload;
        String fileName = upload.getOriginalFilename();

        System.out.println("fileName : " + fileName);

        this.filename = fileName;
    }

   /* @Builder
    public NoticeBean(Integer nnum, String mbname,
                      String ntitle, String ndetail, String filename, String filepath,
                      int nimportant){
        this.nnum = nnum;
        this.mbname = mbname;
        this.ntitle = ntitle;
        this.ndetail = ndetail;
        this.filename = filename;
        this.filepath = filepath;
        this.nimportant = nimportant;
    }*/

}
