package com.example.PixelPro.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker //웹소켓 사용가능하는 아노테이션
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) { //접속 연결 장소 설정
        registry.addEndpoint("/stompTest").addInterceptors(new MyHttpSessionHandShakeInterceptor()).setAllowedOriginPatterns("*").withSockJS(); //sock JS 를 등록한다. endpoint
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/")
                .enableSimpleBroker("/topic"); //토픽 방식 지정해준다. 토픽이 여러개
        // /topic/XXX
    }
}
