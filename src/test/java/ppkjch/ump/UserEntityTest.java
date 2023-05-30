package ppkjch.ump;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ppkjch.ump.entity.Friend;
import ppkjch.ump.entity.User;
import ppkjch.ump.repository.JpaFriendRepository;
import ppkjch.ump.repository.JpaUserRepository;
import ppkjch.ump.service.FriendService;
import ppkjch.ump.service.UserService;

@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)

public class UserEntityTest {

    @Autowired
    public JpaUserRepository jpaUserRepository;
    @Autowired
    public UserService userService;

    @Autowired
    public JpaFriendRepository jpaFriendRepository;
    @Autowired
    public FriendService friendService;

    @Test
    @Rollback(value = false)
    public void Join() {
        User user = new User(); //유저 만들기
        user.setId("choi");
        user.setName("siwon");
        user.setPassword("12345");
        user.setPhone_num("01012345678");

        userService.join(user);
    }

    @Test
    @Rollback(value = false)
    public void findUser(){
        User user= userService.findUser("park");
        System.out.println(user.getName());
        System.out.println(user.getId());
    }

    public User makeUser(String id, String name, String password, String phone_num){
        User user = new User(); //유저 만들기
        user.setId(id);
        user.setName(name);
        user.setPassword(password);
        user.setPhone_num(phone_num);
        return user;
    }

}
