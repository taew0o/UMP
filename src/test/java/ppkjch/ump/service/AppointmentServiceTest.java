package ppkjch.ump.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ppkjch.ump.entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest //스프링 통합 테스트
@ExtendWith(SpringExtension.class)
@Transactional()
class AppointmentServiceTest {

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    UserService userService;

    @Rollback(value = false)
    @Test
    void saveAppointment() {
//        User u1 = userService.findUser("taewoo9240");
//        User u2 = userService.findUser("admin");
//        User u3 = userService.findUser("admin2");
//        List<User> userList = new ArrayList<>();
//        userList.add(u1);
//        userList.add(u2);
//        userList.add(u3);
//
//
//        String roomName = "태우생일";
//        LocalDateTime aptTime = LocalDateTime.of(2023, 07, 22, 0, 0, 0);

        //appointmentService.saveAppointment(userList,roomName,aptTime);
    }

}