package ppkjch.ump;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ppkjch.ump.entity.*;
import ppkjch.ump.repository.JpaAppointmentChattingRoomRepository;
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

public class AppointmentChattingRoomEntityTest {

    @Autowired
    public JpaAppointmentChattingRoomRepository jpaAppointmentChattingRoomRepository;
    @Autowired
    public UserService userService;

    @Test
    @Rollback(value = false)
    public void 약속채팅방_엔티티(){
//

        AppointmentChattingRoom apcRoom = new AppointmentChattingRoom();
        LocalDate date = LocalDate.of(2023, 5, 19);
        LocalTime time = LocalTime.of(0, 0,1,1);
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        apcRoom.setApt_time(dateTime);
        apcRoom.setNumPerson(10);
        apcRoom.setCreateTime(dateTime);
        jpaAppointmentChattingRoomRepository.save(apcRoom);

        //int numPerson = jpaAppointmentChattingRoomRepository.findOne();




    }
}
