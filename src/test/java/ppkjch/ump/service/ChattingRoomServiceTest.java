package ppkjch.ump.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ppkjch.ump.entity.ChattingRoom;
import ppkjch.ump.entity.User;
import ppkjch.ump.entity.UserChattingRoom;
import ppkjch.ump.repository.JpaChattingRoomRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest //스프링 통합 테스트
@ExtendWith(SpringExtension.class)
class ChattingRoomServiceTest {

    @Autowired
    ChattingRoomService chattingRoomService;
    @Autowired
    UserService userService;
    @Autowired
    JpaChattingRoomRepository jpaChattingRoomRepository;
    @Test
    void makeRoom() {
        //유저 3명이 가입 되어 있음
        User user1 = new User();
        user1.setId("user1");
        user1.setName("taewoo");
        user1.setPassword("1234");

        User user2 = new User();
        user2.setId("user2");
        user2.setName("harang");
        user2.setPassword("1234");

        User user3 = new User();
        user3.setId("user3");
        user3.setName("seongyul");
        user3.setPassword("1234");

        List<String> userIds = new ArrayList<>();
        String userId1 = userService.join(user1);
        String userId2 = userService.join(user2);
        String userId3 = userService.join(user3);
        //3명이 있는 방을 만들고 userId정보들로 ChatRoom 가져오기
        
        
        Long chatRoomId = chattingRoomService.makeRoom(3, userIds);
//        ChattingRoom findChatRoom = jpaChattingRoomRepository.findOne(chatRoomId);
//        System.out.println("findChatRoom.getCreateTime() = " + findChatRoom.getCreateTime());
        User findUser1 = userService.findUser(userId1);
        User findUser2 = userService.findUser(userId2);
        User findUser3 = userService.findUser(userId3);

        List<UserChattingRoom> findUserChattingRoom1 = jpaChattingRoomRepository.findChatRoomByUser(findUser1);
        System.out.println("findUserChattingRoom1.get(0).toString() = " + findUserChattingRoom1.isEmpty());
//        List<UserChattingRoom> findUserChattingRoom2 = jpaChattingRoomRepository.findChatRoomByUserId(userId2);
//        List<UserChattingRoom> findUserChattingRoom3 = jpaChattingRoomRepository.findChatRoomByUserId(userId3);
//
//        //같은 Chattingroom이 나오면 통과
//        ChattingRoom chattingRoom1 = findUserChattingRoom1.get(0).getChattingRoom();
//        ChattingRoom chattingRoom2 = findUserChattingRoom2.get(0).getChattingRoom();
//        ChattingRoom chattingRoom3 = findUserChattingRoom3.get(0).getChattingRoom();
//
//        Assertions.assertEquals(chattingRoom1, chattingRoom2);
//        Assertions.assertEquals(chattingRoom2, chattingRoom3);

    }
}