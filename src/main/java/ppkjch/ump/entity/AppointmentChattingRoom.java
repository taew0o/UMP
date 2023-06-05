package ppkjch.ump.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
public class AppointmentChattingRoom extends ChattingRoom{

    private String time;
    private String location;

    public static AppointmentChattingRoom createAppointmentChattingRoom(int numPerson, List<UserChattingRoom> userChattingRooms
            , String roomName, String time, String location){
        AppointmentChattingRoom appointmentChattingRoom = new AppointmentChattingRoom();
        appointmentChattingRoom.setNumPerson(numPerson);
        appointmentChattingRoom.setTime(time);
        appointmentChattingRoom.setLocation(location);
        appointmentChattingRoom.setChattingRoomName(roomName);
        for(UserChattingRoom ucr : userChattingRooms){
            appointmentChattingRoom.addUserChattingRoom(ucr);
        }
        return appointmentChattingRoom;

    }
}
