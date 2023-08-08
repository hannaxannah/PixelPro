package com.example.PixelPro.Bean;

import com.example.PixelPro.entity.Notice;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@ToString
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Data
public class NoticeBean {

    private Integer nnum;
    private String mbname;
    @NotBlank(message = "제목은 필수입력입니다.")
    private String ntitle;
    @NotBlank(message = "내용은 필수입력입니다.")
    private String ndetail;
    private String filename;
    private String filepath;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "date default sysdate")
    private String ndate;
    @Column(columnDefinition = "integer default 0", nullable = false)
    private int nimportant;



    public Notice toEntity(){
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
    }

    @Builder
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
    }

}
