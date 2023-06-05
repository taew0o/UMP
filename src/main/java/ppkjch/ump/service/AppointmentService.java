package ppkjch.ump.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import ppkjch.ump.entity.AppointmentChattingRoom;
import ppkjch.ump.entity.User;
import ppkjch.ump.entity.UserChattingRoom;
import ppkjch.ump.exception.RoomFullException;
import ppkjch.ump.repository.JpaAppointmentChattingRoomRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AppointmentService {
    private final JpaAppointmentChattingRoomRepository jpaAppointmentChattingRoomRepository;

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

    public List<AppointmentChattingRoom> findAppointmentTime(User user){
        return jpaAppointmentChattingRoomRepository.findAppointment(user);


    }

}
