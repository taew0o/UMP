package ppkjch.ump.controller;

import jakarta.websocket.Session;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ppkjch.ump.service.MessageService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;



@Component
@RequiredArgsConstructor
public class UmpWebSocketHandler extends TextWebSocketHandler {
    //private final
    // WebSocketHandler 구현
    private final MessageService messageService;
    private static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());
    private static Logger logger = LoggerFactory.getLogger(UmpWebSocketHandler.class);
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        //session.
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        //messageService
        for (Session s : clients) {
            logger.info("send data : {}", message);
            s.getBasicRemote().sendText(message.getPayload());
        }
    }
        //받은 메세지 DB에 저장
        //세션을 통해 모든 소켓에 message 전달

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }
}