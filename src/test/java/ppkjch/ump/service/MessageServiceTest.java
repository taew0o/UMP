//package ppkjch.ump.service;
//
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import ppkjch.ump.entity.ChattingRoom;
//import ppkjch.ump.entity.Message;
//import ppkjch.ump.entity.User;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//@SpringBootTest //스프링 통합 테스트
//@ExtendWith(SpringExtension.class)
//@Transactional()
//class MessageServiceTest {
//
//    @Autowired
//    UserService userService;
//    @Autowired
//    ChattingRoomService chattingRoomService;
//    @Autowired
//    MessageService messageService;
//
//    @Rollback(value = false)
//    @Test
//    void createMessage() {
////        int i = 54;
////        long id = i;
////        User user = userService.findUser("taewoo9240");
////        ChattingRoom chattingRoom = chattingRoomService.findRoom(id);
////        String text = "반갑습니다";
////        messageService.createMessage(text,user,chattingRoom, Long.parseLong("sdfsf"));
//    }
//
//    @Test
//    void findMessages() {
////        int i = 54;
////        long id = i;
////        ChattingRoom chattingRoom = chattingRoomService.findRoom(id);
////        List<Message> messageList =  messageService.findMessages(id);
////        for(Message m : messageList){
////            System.out.println(m.getUser().getName()+":" + m.getSendTime() + " " + m.getTextMsg());
////        }
//    }
//
//    @Test
//    void findMessage() {
//    }
//}