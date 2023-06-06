package ppkjch.ump.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ppkjch.ump.dto.EvaluateAppointmentDTO;
import ppkjch.ump.entity.*;
import ppkjch.ump.exception.RoomFullException;
import ppkjch.ump.repository.JpaAppointmentChattingRoomRepository;
import ppkjch.ump.repository.JpaChattingRoomRepository;
import ppkjch.ump.repository.JpaMessageRepository;

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
    public Long saveAppointment(List<User> users, String roomName, Long createTime, String time, String location){
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
        AppointmentChattingRoom acr = AppointmentChattingRoom.createAppointmentChattingRoom(numPerson,userChattingRooms,roomName, time, location);
        jpaAppointmentChattingRoomRepository.save(acr);
        System.out.println("time = " + time);
        return acr.getId();
    }

    @Transactional
    public void goOutRoom(User u, AppointmentChattingRoom cr){
        //userChattingroom DB에서 지우고 채팅방 인원 수를 갱신
        jpaChattingRoomRepository.goOutRoom(u,cr);
        cr.updateNumPerson(-1);

        //만약 빈방이 되었다면 해당 방의 메세지 및 방 정보 삭제
        if(cr.isEmptyRoom()){
            jpaAppointmentChattingRoomRepository.removeRoom(cr);
            List<Message> messages = jpaMessageRepository.findMessageByRoom(cr);
            for (Message m: messages) {
                jpaMessageRepository.removeMessage(m);
            }
        }
    }


    public List<AppointmentChattingRoom> findAppointments(User user){
        return jpaAppointmentChattingRoomRepository.findAppointmentByUser(user);


    }

    public AppointmentChattingRoom findAppointmentChattingRoom(Long roomId){
        return jpaAppointmentChattingRoomRepository.findOne(roomId);
    }
}
