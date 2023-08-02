package com.example.PixelPro.entity;

import com.example.PixelPro.Bean.AtapprovalBean;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.sql.Date;

@Entity(name = "atapproval")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "atapproval")
@ToString
public class Atapproval {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer atnum;
    private String atcontent;
    private Integer atwmbnum;
    private Integer atsign1;

    @ColumnDefault("0")
    private Integer atsign2;
    private Integer athmbnum;
    private String atstatus;
    private String atcategory;
    private Date atdate;

    private static ModelMapper modelMapper = new ModelMapper();
    public static Atapproval createGapproval(AtapprovalBean atapprovalBean) {
        Atapproval atapproval = modelMapper.map(atapprovalBean, Atapproval.class);
        return atapproval;
    }
}
