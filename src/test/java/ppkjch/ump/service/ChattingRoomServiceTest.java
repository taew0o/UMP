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
        //유저 4명이 가입 되어 있음
        User user1 = userService.findUser("park");
        User user2 = userService.findUser("kim");
        User user3 = userService.findUser("jang");
        User user4 = userService.findUser("choi");

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);

        Long chattingroom_id = chattingRoomService.makeRoom(users);

        //Assertions.assertEquals(chattingRoom1, chattingRoom2);
        //Assertions.assertEquals(chattingRoom2, chattingRoom3);
    }

    @Test
    void findRoom() {
    }

    @Test
    void testMakeRoom() {
    }

    @Test
    void inviteRoom() {
    }

    @Test
    void testFindRoom() {
    }

    @Test
    void goOutRoom() {
    }

    @Test
    void messageTimeArray() {
    }
}