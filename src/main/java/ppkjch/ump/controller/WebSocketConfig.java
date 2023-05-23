package ppkjch.ump.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final UmpWebSocketHandler umpWebSocketHandler;
    @Override //소켓 연결 url 설정 및 모든 연결 허용
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(umpWebSocketHandler, "/websocket")
                .setAllowedOrigins("*");
    }
}
