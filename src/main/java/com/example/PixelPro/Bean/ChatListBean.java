package com.example.PixelPro.Bean;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatListBean {
    private String cname;
    private String recentMsg;
    private int unreadCount;
    private String recentSenderName;
    private int cnum;
    
}
