package ppkjch.ump.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InviteRoomDTO {
    private Long roomId;
    private List<String> InviteeIds;
    private Long enterTime;
}
