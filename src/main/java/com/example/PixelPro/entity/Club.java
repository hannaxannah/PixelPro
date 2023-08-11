package com.example.PixelPro.entity;

import com.example.PixelPro.Bean.ClubBean;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="club")
@Setter
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer clnum;
    private Integer mbnum;
    private String clcategory;
    private String cltitle;
    private String cldetail;
    private String clwriter; //게시글작성자
    private String cfilename;
    private String cfilepath;
    private String cldate;

    private static ModelMapper modelMapper = new ModelMapper();
    public static Club insertClub(ClubBean clubBean) {

        return modelMapper.map(clubBean, Club.class);
    }
}
