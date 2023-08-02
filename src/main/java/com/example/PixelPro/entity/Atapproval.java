package com.example.PixelPro.entity;

import com.example.PixelPro.Bean.AtapprovalBean;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.persistence.*;

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
    private int atnum;
    private String atcontent;
    private int atwmbnum;
    private int atsign1;
    private int atsign2;
    private int athmbnum;
    private String atstatus;
    private String atcategory;
    private String atdate;

    private static ModelMapper modelMapper = new ModelMapper();
    public static Atapproval createGapproval(AtapprovalBean atapprovalBean) {
        Atapproval atapproval = modelMapper.map(atapprovalBean, Atapproval.class);
        return atapproval;
    }
}
