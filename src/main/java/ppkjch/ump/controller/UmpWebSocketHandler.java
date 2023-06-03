package ppkjch.ump.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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
        System.out.println("관리할 세션 추가" + clients.size());
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

        String jsonString = message.getPayload();
        System.out.println("jsonString = " + jsonString);
        ObjectMapper objectMapper = new ObjectMapper();
        // JSON 문자열을 Java 객체로 파싱
        TextMessageDTO textMessageDTO = objectMapper.readValue(jsonString, TextMessageDTO.class);
        //유저 가져오기

        String senderId = textMessageDTO.getSenderId();
        System.out.println("senderId = " + senderId);
        User sender = userService.findUser(textMessageDTO.getSenderId());
        //유저 이름 가져오기
        textMessageDTO.setSendName(sender.getName());
        //방 가져오기
        System.out.println("textMessageDTO.getRoomId() = " + textMessageDTO.getRoomId());
        ChattingRoom room = chattingRoomService.findRoom(Long.parseLong(textMessageDTO.getRoomId()));
        //날짜 가져오기
        Long sendTime = Long.parseLong(textMessageDTO.getSendTime());
        //텍스트 가져오기
        String text = textMessageDTO.getTextMsg();
        //메세지 저장
        
        messageService.createMessage(text, sender, room, sendTime);
        //sender이름 추가하여 다시 json 형태로 직렬화
        String jsonMessage = objectMapper.writeValueAsString(textMessageDTO);
        //System.out.println("jsonMessage = " + jsonMessage);

        for (WebSocketSession s : clients) {
            System.out.println("세션 룸 아이디" + s.getAttributes().get("roomId"));
            //세션set 순회하며 자기 세션이 아니고 같은 방id를 가진 session이면 정보를 전달
            String sessionRoomId = (String)s.getAttributes().get("roomId");
//            System.out.println("sessionRoomId = " + sessionRoomId);
//            System.out.println("sessionRoomId = " + textMessageDTO.getRoomId());
//            System.out.println("sessionRoomId = " + s.getId());
//            System.out.println("sessionRoomId = " + session.getId());
            if(textMessageDTO.getRoomId().equals(sessionRoomId) && !(s.getId().equals(session.getId()))){
                logger.info("send data : {}", message);
                try{
                    s.sendMessage(new TextMessage(jsonMessage));
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        clients.remove(session);
        System.out.println(session.getId() + "세션 연결 종료 및 clients에서 제거");
        //서버에 나감 메세지 userId = "sever"로 해서 저장 하기 및
        //나머지 같은방 세션 실시간 사용자들에게 해당 메세지 보내기
    }
}