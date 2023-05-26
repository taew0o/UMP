package ppkjch.ump.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
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
@Transactional()
class ChattingRoomServiceTest {

    @Autowired
    ChattingRoomService chattingRoomService;
    @Autowired
    UserService userService;
    @Autowired
    JpaChattingRoomRepository jpaChattingRoomRepository;
    @Test
    @Rollback(value = false)
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
        userIds.add(userId1);
        userIds.add(userId2);
        userIds.add(userId3);
        //3명이 있는 방을 만들고 userId정보들로 ChatRoom 가져오기

        //Long chatRoomId = chattingRoomService.makeRoom();
        List<UserChattingRoom> findUserChattingRoom1 = jpaChattingRoomRepository.findChatRoomByUser(user1);
        List<UserChattingRoom> findUserChattingRoom2 = jpaChattingRoomRepository.findChatRoomByUser(user2);
        List<UserChattingRoom> findUserChattingRoom3 = jpaChattingRoomRepository.findChatRoomByUser(user3);

        //같은 Chattingroom이 나오면 통과
        ChattingRoom chattingRoom1 = findUserChattingRoom1.get(0).getChattingRoom();
        ChattingRoom chattingRoom2 = findUserChattingRoom2.get(0).getChattingRoom();
        ChattingRoom chattingRoom3 = findUserChattingRoom3.get(0).getChattingRoom();

        Assertions.assertEquals(chattingRoom1, chattingRoom2);
        Assertions.assertEquals(chattingRoom2, chattingRoom3);
    }
}