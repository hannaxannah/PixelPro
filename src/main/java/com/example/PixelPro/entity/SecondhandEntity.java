package com.example.PixelPro.entity;

import com.example.PixelPro.Bean.SecondhandBean;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import javax.persistence.*;

@Entity
@Table(name="secondhand")
@Setter
@Getter
@ToString
public class SecondhandEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer shnum; //글번호
    private Integer mbnum; //회원번호
    private String shcategory; //카테고리
    private String shtitle; //제목
    @Column(length = 50000)
    private String shdetail; //내용
    private String shprice; //가격
    private String shlocation; //거래 희망 장소
    private String shfile; //파일이름
    private String storefilename;   //서버 내부에서 관리하는 파일명
    private String shdate; //작성날짜
    private Integer shcount; //조회수

    private static ModelMapper modelMapper = new ModelMapper();

    public static SecondhandEntity insertSecondHandBoard(SecondhandBean secondhandBean) {
        SecondhandEntity secondhandEntity = modelMapper.map(secondhandBean, SecondhandEntity.class);
        return secondhandEntity;
    }

}
