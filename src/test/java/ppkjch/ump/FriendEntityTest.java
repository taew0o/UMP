package ppkjch.ump;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ppkjch.ump.entity.ChattingRoom;
import ppkjch.ump.entity.Friend;
import ppkjch.ump.entity.Message;
import ppkjch.ump.entity.User;
import ppkjch.ump.repository.JpaFriendRepository;
import ppkjch.ump.repository.JpaUserRepository;
import ppkjch.ump.service.MessageService;
import ppkjch.ump.service.UserService;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)

public class FriendEntityTest {

    @Autowired
    public JpaFriendRepository jpaFriendRepository;
    @Autowired
    public UserService userService;

    @Test
    @Rollback(value = false)
    public void 친구_엔티티(){
        User user1 = new User(); //유저 만들기
        user1.setId("taewoo");
        user1.setName("taewoo");
        user1.setPassword("12345");

        User user2 = new User(); //유저 만들기
        user2.setId("wuseong");
        user2.setName("wuseong");
        user2.setPassword("12345");

        userService.join(user1);// 유저 DB에 저장
        userService.join(user2);

        Friend f = new Friend();
        f.setUser1(user1);
        f.setUser2(user2);

        Long id = userService.makeFriend(f);
        //Long findId = jpaFriendRepository.findOne(id).getId();

//        Assertions.assertEquals(id,findId);



    }
}
