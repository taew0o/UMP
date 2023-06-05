package ppkjch.ump.dto;

import lombok.Getter;
import lombok.Setter;


import java.util.ArrayList;


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
