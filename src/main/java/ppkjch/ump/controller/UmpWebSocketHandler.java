package ppkjch.ump.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.websocket.Session;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ppkjch.ump.dto.TextMessageDTO;
import ppkjch.ump.entity.ChattingRoom;
import ppkjch.ump.entity.Message;
import ppkjch.ump.entity.User;
import ppkjch.ump.service.ChattingRoomService;
import ppkjch.ump.service.MessageService;
import ppkjch.ump.service.UserService;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Component
@RequiredArgsConstructor
public class UmpWebSocketHandler extends TextWebSocketHandler {

    // WebSocketHandler 구현
    private final MessageService messageService;
    private final UserService userService;
    private final ChattingRoomService chattingRoomService;
    private static Set<WebSocketSession> clients = new HashSet<WebSocketSession>();
    private static Logger logger = LoggerFactory.getLogger(UmpWebSocketHandler.class);
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        //관리할 세션 set에 추가
        clients.add(session);
        //쿼리스트링 읽어서 RoomId 정보 저장
        String roomId = null;
        String query = session.getUri().getQuery();
        for (int i = 0; i< query.length(); i++) {
            if(query.charAt(i) == '='){
                roomId = query.substring(i+1);
                break;
            }
        }
        System.out.println("roomId" + roomId);
        session.getAttributes().put("roomId", roomId);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        //System.out.println(message.getPayload());
        //유저 가져오기
        Map<String, Object> attributes = session.getAttributes();

        String jsonString = message.getPayload();

        ObjectMapper objectMapper = new ObjectMapper();
        // JSON 문자열을 Java 객체로 파싱
        TextMessageDTO textMessageDTO = objectMapper.readValue(jsonString, TextMessageDTO.class);
        //유저 가져오기
        String senderId = textMessageDTO.getSenderId();
        System.out.println("senderId = " + senderId);
        User sender = userService.findUser(textMessageDTO.getSenderId());
        //방 가져오기
        System.out.println("textMessageDTO.getRoomId() = " + textMessageDTO.getRoomId());
        ChattingRoom room = chattingRoomService.findRoom(Long.parseLong(textMessageDTO.getRoomId()));
        //날짜 가져오기
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy. M. d. a h:mm:ss", Locale.KOREAN);
        LocalDateTime sendTime = LocalDateTime.parse(textMessageDTO.getSendTime(), formatter);
        //텍스트 가져오기
        String text = textMessageDTO.getTextMsg();
        //메세지 저장

        messageService.createMessage(text, sender, room, sendTime);


        for (WebSocketSession s : clients) {
            System.out.println("세션 룸 아이디" + s.getAttributes().get("roomId"));
            //세션set 순회하며 자기 세션이 아니고 같은 방id를 가진 session이면 정보를 전달
            String sessionRoomId = (String)s.getAttributes().get("roomId");
            if(textMessageDTO.getRoomId().equals(sessionRoomId) && (s != session)){
                logger.info("send data : {}", message);
                try{
                    session.sendMessage(new TextMessage(jsonString));
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }
}