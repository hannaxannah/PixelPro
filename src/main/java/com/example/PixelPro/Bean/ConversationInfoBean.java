package com.example.PixelPro.Bean;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ConversationInfoBean {
    private String cname; //대화 별칭
    private String pname; //대화 참여자
}
