package ppkjch.ump.service;

import org.junit.jupiter.api.Test;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ppkjch.ump.entity.User;

class UserServiceTest {
    @Autowired
    public UserService userService;
    User U = new User();
    User M = new User();
    @Test

    void join() {
        U.setId("alexander");
        U.setPassword("66");
        U.setName("arnold");
        U.setPhone_num("01012345678");
        userService.join(U);
    }

    @Test
    @Rollback(value = false)
    void findUser() {
        U.setId("alexander");
        U.setPassword("66");
        U.setName("arnold");
        U.setPhone_num("01012345678");
        userService.join(U);
        System.out.println(userService.findUser("alexander"));
    }

    @Test
    @Rollback(value = false)
    void signUp() {
        U.setId("alexander");
        U.setPassword("66");
        U.setName("arnold");
        U.setPhone_num("01012345678");
        userService.join(U);
        userService.signUp("alexander","arnold","66","66");
    }

    @Test
    @Rollback(value = false)
    void checkLoginException() {
        userService.checkLoginException("alexander","");
        userService.checkLoginException("","66");
        userService.checkLoginException("alexander", "66");
    }

    @Test
    @Rollback(value = false)
    void updateUser() {
        U.setId("alexander");
        U.setPassword("66");
        U.setName("arnold");
        U.setPhone_num("01012345678");
        userService.join(U);
        userService.updateUser(U,"salah","01011001100","11");
    }

    @Test
    @Rollback(value = false)
    void findFriend() {
        U.setId("alexander");
        U.setPassword("66");
        U.setName("arnold");
        U.setPhone_num("01012345678");
        userService.join(U);
        userService.findUser("alexander");
        userService.findUser("thiago");
    }
}