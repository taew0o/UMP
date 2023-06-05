package ppkjch.ump.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import ppkjch.ump.entity.*;
import ppkjch.ump.exception.RoomFullException;
import ppkjch.ump.repository.JpaAppointmentChattingRoomRepository;
import ppkjch.ump.repository.JpaChattingRoomRepository;
import ppkjch.ump.repository.JpaMessageRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


//@EnableScheduling
//@EnableAsync
@RequiredArgsConstructor
@Service
public class AppointmentService {
    private final JpaAppointmentChattingRoomRepository jpaAppointmentChattingRoomRepository;
    private final JpaChattingRoomRepository jpaChattingRoomRepository;
    private final JpaMessageRepository jpaMessageRepository;
    @Transactional
    public Long saveAppointment(List<User> users, String roomName, Long createTime, String date, String time, String location){
        int numPerson = users.size();
        if(numPerson > 10){
            throw new RoomFullException("10명 이하의 유저를 선택해주십시오.");
        }
        List<UserChattingRoom> userChattingRooms = new ArrayList<>();
        for (User user: users) { //각 유저ID로 User 찾아 UserChattingroom객체 만들어 매핑
            UserChattingRoom userChattingRoom = new UserChattingRoom();
            userChattingRoom.setEnterTime(createTime);
            userChattingRoom.setUser(user);
            userChattingRooms.add(userChattingRoom);
        }

        //약속 채팅방 생성
        AppointmentChattingRoom acr = AppointmentChattingRoom.createAppointmentChattingRoom(numPerson,userChattingRooms,roomName, date, time, location);
        jpaAppointmentChattingRoomRepository.save(acr);
        return acr.getId();
    }
//    @Scheduled(initialDelay = 10000, fixedDelay = 1000000)
//    @Async //initialDelay 후 병렬적으로 실행
//    @Transactional
    public void explodeRoom(ChattingRoom cr) throws InterruptedException{
//        //관련 userChatting 정보 모두 삭제
//        //jpaChattingRoomRepository.removeRoom(cr);
//        //관련 메세지 정보 모두 삭제
//        System.out.println("siuuuuuuuuuuuu!!!!!");
//        //List<Message> messages = jpaMessageRepository.findMessageByRoom(cr);
//        //for (Message m: messages) {
//        //    jpaMessageRepository.removeMessage(m);
//        //}
    }


    public List<AppointmentChattingRoom> findAppointments(User user){
        return jpaAppointmentChattingRoomRepository.findAppointment(user);


    }

}
