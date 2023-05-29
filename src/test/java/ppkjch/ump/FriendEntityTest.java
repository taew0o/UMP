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
import ppkjch.ump.service.FriendService;
import ppkjch.ump.service.MessageService;
import ppkjch.ump.service.UserService;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)

public class FriendEntityTest {

    @Autowired
    public JpaFriendRepository jpaFriendRepository;
    @Autowired
    public UserService userService;

    @Autowired
    public FriendService friendService;

    @Test
    @Rollback(value = false)
    public void makeFriend(){
        User user1 = userService.findUser("park");
        User user2 = userService.findUser("choi");
        //find 메소드 있어야할 듯
        friendService.addFriend(user1,user2);
    }

    @Test
    @Rollback(value = false)
    public void findFriend(){
        User user1 = userService.findUser("park");
        List<User> friend_list = friendService.findFriendList(user1);
        for(int i = 0 ; i < friend_list.size() ; i++){
            System.out.println(friend_list.get(i).getId() + " " + friend_list.get(i).getName());
        }

    }
}
