package com.example.PixelPro.Bean;

import lombok.*;

import javax.persistence.Id;
import java.util.Date;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class inboxBean {

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
