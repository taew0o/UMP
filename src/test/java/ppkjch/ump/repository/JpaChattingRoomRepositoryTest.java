package ppkjch.ump.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ppkjch.ump.entity.ChattingRoom;
import ppkjch.ump.entity.User;
import ppkjch.ump.entity.UserChattingRoom;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JpaChattingRoomRepositoryTest {
    @Autowired
    public JpaChattingRoomRepository jpaChattingRoomRepository;

    @Autowired
    public JpaUserRepository jpaUserRepository;

    @Test
    void save() {
        try {

            User u1 = jpaUserRepository.findOne("park");
            User u2 = jpaUserRepository.findOne("jang");

            List<UserChattingRoom> userChattingRooms = new ArrayList<>();

            UserChattingRoom userChattingRoom1 = new UserChattingRoom();
            userChattingRoom1.setUser(u1);

            UserChattingRoom userChattingRoom2 = new UserChattingRoom();
            userChattingRoom1.setUser(u2);

            userChattingRooms.add(userChattingRoom1);
            userChattingRooms.add(userChattingRoom2);

            ChattingRoom chattingRoom = ChattingRoom.createChattingroom(2, userChattingRooms);

            jpaChattingRoomRepository.save(chattingRoom);
        }catch(NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void findOne() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findChatRoomByUser() {
    }

    @Test
    void findChattingRoomByUser() {
    }

    @Test
    void goOutRoom() {
    }
}