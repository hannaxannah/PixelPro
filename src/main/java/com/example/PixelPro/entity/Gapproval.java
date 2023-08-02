package com.example.PixelPro.entity;

import com.example.PixelPro.Bean.GapprovalBean;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.sql.Date;

@Entity(name = "gapproval")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "gapproval")
@ToString
public class Gapproval {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int ganum;
    private String gsubject;
    private String gcontent;
    private int gwmbnum;
    private int gsign1;
    private int gsign2;
    private int ghmbnum;
    private String gfile;
    private String gstatus;
    private String gcategory;

    @ColumnDefault("sysdate")
    private Date gdate;

    private static ModelMapper modelMapper = new ModelMapper();
    public static Gapproval createGapproval(GapprovalBean gapprovalBean) {
        Gapproval gapproval = modelMapper.map(gapprovalBean, Gapproval.class);
        return gapproval;
    }
}
