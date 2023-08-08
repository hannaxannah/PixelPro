package com.example.PixelPro.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker //웹소켓 사용가능하게 하는 아노테이션
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) { //접속 연결 장소 설정 - 클라이언트 생성시 사용된다.
        registry.addEndpoint("/stompTest").addInterceptors(new MyHttpSessionHandShakeInterceptor()).setAllowedOriginPatterns("*").withSockJS(); //sock JS 를 등록한다. endpoint
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) { //구독하는 방 / 응답을 보내는 곳으로 생각하면된다. subscribe메서드 사용할때 필수
        registry.setApplicationDestinationPrefixes("/")
                .enableSimpleBroker("/topic"); //토픽 방식 지정해준다. 토픽이 여러개
        // /topic/XXX
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) { //버퍼 사이즈 에러 발생시 해결.
        registry.setMessageSizeLimit(500 * 1024);
        registry.setSendBufferSizeLimit(1024 * 1024);
        registry.setSendTimeLimit(20000);
    }
}
