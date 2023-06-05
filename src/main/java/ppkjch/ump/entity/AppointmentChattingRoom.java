package ppkjch.ump.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
public class AppointmentChattingRoom extends ChattingRoom{
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime apt_time;


    public static AppointmentChattingRoom createAppointmentChattingRoom(int numPerson, List<UserChattingRoom> userChattingRooms, String roomName, LocalDateTime aptTime){
        AppointmentChattingRoom appointmentChattingRoom = new AppointmentChattingRoom();
        appointmentChattingRoom.setNumPerson(numPerson);
        appointmentChattingRoom.setCreateTime(LocalDateTime.now());
        appointmentChattingRoom.setChattingRoomName(roomName);
        for(UserChattingRoom ucr : userChattingRooms){
            appointmentChattingRoom.addUserChattingRoom(ucr);
        }
        appointmentChattingRoom.setApt_time(aptTime);
        return appointmentChattingRoom;

    }
}
