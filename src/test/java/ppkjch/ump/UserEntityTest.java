package ppkjch.ump;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ppkjch.ump.entity.ChattingRoom;
import ppkjch.ump.entity.Message;
import ppkjch.ump.entity.User;
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

public class UserEntityTest {

    @Autowired
    public JpaUserRepository jpaMessageRepository;
    @Autowired
    public MessageService messageService;

    @Test
    @Rollback(value = false)
    public void 메세지_엔티티(){
        Message message = new Message();
        User user = new User(); //유저 만들기
        user.setId("park");
        user.setName("taewoo");
        user.setPassword("12345");

        ChattingRoom chattingRoom = new ChattingRoom();

        message.setUser(user); //채팅방 만들기
        message.setChattingRoom(chattingRoom);
        LocalDate date = LocalDate.of(2022, 1, 1);
        LocalTime time = LocalTime.of(0, 0,1,1);
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        
        message.setSendTime(dateTime);
        message.setTextMsg("박태우는 를 존나 좋아한다");
        message.setChattingRoom(chattingRoom);
        message.setUser(user);
        
        messageService.send(message);
        Long messageId = message.getId();
        //Message findMessage = messageService.findMessage(messageId);
        //System.out.println("findMessage.getTextMsg() = " + findMessage.getTextMsg());
        
    }
}
