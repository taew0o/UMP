package ppkjch.ump.dto;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ppkjch.ump.entity.ChattingRoom;
import ppkjch.ump.entity.User;

@Getter
@Setter
public class GetMessageDTO {
    private Long id;

    private String textMsg;

    private ChattingRoom chattingRoom;

    private String senderId;

    private String sendName;

    private Long sendTime;
}
