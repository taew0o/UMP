package ppkjch.ump.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TextMessageDTO {
    private Long roomId;
    private String textMsg;
    private LocalDateTime sendTime;
}
