package com.example.PixelPro.entity;

import com.example.PixelPro.Bean.FreeBean;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import javax.persistence.*;

@Entity
@Table(name="free")
@Setter
@Getter
@ToString
public class FreeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer fnum; //글번호
    private Integer mbnum; //회원번호

    //private Integer fcnum; //카테고리번호
    private String fcategory; //카테고리
    private String ftitle; //제목
    @Column(length = 50000)
    private String fdetail; //내용
    private String ffile; //파일이름
    private String storefilename;   //서버 내부에서 관리하는 파일명
    private String fdate; //작성날짜
    private Integer fcount; //조회수

    private static ModelMapper modelMapper = new ModelMapper();

    public static FreeEntity insertFreeBoard(FreeBean freeBean) {
        FreeEntity freeEntity = modelMapper.map(freeBean, FreeEntity.class);
        return freeEntity;
    }

}
