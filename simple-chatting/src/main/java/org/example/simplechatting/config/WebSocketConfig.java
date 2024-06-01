package org.example.simplechatting.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author : kangtaegyung
 * @fileName : WebSocketConfig
 * @since : 24. 6. 1.
 * description :
 *     1) sub 구독한 수신자에게 메세지 broadcasting
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${simpleDms.app.front-url}")
    private String frontUrl;

//    메시지를 구독할 주소를 정의
    @Override
    public void configureMessageBroker(MessageBrokerRegistry messageBrokerRegistry) {
        messageBrokerRegistry.enableSimpleBroker("/sub");               // 1)
    }

//    웹 소켓 연결설정
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/")
                .setAllowedOrigins(frontUrl)                                            // vue 주소
                .withSockJS();
    }
}