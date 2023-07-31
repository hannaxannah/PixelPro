package com.example.PixelPro.Bean;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MsgstatusBean {
    private int msnum; //메시지 상태 번호
    private int mnum; //메시지 번호
    private int cpnum; //참여자 번호
    private String isread; //메시지 상태
    private int cnum; //대화번호
}
