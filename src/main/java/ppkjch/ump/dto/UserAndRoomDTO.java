package ppkjch.ump.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserAndRoomDTO {
    private Long roomId;
    private List<String> InviteeIds;
}
