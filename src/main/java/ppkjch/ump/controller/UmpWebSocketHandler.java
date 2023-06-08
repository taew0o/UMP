package ppkjch.ump.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ppkjch.ump.dto.TextMessageDTO;
import ppkjch.ump.entity.ChattingRoom;
import ppkjch.ump.entity.User;
import ppkjch.ump.service.ChattingRoomService;
import ppkjch.ump.service.MessageService;
import ppkjch.ump.service.UserService;

import java.io.IOException;
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
        session.getAttributes().put("roomId", roomId);
    }

    @Async
    protected void saveMessage(String text,User sender,ChattingRoom room, Long sendTime){
        messageService.createMessage(text, sender, room, sendTime);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);

        // JSON 문자열을 받아와 Java 객체로 바인딩받기
        String jsonString = message.getPayload();
        ObjectMapper objectMapper = new ObjectMapper();

        //바인딩할 객체 만들고 바인딩
        TextMessageDTO textMessageDTO = objectMapper.readValue(jsonString, TextMessageDTO.class);
        ChattingRoom room = chattingRoomService.findRoom(Long.parseLong(textMessageDTO.getRoomId()));
        Long sendTime = Long.parseLong(textMessageDTO.getSendTime());
        String text = textMessageDTO.getTextMsg();
        String senderId = textMessageDTO.getSenderId();

        //유저 가져오기
        User sender = userService.findUser(textMessageDTO.getSenderId());
        //가져온 유저 이름 바인딩 (server 이면 id가 server인 것을 가져옴 server는 예약된 유저)
        textMessageDTO.setSendName(sender.getName());

        //메세지 저장(비동기적 호출)
       this.saveMessage(text, sender, room, sendTime);

        // JSON 형식으로 직렬화
        String jsonMessage = objectMapper.writeValueAsString(textMessageDTO);

        //해당 메세지의 출처방에 소켓이 연결되어 있는 모든 유저에게 메세지 정보 전달
        for (WebSocketSession s : clients) {
            String sessionRoomId = (String)s.getAttributes().get("roomId");

            //같은방이고 자기자신이 아닌 소켓 연결 유저에게 메세지 전달
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
        
    }
}