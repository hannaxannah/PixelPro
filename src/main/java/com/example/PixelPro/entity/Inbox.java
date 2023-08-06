package com.example.PixelPro.entity;

import com.example.PixelPro.Bean.InboxBean;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.util.Date;

@Entity(name="inbox")
@Table(name="inbox")
@Setter
@Getter
@ToString
@DynamicInsert
public class Inbox {
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
   /* @ColumnDefault("sysdate")*/
    private Date senddate;
    private String status;
    private Date readdate;
    private int impo;
    private int iref;
    private int istep;

    private static ModelMapper modelMapper = new ModelMapper();
    public static Inbox changEntity(InboxBean inboxBean){
        return modelMapper.map(inboxBean, Inbox.class);
    }
}
