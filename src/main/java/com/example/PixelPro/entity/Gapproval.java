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
    private Integer ganum;
    private String gsubject;

    @Column(length = 3000)
    private String gcontent;

    @Column(length = 3000)
    private String signcontent;
    private Integer gwmbnum;
    private Integer gsign1;

    @ColumnDefault("-1")
    private Integer gsign2;
    private Integer ghmbnum;
    //private String gfile;
    private String gstatus;

    @ColumnDefault("sysdate")
    private Date gdate;

    private static ModelMapper modelMapper = new ModelMapper();
    public static Gapproval createGapproval(GapprovalBean gapprovalBean) {
        Gapproval gapproval = modelMapper.map(gapprovalBean, Gapproval.class);
        return gapproval;
    }
}
