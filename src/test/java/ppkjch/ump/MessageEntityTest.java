package ppkjch.ump;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ppkjch.ump.entity.User;
import ppkjch.ump.repository.JpaUserRepository;
import ppkjch.ump.service.UserService;

@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)

public class MessageEntityTest {

    @Autowired
    public JpaUserRepository jpaUserRepository;
    @Autowired
    public UserService userService;

    @Test
    @Rollback(value = true)
    public void 회원_엔티티(){
        User user = new User();
        user.setName("wuseong2");
        user.setPassword("1234");
        userService.join(user);
    }
}
