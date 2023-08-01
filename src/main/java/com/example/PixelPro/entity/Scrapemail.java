package com.example.PixelPro.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="scrapemail")
@Setter
@Getter
@ToString
public class Scrapemail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer scnum;
    private String email;
    private String scemail;
}
