package ppkjch.ump.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
public class MakeRoomDTO {
    private ArrayList<String> userIds;
    private String roomName;

}
