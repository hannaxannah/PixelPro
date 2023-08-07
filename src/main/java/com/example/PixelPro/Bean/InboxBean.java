package com.example.PixelPro.Bean;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class InboxBean {

    private Integer inum;
    private String email;
    private String recipient;
    private String ititle;
    private String icontent;
    private String attach;
    private String trash;
    private String tdate;
    private String senddate;
    private String status;
    private String readdate;
    private int impo;
    private int iref;
    private int istep;

    private String original;


    private MultipartFile upload;
    public MultipartFile getUpload() {
        return upload;
    }

    public void setUpload(MultipartFile upload) {
        this.upload = upload;
        String fileName = upload.getOriginalFilename();

        System.out.println("fileName : " + fileName);

        this.attach = fileName;
    }



}
