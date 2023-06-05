package ppkjch.ump.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;


@Setter
@Getter
public class MakeAppointmentRoomDTO {
    private ArrayList<String> userIds;
    private String roomName;
    private Long createTime;

    private String date;
    private String time;
    private String location;
}
