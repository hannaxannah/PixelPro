package com.example.PixelPro.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int mbnum;
    private String email;
    private String mbname;
    private String mblevel;
    private String mbaccess;
    private String password;
    private String mbsign;
    private String mbphone;
    private String mbStartDate;
    private String mbEndDate;
    private String dept;
    private int msalary;
    private String mstate;

}
