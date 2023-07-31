package com.example.PixelPro.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="inbox")
@Setter
@Getter
@ToString
public class inbox {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer inum;
    private String email;
    private String recipient;
    private String ititle;
    private String icontent;
    private String attach;
    private String trash;
    private Date tdate;
    private Date senddate;
    private String status;
    private Date readdate;
    private int impo;
    private int iref;
    private int istep;
}
